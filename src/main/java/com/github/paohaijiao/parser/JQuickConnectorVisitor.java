// Generated from D:/idea/jthornruleGrammer/connector/JQuickConnector.g4 by ANTLR 4.13.2
package com.github.paohaijiao.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JQuickConnectorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JQuickConnectorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#select}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect(JQuickConnectorParser.SelectContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#fieldMapping}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldMapping(JQuickConnectorParser.FieldMappingContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(JQuickConnectorParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#targetField}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTargetField(JQuickConnectorParser.TargetFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fieldProcessor}
	 * labeled alternative in {@link JQuickConnectorParser#processor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldProcessor(JQuickConnectorParser.FieldProcessorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jsonPathProcessor}
	 * labeled alternative in {@link JQuickConnectorParser#processor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJsonPathProcessor(JQuickConnectorParser.JsonPathProcessorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#connector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnector(JQuickConnectorParser.ConnectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#connectorCode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnectorCode(JQuickConnectorParser.ConnectorCodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(JQuickConnectorParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(JQuickConnectorParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#columnName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumnName(JQuickConnectorParser.ColumnNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JQuickConnectorParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(JQuickConnectorParser.VarContext ctx);
}