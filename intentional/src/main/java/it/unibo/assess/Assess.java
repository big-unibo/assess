package it.unibo.assess;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unibo.Intention;
import it.unibo.conversational.Utils;
import it.unibo.conversational.database.QueryGenerator;
import it.unibo.conversational.datatypes.DependencyGraph;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static it.unibo.conversational.Utils.*;

/**
 * Assess intentional operator.
 */
public class Assess extends Intention {
    public final String[] usedIndexes;
    protected Object benchmark;
    protected Set<Triple<Double, Double, String>> labelingSchema = Sets.newLinkedHashSet();
    protected String labelingFunction;
    protected BenchmarkType benchmarkType;
    protected JSONObject function;
    private long timeBenchmark;
    private long timeCube;
    public static int id = 0;
    protected String scaled;

    public void setScaled(String text) {
        this.scaled = text;
    }

    public String getLabelingFunction() {
        return labelingFunction;
    }

    public Set<Triple<Double, Double, String>> getLabelingSchema() {
        return labelingSchema;
    }

    public ExecutionPlan getExecutionPlan() {
        return plan;
    }

    public Set<String> getIndexes() {
        return Sets.newLinkedHashSet(Arrays.asList(usedIndexes));
    }

    public enum ExecutionPlan {
        JOININMEMORY,
        JOININDBMS,
        PIVOT,
        PIVOTMV
    }

    public Assess(final Intention i, final ExecutionPlan plan, final String[] usedIndexes) {
        super(i, false);
        this.plan = plan;
        this.usedIndexes = usedIndexes;
        this.scaled = "";
    }

    public Assess(final ExecutionPlan plan, final String[] usedIndexes) {
        super(null, false);
        this.plan = plan;
        this.usedIndexes = usedIndexes;
        this.scaled = "";
    }

    public Assess(final Intention i, final ExecutionPlan plan) {
        this(i, plan, new String[0]);
    }

    public Assess(final ExecutionPlan plan) {
        this(plan, new String[0]);
    }

    @Override
    public String toPythonCommand(String commandPath, String path) {
        final int sessionStep = getSessionStep();
        final String filename = getFilename();
        final String fullCommand = commandPath.replace("/", File.separator)//
                + " --path " + (path.contains(" ") ? "\"" : "") + path.replace("\\", "/") + (path.contains(" ") ? "\"" : "")//
                + " --file " + filename //
                + " --session_step " + sessionStep //
                + " --distance_function " + getFunction() //
                + " --measure " + getMeasures().stream().findFirst().get() //
                + " --benchmark_type " + getBenchmarkType() //
                + " --benchmark " + getBenchmark() //
                + " --cube " + getJSON() //
                + " --time_benchmark " + timeBenchmark //
                + " --time_cube " + timeCube //
                + " --id " + id //
                + " --plan " + plan.toString() //
                + " --dbms " + cube.getDbms() //
                + " --indexes " + (usedIndexes.length == 0 ? "none" : usedIndexes[0]) //
                + (Intention.DEBUG ? " --save y" : "")
                + (labelingFunction == null || labelingFunction.isEmpty() ? "" : " --labeling_schema " + labelingFunction)
                + (labelingSchema.isEmpty() ? "" : " --labeling_schema " + labelingSchema.stream().map(Object::toString).reduce((a, b) -> a + ";" + b).get());
        L.warn(fullCommand);
        return fullCommand;
    }

    public BenchmarkType getBenchmarkType() {
        if (benchmarkType == null) {
            return BenchmarkType.TARGET;
        }
        return benchmarkType;
    }

    public String getScaled() {
        return scaled;
    }

    public String getFunction() {
        if (function == null) {
            return new JSONObject().toString();
        }
        return function.toString();
    }

    public JSONObject getFunctionJSON() {
        return function;
    }

    /**
     * Given some nested functions, return them as a human readable string.
     * <p>
     * E.g., given
     *      { "fun": "diff", "params": [ {"fun": "diff", "params": [ "a", "b" ]}, "c" ]}
     * return
     *      diff(diff(a, b), c)
     *
     * @param fun JSON object with nested functions
     * @return human readable string
     */
    public String getFunctionAsString(final JSONObject fun) {
        String s = fun.getString(Utils.quote("fun"));
        final List<Object> params = fun.getJSONArray(Utils.quote("params")).toList();
        final String p = params.stream().map(i -> {
            if (i instanceof JSONObject) {
                return getFunctionAsString((JSONObject) i);
            } else {
                return i.toString();
            }
        }).reduce((a, b) -> a + ", " + b).get();
        return s + "(" + p + ")";
    }

    public void setFunction(final JSONObject fun) {
        this.function = fun;
    }

    public JSONObject appendFunction(final String name, final List<JSONObject> params) {
        final JSONObject nestedFunction = new JSONObject();
        nestedFunction.put(Utils.quote("fun"), Utils.quote(name));
        final JSONArray nestedFunctionParams = new JSONArray();
        for (final JSONObject p : params) {
            nestedFunctionParams.put(p);
        }
        nestedFunction.put(Utils.quote("params"), nestedFunctionParams);
        return nestedFunction;
    }

    public JSONObject prepareFunction(final String name, final Object[] params) {
        final JSONObject nestedFunction = new JSONObject(); // create an object for a nested function
        nestedFunction.put(Utils.quote("fun"), Utils.quote(name)); // put "fun", the function name
        final JSONArray nestedFunctionParams = new JSONArray(); // accumulate the parameters
        for (final Object p : params) { // iterate over the parameters
            final String pp = p.toString().replace(getCubeName() + ".", ""); // hide the cube name (but not the benchmark)
            final String s = pp.replace("benchmark.", ""); // ... hide the benchmark (need to check real values and attributes)
            try {
                nestedFunctionParams.put(Double.parseDouble(s)); // if it is a double value, just add it to the list. It will be handled in python
            } catch (final Exception e) {
                if (QueryGenerator.getLevel(cube, s, false) != null) { // if it is a level, check if it is functionally determined by the coordinate
                    if (getAttributes().stream().noneMatch(att -> DependencyGraph.lca(getCube(), s, att).or("foo").equals(att))) {
                        throw new IllegalArgumentException(s + " should be functionally determined by at least an attribute");
                    }
                    addAttribute(false, s); // add it to the group by set, since it will be used
                } else {
                    addMeasures(s);
                }
                nestedFunctionParams.put(Utils.quote(pp)); // in the function object, I need "benchmark." to identify the column of the extended cube
            }
        }
        nestedFunction.put(Utils.quote("params"), nestedFunctionParams);
        return nestedFunction;
    }

    private final ExecutionPlan plan;

    public ExecutionPlan getPlan() {
        return plan;
    }

    public enum BenchmarkType {
        TARGET,
        SIBLING,
        PARENT,
        PAST
    }

    /**
     * @param label label to add (from, to, label)
     */
    public void addLabel(final Triple<Double, Double, String> label) {
        this.labelingSchema.add(label);
    }

    /**
     * @return benchmark cube
     */
    public Object getBenchmark() {
        if (benchmarkType == null) {
            return 0;
        } else {
            return benchmark;
        }
    }

    /**
     * @param benchmark benchmark function
     */
    public void setBenchmark(final Object benchmark) {
        this.benchmark = benchmark;
        if (benchmark instanceof Triple) {
            final String attr = ((Triple) benchmark).getLeft().toString();
            if (getAttributes().stream().noneMatch(a -> a.equalsIgnoreCase(attr))) {
                throw new IllegalArgumentException("The Sibling '" + benchmark + "' does not refer to a valid level. The list of levels is " + getAttributes());
            } else if (getClauses().stream().noneMatch(a -> a.getLeft().equalsIgnoreCase(attr))) {
                throw new IllegalArgumentException("The Sibling '" + benchmark + "' does not refer to any selection in the for clause. The for clause contains " + getClauses());
            }
        } else if (benchmark instanceof String) {
            if (getAttributes().stream().filter(a -> DependencyGraph.lca(cube, a, benchmark.toString()).isPresent()).count() == 0) {
                throw new IllegalArgumentException("The Parent '" + benchmark + "' is not parent of any level. The list of levels is " + getAttributes());
            }
        }
    }

    /**
     * @param benchmarkType benchmark type
     */
    public void setBenchmarkType(final BenchmarkType benchmarkType) {
        this.benchmarkType = benchmarkType;
    }

    /**
     * @param labelingFunction labeling function
     */
    public void setLabelingFunction(final String labelingFunction) {
        this.labelingFunction = labelingFunction;
    }

    /**
     * @param time elapsed time
     */
    public void setTimeBenchmark(final long time) {
        this.timeBenchmark = time;
    }

    /**
     * @param time elapsed time
     */
    public void setTimeCube(final long time) {
        this.timeCube = time;
    }

    /**
     * Create the ASSESS intention for the benchmark
     * @return the benchmark intention
     */
    public Assess createBenchmark() {
        Assess tmp = new Assess(this, plan);
        tmp.setMeasures(getMeasures());
        final Triple<String, String, List<String>> r =
                tmp.removeClause(t -> {
                    if (benchmarkType.equals(BenchmarkType.SIBLING)) {
                        return t.getLeft().equals(((Triple<String, String, List<String>>) benchmark).getLeft());
                    } else if (benchmarkType.equals(BenchmarkType.PAST)) {
                        return t.getMiddle().equals("=") && (t.getLeft().toLowerCase().contains("date") || t.getLeft().toLowerCase().contains("month") || t.getLeft().toLowerCase().contains("year"));
                    } else {
                        throw new IllegalArgumentException("Cannot remove anything for " + benchmarkType);
                    }
                });
        final List<String> pastSlice = Lists.newLinkedList(r.getRight());
        if (benchmarkType.equals(BenchmarkType.SIBLING)) {
            final Triple<String, String, List<String>> triple = (Triple<String, String, List<String>>) benchmark;
            switch (plan) {
                case JOININDBMS:
                case JOININMEMORY:
                    tmp.addClause(triple);
                    break;
                case PIVOTMV:
                case PIVOT:
                    pastSlice.add(triple.getRight().get(0));
                    tmp.addClause(Triple.of(r.getLeft(), "IN", pastSlice));
                    break;
            }
        } else if (benchmarkType.equals(BenchmarkType.PAST)) {
            final String prevSlice = pastSlice.remove(0);
            pastSlice.add(toInterval(cube, r.getLeft(), r.getRight().get(0), (Integer) getBenchmark() + 1));
            switch (plan) {
                case JOININDBMS:
                case JOININMEMORY:
                    pastSlice.add(toInterval(cube, r.getLeft(), r.getRight().get(0), 1));
                    break;
                case PIVOTMV:
                case PIVOT:
                    pastSlice.add(toDate(cube, r.getLeft(), prevSlice));
                    break;
            }
            tmp.addClause(Triple.of(r.getLeft(), "BETWEEN", pastSlice));
        }
        return tmp;
    }
}
