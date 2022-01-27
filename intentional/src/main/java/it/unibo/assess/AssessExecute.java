package it.unibo.assess;

import com.google.common.collect.Lists;
import edu.stanford.nlp.util.Sets;
import it.unibo.antlr.gen.AssessLexer;
import it.unibo.antlr.gen.AssessParser;
import it.unibo.antlr.gen.ThrowingErrorListener;
import it.unibo.conversational.Utils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.unibo.conversational.Utils.*;

/**
 * Assess operator in action
 */
public class AssessExecute {
    private static final Logger L = LoggerFactory.getLogger(AssessExecute.class);

    /**
     * Parse intention from text.
     *
     * @param input current operator
     * @return new operator
     */
    public static Assess parse(final String input) {
        return parse(null, Assess.ExecutionPlan.JOININMEMORY, input, new String[0]);
    }

    /**
     * Parse intention from text.
     *
     * @param input current operator
     * @return new operator
     */
    public static Assess parse(final String input, final Assess.ExecutionPlan plan, final String... usedIndexes) {
        return parse(null, plan, input, usedIndexes);
    }

    /**
     * Parse intention from text.
     *
     * @param a     previous intention
     * @param input current operator
     * @return new operator
     */
    public static Assess parse(final Assess a, final String input, final String... usedIndexes) {
        return parse(a, Assess.ExecutionPlan.JOININMEMORY, input, usedIndexes);
    }

    /**
     * Parse intention from text.
     *
     * @param a     previous intention
     * @param input current operator
     * @return new operator
     */
    public static Assess parse(final Assess a, final Assess.ExecutionPlan plan, final String input, final String... usedIndexes) {
        final AssessLexer lexer = new AssessLexer(new ANTLRInputStream(input)); // new ANTLRInputStream(System.in);
        lexer.addErrorListener(new ThrowingErrorListener());
        final CommonTokenStream tokens = new CommonTokenStream(lexer); // create a buffer of tokens pulled from the lexer
        final AssessParser parser = new AssessParser(tokens); // create a parser that feeds off the tokens buffer
        parser.addErrorListener(new ThrowingErrorListener());
        final ParseTree tree = parser.assess(); // begin parsing at init rule
        final CustomAssessVisitor v = new CustomAssessVisitor(a == null ? new Assess(plan, usedIndexes) : new Assess(a, plan, usedIndexes));
        v.visit(tree);
        return v.getAssess();
    }

    public static JSONObject execute(Assess d, final String path) throws Exception {
        return execute(d, path, "src/main/python/");
    }

    private static JSONObject getMC(final String agg, final String mea, final String as) {
        JSONObject mc = new JSONObject();
        if (agg != null) {
            put(mc, Type.AGG, agg);
        }
        if (as != null) {
            put(mc, "AS", as);
        }
        put(mc, Type.MEA, mea);
        return mc;
    }

    public static JSONObject execute(final Assess d, final String path, final String pythonPath) throws Exception {
        final long timeCube;
        if (d.getBenchmarkType().equals(Assess.BenchmarkType.TARGET)) {
            timeCube = d.writeMultidimensionalCube(path, d.getJSON(), "");
            d.setTimeCube(timeCube);
        } else {
            final JSONObject query = new JSONObject();
            switch (d.getExecutionPlan()) {
                case JOININMEMORY:
                    final long timeBenchmark = d.writeMultidimensionalCube(path, d.createBenchmark().getJSON(), "bc");
                    d.setTimeBenchmark(timeBenchmark);
                    timeCube = d.writeMultidimensionalCube(path, d.getJSON(), "");
                    d.setTimeCube(timeCube);
                    break;
                case JOININDBMS:
                    // use the cube and benchmark as nested queries
                    final JSONObject target = d.getJSON();
                    final JSONObject benchmark = d.createBenchmark().getJSON();
                    // TODO: IDKW, but on SSB100 Oracle is not picking the view, have to manually add it
                    if (d.getBenchmarkType().equals(Assess.BenchmarkType.PAST)) {
                        put(target, "HINT", "/*+ REWRITE(MV_MY_NPR_SR) */");
                        put(benchmark, "HINT", "/*+ REWRITE(MV_MY_NPR_SR) */");
                    }
                    append(query, "FROM", target); // t1
                    append(query, "FROM", benchmark); // t2
                    // select attributes, measure, and properties
                    Sets.union(Sets.union(d.getAttributes(), d.getProperties()), d.getMeasures()).forEach(a -> {
                        append(query, Type.MC, getMC(null, "t1." + a, a));
                        append(query, Type.MC, getMC(null, "t2." + a, "bc_" + a));
                    });
                    // Join on attributes that do not belong to the benchmark
                    d.getAttributes().forEach(a -> {
                        if ((d.getBenchmarkType().equals(Assess.BenchmarkType.SIBLING) && !a.equalsIgnoreCase(((Triple<String, String, List<String>>) d.getBenchmark()).getLeft()))
                                || (d.getBenchmarkType().equals(Assess.BenchmarkType.PAST) && !(a.toLowerCase().contains("date") || a.toLowerCase().contains("month") || a.toLowerCase().contains("year")))) {
                            append(query, "JOIN", a);
                        }
                    });
                    String sql = Utils.createQuery(d.getCube(), query);
                    // TODO: this is a trick to force materialized views on Oracle (IDKW)
                    if (d.getIndexes().contains("mv")) {
                        for (int scaleFactor : Lists.newArrayList(1, 2, 10, 100)) {
                            sql = sql.replaceAll("CUSTOMER" + scaleFactor + ".NATION = 'GERMANY'", "(CUSTOMER" + scaleFactor + ".NATION = 'GERMANY' or CUSTOMER" + scaleFactor + ".NATION = 'GERMANY')");
                            sql = sql.replaceAll("CUSTOMER" + scaleFactor + ".NATION = 'FRANCE'", "(CUSTOMER" + scaleFactor + ".NATION = 'FRANCE' or CUSTOMER" + scaleFactor + ".NATION = 'FRANCE')");
                        }
                    }
                    timeCube = d.writeMultidimensionalCube(path, sql, "");
                    d.setTimeCube(timeCube);
                    break;
                case PIVOTMV:
                case PIVOT:
                    final Triple<String, String, List<String>> bnch = d.getBenchmarkType().equals(Assess.BenchmarkType.SIBLING) //
                            ? (Triple<String, String, List<String>>) d.getBenchmark() //
                            : d.getClauses().stream().filter(c -> c.getLeft().toLowerCase().contains("month") || c.getLeft().toLowerCase().contains("date") || c.getLeft().toLowerCase().contains("year")).findAny().get();
                    final Triple<String, String, List<String>> targetSibling = d.getClauses().stream().filter(c -> c.getLeft().equals(bnch.getLeft())).findAny().get();
                    final JSONObject cube = d.createBenchmark().getJSON();
                    // TODO: IDKW, but on SSB100 Oracle is not picking the view, have to manually add it
                    if (d.getBenchmarkType().equals(Assess.BenchmarkType.PAST)) {
                        put(cube, "HINT", "/*+ REWRITE(MV_MY_NPR_SR) */");
                    }
                    if (d.getExecutionPlan().equals(Assess.ExecutionPlan.PIVOTMV)) {
                        append(cube, Type.GC, targetSibling.getLeft());
                        append(query, "FROM", cube);
                    } else {
                        // in the selection clause goes everything
                        put(query, Type.SC, cube.getJSONArray(quote(Type.SC)));
                    }
                    // in the gc goes all the attributes that are not benchmark
                    cube.getJSONArray(quote(Type.GC)).forEach(l -> {
                        if (!l.equals(quote(bnch.getLeft()))) {
                            append(query, Type.GC, unquote(l));
                        }
                    });

                    if (d.getBenchmarkType().equals(Assess.BenchmarkType.SIBLING)) { // benchmark cube
                        // measures are duplicated for each sibling
                        cube.getJSONArray(quote(Type.MC)).forEach(o -> {
                            final JSONObject m = new JSONObject(o.toString());
                            final String measure = unquote(m.getString(quote(Type.MEA)));
                            final boolean isAssessedMeasure = measure.toLowerCase().equals(d.getMeasures().stream().findFirst().get().toLowerCase());
                            put(m, Type.MEA, toIf(d.getCube(), targetSibling.getLeft() + "=" + targetSibling.getRight().get(0), measure));
                            append(query, Type.MC, m);

                            final JSONObject otherm = new JSONObject(o.toString());
                            put(otherm, Type.MEA, toIf(d.getCube(), bnch.getLeft() + "=" + bnch.getRight().get(0), measure));
                            put(otherm, "AS", "bc_" + unquote(otherm.getString(quote("AS"))));
                            append(query, Type.MC, otherm);

                            if (isAssessedMeasure) {
                                put(query, "HAVING", jsonMeasureToString(m, false) + " is not null and " + jsonMeasureToString(otherm, false) + " is not null");
                            }
                        });
                        append(query, Type.MC, getMC(null, targetSibling.getRight().get(0), targetSibling.getLeft()));
                        append(query, Type.MC, getMC(null, bnch.getRight().get(0), "bc_" + bnch.getLeft()));

                        // properties are duplicated for each sibling and added only to the SELECT clause
                        cube.getJSONArray(quote("PROPERTIES")).forEach(o -> {
                            final JSONObject p = new JSONObject();
                            put(p, Type.AGG, "max");
                            put(p, Type.MEA, toIf(d.getCube(), bnch.getLeft() + "=" + targetSibling.getRight().get(0), unquote(o)));
                            put(p, "AS", unquote(o));
                            append(query, Type.MC, p);

                            final JSONObject q = new JSONObject();
                            put(q, Type.AGG, "max");
                            put(q, Type.MEA, toIf(d.getCube(), bnch.getLeft() + "=" + bnch.getRight().get(0), unquote(o)));
                            put(q, "AS", "bc_" + unquote(o));
                            append(query, Type.MC, q);
                        });
                    } else if (d.getBenchmarkType().equals(Assess.BenchmarkType.PAST)) { // benchmark cube
                        final JSONObject fakeMeasure = new JSONObject();
                        put(fakeMeasure, Type.MEA, bnch.getRight().get(0));
                        put(fakeMeasure, "AS", bnch.getLeft());
                        append(query, Type.MC, fakeMeasure);

                        JSONObject assessMeasure = null;
                        String havingPrefix = "";
                        String having = "";
                        // all the measures are added for the current slice
                        for (final Object o : cube.getJSONArray(quote(Type.MC))) {
                            final JSONObject m = new JSONObject(o.toString());
                            final String measure = unquote(m.getString(quote(Type.MEA)));
                            final boolean isAssessedMeasure = measure.toLowerCase().equals(d.getMeasures().stream().findFirst().get().toLowerCase());
                            if (isAssessedMeasure) {
                                assessMeasure = new JSONObject(m.toString());
                            }
                            put(m, Type.MEA, toIf(d.getCube(), bnch.getLeft() + "=" + bnch.getRight().get(0), measure));
                            append(query, Type.MC, m);
                            if (isAssessedMeasure) {
                                havingPrefix = jsonMeasureToString(m, false);
                            }
                        }

                        // the assessed measure is added for each slice
                        final int pastSlices = (Integer) d.getBenchmark();
                        for (int i = 1; i <= pastSlices; i++) {
                            final JSONObject m = new JSONObject(assessMeasure.toString());
                            final String measure = unquote(m.getString(quote(Type.MEA)));
                            put(m, Type.MEA, toIf(d.getCube(), toDate(d.getCube(), bnch.getLeft(), bnch.getLeft()) + "=" + toInterval(d.getCube(), bnch.getLeft(), bnch.getRight().get(0), i), measure));
                            put(m, "AS", "bc_" + (pastSlices + 1 - i));
                            append(query, Type.MC, m);
                            having += (having.isEmpty() ? "" : " or ") + jsonMeasureToString(m, false) + " is not null";
                        }
                        put(query, "PROPERTIES", cube.getJSONArray(quote("PROPERTIES")));
                        put(query, "HAVING", havingPrefix + "is not null and (" + having + ")");
                    }
                    timeCube = d.writeMultidimensionalCube(path, Utils.createQuery(d.getCube(), query), "");
                    d.setTimeCube(timeCube);
                    break;
            }
        }
        d.computePython(pythonPath, path, "assess.py");
        return new JSONObject();
    }
}
