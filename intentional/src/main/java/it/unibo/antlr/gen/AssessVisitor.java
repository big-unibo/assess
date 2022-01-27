// Generated from Assess.g4 by ANTLR 4.8

package it.unibo.antlr.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AssessParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AssessVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AssessParser#assess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssess(AssessParser.AssessContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(AssessParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code target}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTarget(AssessParser.TargetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code sibling}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSibling(AssessParser.SiblingContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parent}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParent(AssessParser.ParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code past}
	 * labeled alternative in {@link AssessParser#benchmark}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPast(AssessParser.PastContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedFunction}
	 * labeled alternative in {@link AssessParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedFunction(AssessParser.NestedFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parametricFunction}
	 * labeled alternative in {@link AssessParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametricFunction(AssessParser.ParametricFunctionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code label_int}
	 * labeled alternative in {@link AssessParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel_int(AssessParser.Label_intContext ctx);
	/**
	 * Visit a parse tree produced by the {@code useless}
	 * labeled alternative in {@link AssessParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUseless(AssessParser.UselessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code label_fun}
	 * labeled alternative in {@link AssessParser#interval}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel_fun(AssessParser.Label_funContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClause(AssessParser.ClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(AssessParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(AssessParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparator(AssessParser.ComparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#binary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinary(AssessParser.BinaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssessParser#bool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool(AssessParser.BoolContext ctx);
}