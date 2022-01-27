// Generated from Assess.g4 by ANTLR 4.8

package it.unibo.antlr.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AssessParser}.
 */
public interface AssessListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AssessParser#assess}.
	 * @param ctx the parse tree
	 */
	void enterAssess(AssessParser.AssessContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#assess}.
	 * @param ctx the parse tree
	 */
	void exitAssess(AssessParser.AssessContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#id}.
	 * @param ctx the parse tree
	 */
	void enterId(AssessParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#id}.
	 * @param ctx the parse tree
	 */
	void exitId(AssessParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code target}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void enterTarget(AssessParser.TargetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code target}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void exitTarget(AssessParser.TargetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sibling}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void enterSibling(AssessParser.SiblingContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sibling}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void exitSibling(AssessParser.SiblingContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parent}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void enterParent(AssessParser.ParentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parent}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void exitParent(AssessParser.ParentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code past}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void enterPast(AssessParser.PastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code past}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 */
	void exitPast(AssessParser.PastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nestedFunction}
	 * labeled alternative in {@link AssessParser#function}.
	 * @param ctx the parse tree
	 */
	void enterNestedFunction(AssessParser.NestedFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nestedFunction}
	 * labeled alternative in {@link AssessParser#function}.
	 * @param ctx the parse tree
	 */
	void exitNestedFunction(AssessParser.NestedFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parametricFunction}
	 * labeled alternative in {@link AssessParser#function}.
	 * @param ctx the parse tree
	 */
	void enterParametricFunction(AssessParser.ParametricFunctionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parametricFunction}
	 * labeled alternative in {@link AssessParser#function}.
	 * @param ctx the parse tree
	 */
	void exitParametricFunction(AssessParser.ParametricFunctionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code label_int}
	 * labeled alternative in {@link AssessParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel_int(AssessParser.Label_intContext ctx);
	/**
	 * Exit a parse tree produced by the {@code label_int}
	 * labeled alternative in {@link AssessParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel_int(AssessParser.Label_intContext ctx);
	/**
	 * Enter a parse tree produced by the {@code useless}
	 * labeled alternative in {@link AssessParser#label}.
	 * @param ctx the parse tree
	 */
	void enterUseless(AssessParser.UselessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code useless}
	 * labeled alternative in {@link AssessParser#label}.
	 * @param ctx the parse tree
	 */
	void exitUseless(AssessParser.UselessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code label_fun}
	 * labeled alternative in {@link AssessParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterLabel_fun(AssessParser.Label_funContext ctx);
	/**
	 * Exit a parse tree produced by the {@code label_fun}
	 * labeled alternative in {@link AssessParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitLabel_fun(AssessParser.Label_funContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#clause}.
	 * @param ctx the parse tree
	 */
	void enterClause(AssessParser.ClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#clause}.
	 * @param ctx the parse tree
	 */
	void exitClause(AssessParser.ClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(AssessParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(AssessParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(AssessParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(AssessParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterComparator(AssessParser.ComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitComparator(AssessParser.ComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#binary}.
	 * @param ctx the parse tree
	 */
	void enterBinary(AssessParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#binary}.
	 * @param ctx the parse tree
	 */
	void exitBinary(AssessParser.BinaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssessParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(AssessParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssessParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(AssessParser.BoolContext ctx);
}