// Generated from D:/idea/jthornruleGrammer/connector/JQuickConnector.g4 by ANTLR 4.13.2
package com.github.paohaijiao.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JQuickConnectorParser}.
 */
public interface JQuickConnectorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#select}.
	 * @param ctx the parse tree
	 */
	void enterSelect(JQuickConnectorParser.SelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#select}.
	 * @param ctx the parse tree
	 */
	void exitSelect(JQuickConnectorParser.SelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#fieldMapping}.
	 * @param ctx the parse tree
	 */
	void enterFieldMapping(JQuickConnectorParser.FieldMappingContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#fieldMapping}.
	 * @param ctx the parse tree
	 */
	void exitFieldMapping(JQuickConnectorParser.FieldMappingContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(JQuickConnectorParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(JQuickConnectorParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#targetField}.
	 * @param ctx the parse tree
	 */
	void enterTargetField(JQuickConnectorParser.TargetFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#targetField}.
	 * @param ctx the parse tree
	 */
	void exitTargetField(JQuickConnectorParser.TargetFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code fieldProcessor}
	 * labeled alternative in {@link JQuickConnectorParser#processor}.
	 * @param ctx the parse tree
	 */
	void enterFieldProcessor(JQuickConnectorParser.FieldProcessorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code fieldProcessor}
	 * labeled alternative in {@link JQuickConnectorParser#processor}.
	 * @param ctx the parse tree
	 */
	void exitFieldProcessor(JQuickConnectorParser.FieldProcessorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jsonPathProcessor}
	 * labeled alternative in {@link JQuickConnectorParser#processor}.
	 * @param ctx the parse tree
	 */
	void enterJsonPathProcessor(JQuickConnectorParser.JsonPathProcessorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jsonPathProcessor}
	 * labeled alternative in {@link JQuickConnectorParser#processor}.
	 * @param ctx the parse tree
	 */
	void exitJsonPathProcessor(JQuickConnectorParser.JsonPathProcessorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#connector}.
	 * @param ctx the parse tree
	 */
	void enterConnector(JQuickConnectorParser.ConnectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#connector}.
	 * @param ctx the parse tree
	 */
	void exitConnector(JQuickConnectorParser.ConnectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#connectorCode}.
	 * @param ctx the parse tree
	 */
	void enterConnectorCode(JQuickConnectorParser.ConnectorCodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#connectorCode}.
	 * @param ctx the parse tree
	 */
	void exitConnectorCode(JQuickConnectorParser.ConnectorCodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(JQuickConnectorParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(JQuickConnectorParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(JQuickConnectorParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(JQuickConnectorParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#columnName}.
	 * @param ctx the parse tree
	 */
	void enterColumnName(JQuickConnectorParser.ColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#columnName}.
	 * @param ctx the parse tree
	 */
	void exitColumnName(JQuickConnectorParser.ColumnNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JQuickConnectorParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(JQuickConnectorParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link JQuickConnectorParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(JQuickConnectorParser.VarContext ctx);
}