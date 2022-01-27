from assess import *
from sklearn_extra.cluster import KMedoids
import cx_Oracle
import json
import platform
import numpy as np
import time

# Library functions
usings = [difference, ratio, reldifference]  # , absdifference
# labels = [quartile,  # ok for all comparisons
#           likert3, likert5,  # ok for difference and relative difference
#           fixedratio2, fixedratio3, fixedratio5,  # ok for ratio
#           fixeddiff2, fixeddiff3, fixeddiff5,  # ok for difference
#           fixedrel3, fixedrel5]  # ok for relative difference
labels = {
    "ratio":         [quartile, likert3, likert5, fixedratio2, fixedratio3, fixedratio5],  # ok for relative difference
    "difference":    [quartile, likert3, likert5, fixeddiff2,  fixeddiff3,  fixeddiff5],  # ok for relative difference
    "reldifference": [quartile, likert3, likert5, fixeddiff2,  fixedrel3,   fixedrel5]  # ok for relative difference
}
features = [avg, std, skew]

def clean_sql_df(df):
    # if len(df[byclause].drop_duplicates().index) != len(df.index):
    #     print("Duplicates in the cube")
    #     sys.exit(1)
    return df.round(5).fillna(0)


def compute_auto_benchmark_sql(sql, k, measure, byclause):
    df = clean_sql_df(pd.read_sql(sql, con=connection))
    if len(df.index) == 0:
        print("Empty dataframe, did you choose a proper selection predicate?")
        sys.exit(1)
    return compute_auto_benchmarks(df, k, measure, byclause)


def splitAttr(benchmark):
    attr, op, val = benchmark[1:-1].replace("[", "").replace("]", "").replace("'", "").split(",")
    return attr, op, val


def diversify(X, benchmarks, k):
    # apply diversification with clustering
    kmedoids = KMedoids(n_clusters=min(k, len(benchmarks)), random_state=0, init='k-medoids++').fit(X) # , max_iter=300
    # get the names of the diversified columns
    return [benchmarks[x] for x in kmedoids.medoid_indices_]


def compute_auto_benchmarks(Y, k, measure, byclause):
    # pick a default function
    using = usings[0]
    fun_name = str(using.__name__)
    # get all the siblings and parents (which are not normalized by property)
    # E.g., pick RUSSIA but not RUSSIA_population
    for slice in [x for x in Y.columns if x not in byclause and x != measure and "_" not in x]:
        # SOL1: apply the default comparison function
        # Y[fun_name + "_" + slice] = Y.apply(lambda x: using(x[measure], x[slice]), axis=1)
        # SOL2: do not apply any comparison here
        Y[fun_name + "_" + slice] = Y[slice]
    # select the column names on which diversification will be applied
    benchmarks = [x for x in Y.columns if fun_name in x]
    # transform such columns into numpy arrays
    X = np.array([Y[x].to_numpy() for x in benchmarks])
    # apply diversification
    benchmarks = diversify(X, benchmarks, k)
    Ys = []
    # iterate over the diversified columns
    for benchmark in benchmarks:
        # # get the function name
        # using = benchmark.split("_")[0]
        # get the name of the column
        l = benchmark.split("_")[1]
        # generate the new data frame
        Z = Y[[c for c in byclause + [measure] + [x for x in Y.columns if l in x]]].copy(deep=True)
        # SOL2: compute the comparison here!
        Z[fun_name + "_" + l] = Y.apply(lambda x: using(x[measure], x[slice]), axis=1)
        # fix the column names
        Z.columns = [x.replace(benchmark, "comparison").replace(l, "bc_" + measure) for x in Z.columns]
        # append it to the diversified enhanced cubes
        Ys.append((Z, l))
    return Ys


def compute_using(Y, measure, using, compute_property=False):
    Z = Y.copy(deep=True)
    if isinstance(using, str):
        using = [x for x in usings if x.__name__ == json.loads(using)["fun"]][0]
    comp = [x for x in Y.columns if x.lower() == "bc_" + measure.lower()][0]
    if not compute_property:
        Z["comparison"] = using(Z[measure], Z[comp])
    else:
        orig_comparison = comp
        for column in [x for x in Y.columns if orig_comparison in x]:
            Z["comparison_" + using.__name__ + column.replace(orig_comparison, "")] = using(Z[measure], Z[column])
    return Z


def compute_auto_using(Y, k, measure, byclause):
    # iterate over the existing comparison functions
    for using in usings:
        # compute the comparison (including the property; e.g., consider both bc_quantity and bc_quantity_population
        Y = compute_using(Y, measure, using, compute_property=True)
    # select the column names on which diversification will be applied
    benchmarks = [x for x in Y.columns if "comparison_" in x]
    # transform such columns into numpy arrays
    X = np.array([[fun(Y[x]) for fun in features] for x in benchmarks])
    # apply diversification
    benchmarks = diversify(X, benchmarks, k)
    Ys = []
    # iterate over the diversified columns
    for benchmark in benchmarks:
        # get the function name
        l = benchmark.split("_")[1]
        # generate the new data frame
        Z = Y[[c for c in byclause + [measure, benchmark]]].copy(deep=True)
        # fix the column names
        Z.columns = [x.replace(benchmark, "comparison") for x in Z.columns]
        # append it to the diversified enhanced cubes
        Ys.append((Z, benchmark.replace("comparison_", "")))  # l
    return Ys


def compute_label(Y, label):
    if isinstance(label, str):
        label = [item for sublist in labels.values() for item in sublist if item.__name__ == label][0]
    Z = Y.copy(deep=True)
    Z["label"] = label(Z["comparison"])
    return Z


# def compute_auto_labels(Y):
#     Ys = []
#     curlabels = {}
#     for l in labels:
#         Z = compute_label(Y, l)
#         curlabels[l] = Z["label"]
#         Ys.append((Z, l))
#     # return Ys
#     df = pd.DataFrame(data=curlabels)
#     n = np.array(df)
#     result = rk.center(n, method='kendalltau')
#     rankedYs = []
#     for Z, l in Ys:
#         tau, p = sp.stats.kendalltau(Z["label"], result)
#         rankedYs.append((Z, l, tau))
#     rankedYs = sorted(rankedYs, key=lambda t: t[2], reverse=True)[:3]
#     return [(Z, l) for (Z, l, tau) in rankedYs]


def compute_auto_labels(Y, k, measure, byclause, using):
    def kendall_tau_distance(values1, values2):
        """Compute the Kendall tau distance."""
        n = len(values1)
        assert len(values2) == n, "Both lists have to be of equal length"
        i, j = np.meshgrid(np.arange(n), np.arange(n))
        a = np.argsort(values1)
        b = np.argsort(values2)
        ndisordered = np.logical_or(np.logical_and(a[i] < a[j], b[i] > b[j]), np.logical_and(a[i] > a[j], b[i] < b[j])).sum()
        return ndisordered  # / (n * (n - 1))

    for l in labels[json.loads(using)["fun"] if isinstance(using, str) and "{" in using else using.split("_")[0]]:
        Y = compute_label(Y, l)
        Y = Y.rename(columns={"label": "label_" + l.__name__})
    benchmarks = [x for x in Y.columns if "label_" in x]
    dictionary = {ni: indi for indi, ni in enumerate(sorted(set([item for sublist in [Y[x] for x in benchmarks] for item in sublist])))}
    X = np.array([Y[x].map(dictionary).to_numpy() for x in benchmarks])
    kmedoids = KMedoids(n_clusters=min(k, len(benchmarks)), init='k-medoids++', random_state=0, metric=kendall_tau_distance).fit(X)
    benchmarks = list(set([benchmarks[x] for x in kmedoids.medoid_indices_]))
    Ys = []
    for benchmark in benchmarks:
        l = benchmark.split("_")[1]
        Z = Y[[c for c in byclause + [measure, "comparison", benchmark]]].copy(deep=True)
        Z.columns = [x.replace(benchmark, "label") for x in Z.columns]
        Ys.append((Z, l))
    return Ys

def write_to_file(i, byclause, forclause, measure, df, sibling, using, label):
    if using is None:
        df = compute_using(df, measure, using if using is not None else difference)
    if label is None:
        df = compute_label(df, label if label is not None else quartile)
    byclause = [x for x in byclause if x not in forclause]
    if len(byclause) == 0:
        byclause = [forclause[0]]
    enhcube = {
        "raw": json.loads(df.to_json(orient="records", double_precision=5)),
        "dimensions": byclause,
        "measures": ["comparison"],
        "against": "'" + sibling + "'",
        "scaled": using.split("_")[1] if using is not None and "_" in using else "",
        "using": {"fun": (json.loads(using)["fun"] if isinstance(using, str) and "{" in using else using.split("_")[0]), "params": [measure, "benchmark." + measure]} if using is not None else {},
        # "using": {"fun": (json.loads(using)["fun"] if isinstance(using, str) else using.__name__), "params": [measure, "benchmark." + measure]} if using is not None else {},
        "label": label if label is not None else "",  # label.__name__ if label is not None else "",
        "def_using": difference.__name__ + "(" + measure + ", " + "benchmark." + measure + ")",
        "def_label": quartile.__name__
    }
    with open(args.path + "_" + str(i) + ".json", 'w') as f:
        f.write(json.dumps(enhcube).replace("\\", ""))


if __name__ == '__main__':
    ###############################################################################
    # PARAMETERS SETUP
    ###############################################################################
    toprint = {}
    parser = argparse.ArgumentParser()
    parser.add_argument("--curid", help="curid", type=str)
    parser.add_argument("--credentials", help="credentials", type=str)
    parser.add_argument("--sql", help="query to get the cube", type=str)
    parser.add_argument("--measure", help="measure", type=str)
    parser.add_argument("--byclause", help="group by", type=str)
    parser.add_argument("--forclause", help="selection clause", type=str)
    parser.add_argument("--cube", help="cube", type=str)
    parser.add_argument("--benchmark", help="benchmark", type=str)
    parser.add_argument("--using", help="using", type=str)
    parser.add_argument("--labels", help="labels", type=str)
    parser.add_argument("--k", help="number of diverse clauses", type=int)
    parser.add_argument("--path", help="output path", type=str)
    args = parser.parse_args()
    # print(args)
    credentials = json.loads(args.credentials)
    sql = args.sql.replace("?", "\"").replace("!", " ")
    sql = sql[1:-1] if platform.system() == 'Linux' else sql
    # print(sql)
    # sys.exit(1)
    k = args.k
    toprint["curid"] = args.curid
    toprint["k"] = k
    toprint["cube"] = args.cube.upper()
    measure = args.measure.upper()
    toprint["measure"] = measure
    byclause = [x.upper() for x in args.byclause.split(',')]
    toprint["byclause"] = '"' + str(byclause) + '"'
    toprint["nparents"] = len([x for x in byclause if x != ""])
    forclause = [x.upper() for x in args.forclause[1:-1].split(",")]
    toprint["forclause"] = '"' + str(forclause) + '"'
    toprint["nsiblings"] = len([x for x in forclause if x != ""])
    toprint["id"] = '"' + (str(toprint["measure"]) + "-" + str(toprint["byclause"]) + "-" + str(toprint["forclause"])).replace('"', '') + '"'
    benchmark = args.benchmark.replace("!", " ") if args.benchmark != "0" else None
    toprint["benchmark_time"] = -1
    using = args.using if args.using != "null" and args.using != "{}" else None
    toprint["comparison_time"] = -1
    label = args.labels if args.labels != "null" and args.labels != "[]" else None
    toprint["label_time"] = -1
    toprint["benchmark"] = 0 if benchmark is None else 1
    toprint["comparison"] = 0 if using is None else 1
    toprint["label"] = 0 if label is None else 1
    toprint["sql"] = '"' + sql.replace('"', '""') + '"'

    cx_Oracle.init_oracle_client(lib_dir=credentials["oracleclient"])
    dsn_tns = cx_Oracle.makedsn(credentials["ip"], credentials["port"], credentials["metadata"])
    connection = cx_Oracle.connect(credentials["user"], credentials["pwd"], dsn_tns)

    sibling = ""
    start_time = time.time()
    if benchmark is not None and "(" in benchmark:  # check whether this is a sibling
        attr, op, sibling = splitAttr(benchmark)
    else:
        sibling = benchmark  # else is a parent
    bc = 0  # number of benchmarks
    cm = 0  # number of comparisons
    if benchmark is None:  # if no benchmark has been specified
        i = 0  # file id
        dfs = compute_auto_benchmark_sql(sql, k, measure, byclause)  # compute the most diverse benchmarks
        for df in dfs:  # and write them to file
            toprint["card"] = len(df[0].index)
            write_to_file(i, byclause, forclause, measure, df[0], df[1], None, None)
            i += 1
            bc += 1
    else:  # if the benchmark has been already specified
        if os.path.isfile(args.path):  # if the file exists, then the benchmark has already been created
            data = json.load(open(args.path))['raw']  # I simply need to read it
            df = pd.DataFrame.from_records(data)  # and to get the dataframe
        else:  # the benchmark has not been created yet
            df = clean_sql_df(pd.read_sql(sql, con=connection))  # I need to run the SQL query
        toprint["card"] = len(df.index)
    toprint["benchmark_time"] = time.time() - start_time
    start_time = time.time()
    if bc <= 1:  # if no or a single refinement has been done
        if using is None:  # if no comparison has been specified
            if bc == 1:  # if refinement
                sibling = dfs[0][1]  # get the benchmark from the previous refinement
                df = dfs[0][0]  # take it
            i = 0
            dfs = compute_auto_using(df, k, measure, byclause)  # compute the comparisons , benchmark=sibling
            for df in dfs:
                write_to_file(i, byclause, forclause, measure, df[0], sibling, df[1], None)
                i += 1
                cm += 1
        elif "comparison" not in df.columns:  # if the comparison has been specified but has not been already computed
            df = compute_using(df, measure, using)  # , comp=sibling
    toprint["comparison_time"] = time.time() - start_time
    start_time = time.time()
    if bc <= 1 and cm <= 1:   # if no or a single refinement has been done
        i = 0
        if label is None:  # if no benchmark has been specified
            if cm == 1:  # if the benchmark comes from the previous refinement
                using = dfs[0][1]  # if refinement
                df = dfs[0][0]  # take it
            dfs = compute_auto_labels(df, k, measure, byclause, using)
            for df in dfs:
                write_to_file(i, byclause, forclause, measure, df[0], sibling, using, df[1])
                i += 1
        elif "label" not in df.columns:   # if the label has been specified but has not been already computed
            df = compute_label(df, label)
            write_to_file(i, byclause, forclause, measure, df, sibling, using, label)
    toprint["label_time"] = time.time() - start_time

    exists = os.path.exists('resources/assess/time.csv')
    with open("resources/assess/time.csv", 'a+') as o:
        header = []
        values = []
        for key, value in toprint.items():
            header.append(key)
            values.append(str(value))
        if not exists:
            o.write(','.join(header) + "\n")
        o.write(','.join(values) + "\n")
