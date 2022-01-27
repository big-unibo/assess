package it.unibo.assess;

import it.unibo.antlr.gen.AssessBaseVisitor;
import it.unibo.antlr.gen.AssessParser;
import it.unibo.conversational.datatypes.DependencyGraph;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONObject;

import java.util.stream.Collectors;

/** How to interpret the Assess syntax. */
public class CustomAssessVisitor extends AssessBaseVisitor<JSONObject> {
    private final Assess a;

    public Assess getAssess() {
        return a;
    }

    public CustomAssessVisitor(final Assess a) {
        this.a = a;
    }

    @Override
    public JSONObject visitAssess(AssessParser.AssessContext ctx) {
        a.setCube(ctx.cube.name);
        a.addAttribute(true, ctx.gc.stream().map(i -> i.name).toArray());
        if (ctx.sc != null) {
            visit(ctx.sc);
        }
        a.addMeasures(ctx.mcs.stream().map(i -> i.name).toArray());
        if (ctx.bc != null) {
            visit(ctx.bc);
        }
        if (ctx.as != null) {
            a.setFunction(visit(ctx.as));
        }
        if (ctx.l != null) {
            visit(ctx.l);
        }
        return null;
    }

    @Override
    public JSONObject visitNestedFunction(AssessParser.NestedFunctionContext ctx) {
        return a.appendFunction(ctx.fun.name, ctx.functions.stream().map(this::visit).collect(Collectors.toList()));
    }

    @Override
    public JSONObject visitParametricFunction(AssessParser.ParametricFunctionContext ctx) {
        return a.prepareFunction(ctx.fun.name, ctx.params.stream().map(Token::getText).toArray());
    }

    @Override
    public JSONObject visitTarget(AssessParser.TargetContext ctx) {
        a.setBenchmarkType(Assess.BenchmarkType.TARGET);
        a.setBenchmark(Double.parseDouble(ctx.val.getText()));
        return null;
    }

    @Override
    public JSONObject visitSibling(AssessParser.SiblingContext ctx) {
        final String attr = ctx.attr.name;
        a.setBenchmarkType(Assess.BenchmarkType.SIBLING);
        a.setBenchmark(Triple.of(attr, ctx.op.getText(), ctx.val.stream().map(RuleContext::getText).collect(Collectors.toList())));
        if (ctx.weight != null) {
            a.setScaled(ctx.weight.getText());
        }
        return null;
    }

    @Override
    public JSONObject visitParent(AssessParser.ParentContext ctx) {
        a.setBenchmarkType(Assess.BenchmarkType.PARENT);
        a.setBenchmark(ctx.attr.name);
        if (ctx.weight != null) {
            a.setScaled(ctx.weight.getText());
        }
        return null;
    }

    @Override
    public JSONObject visitPast(AssessParser.PastContext ctx) {
        a.setBenchmarkType(Assess.BenchmarkType.PAST);
        a.setBenchmark(Integer.parseInt(ctx.val.getText()));
        return null;
    }

    @Override
    public JSONObject visitLabel_int(AssessParser.Label_intContext ctx) {
        a.setLabelingFunction(ctx.lbl.name);
        return null;
    }

    @Override
    public JSONObject visitLabel_fun(AssessParser.Label_funContext ctx) {
        a.addLabel(Triple.of(Double.parseDouble(ctx.fr.getText().replace("inf", "Infinity")), Double.parseDouble(ctx.to.getText().replace("inf", "Infinity")), ctx.l.name));
        return null;
    }

    @Override
    public JSONObject visitCondition(AssessParser.ConditionContext ctx) {
        a.addClause(Triple.of(ctx.attr.getText(), ctx.op == null ? ctx.in.getText() : ctx.op.getText(), ctx.val.stream().map(RuleContext::getText).collect(Collectors.toList())));
        return null;
    }
}
