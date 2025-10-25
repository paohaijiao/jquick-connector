// Generated from D:/idea/jthornruleGrammer/connector/JQuickConnector.g4 by ANTLR 4.13.2
package com.github.paohaijiao.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JQuickConnectorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, FIELD=3, PATH=4, INTEGER=5, LONG=6, FLOAT=7, DOUBLE=8, 
		STRING=9, BOOLEAN=10, DATE=11, SELECT=12, FROM=13, NULL_VALUE=14, NULL=15, 
		BOOLEAN_VALUE=16, TRUE=17, FALSE=18, CONNECTOR_CODE=19, CSV=20, EXCEL=21, 
		MYSQL=22, ORACLE=23, KINGBASE=24, CURL=25, PROPERTY_NAME=26, VAR_NAME=27, 
		STRING_VALUE=28, NUMBER_VALUE=29, ARROW=30, COMMA=31, LPAREN=32, RPAREN=33, 
		COLON=34, WS=35;
	public static final int
		RULE_select = 0, RULE_fieldMapping = 1, RULE_dataType = 2, RULE_sourceField = 3, 
		RULE_processor = 4, RULE_connector = 5, RULE_property = 6, RULE_value = 7, 
		RULE_columnName = 8, RULE_var = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"select", "fieldMapping", "dataType", "sourceField", "processor", "connector", 
			"property", "value", "columnName", "var"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "'->'", "','", "'('", "')'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "FIELD", "PATH", "INTEGER", "LONG", "FLOAT", "DOUBLE", 
			"STRING", "BOOLEAN", "DATE", "SELECT", "FROM", "NULL_VALUE", "NULL", 
			"BOOLEAN_VALUE", "TRUE", "FALSE", "CONNECTOR_CODE", "CSV", "EXCEL", "MYSQL", 
			"ORACLE", "KINGBASE", "CURL", "PROPERTY_NAME", "VAR_NAME", "STRING_VALUE", 
			"NUMBER_VALUE", "ARROW", "COMMA", "LPAREN", "RPAREN", "COLON", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JQuickConnector.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JQuickConnectorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(JQuickConnectorParser.SELECT, 0); }
		public List<FieldMappingContext> fieldMapping() {
			return getRuleContexts(FieldMappingContext.class);
		}
		public FieldMappingContext fieldMapping(int i) {
			return getRuleContext(FieldMappingContext.class,i);
		}
		public TerminalNode FROM() { return getToken(JQuickConnectorParser.FROM, 0); }
		public ConnectorContext connector() {
			return getRuleContext(ConnectorContext.class,0);
		}
		public TerminalNode EOF() { return getToken(JQuickConnectorParser.EOF, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JQuickConnectorParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JQuickConnectorParser.COMMA, i);
		}
		public SelectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterSelect(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitSelect(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitSelect(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectContext select() throws RecognitionException {
		SelectContext _localctx = new SelectContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_select);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(SELECT);
			setState(21);
			fieldMapping();
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(22);
				match(COMMA);
				setState(23);
				fieldMapping();
				}
				}
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(29);
			match(FROM);
			setState(30);
			connector();
			setState(31);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldMappingContext extends ParserRuleContext {
		public ProcessorContext processor() {
			return getRuleContext(ProcessorContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(JQuickConnectorParser.ARROW, 0); }
		public SourceFieldContext sourceField() {
			return getRuleContext(SourceFieldContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JQuickConnectorParser.COLON, 0); }
		public DataTypeContext dataType() {
			return getRuleContext(DataTypeContext.class,0);
		}
		public FieldMappingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldMapping; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterFieldMapping(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitFieldMapping(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitFieldMapping(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldMappingContext fieldMapping() throws RecognitionException {
		FieldMappingContext _localctx = new FieldMappingContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fieldMapping);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			processor();
			setState(34);
			match(ARROW);
			setState(35);
			sourceField();
			{
			setState(36);
			match(COLON);
			setState(37);
			dataType();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DataTypeContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(JQuickConnectorParser.INTEGER, 0); }
		public TerminalNode LONG() { return getToken(JQuickConnectorParser.LONG, 0); }
		public TerminalNode FLOAT() { return getToken(JQuickConnectorParser.FLOAT, 0); }
		public TerminalNode DOUBLE() { return getToken(JQuickConnectorParser.DOUBLE, 0); }
		public TerminalNode STRING() { return getToken(JQuickConnectorParser.STRING, 0); }
		public TerminalNode BOOLEAN() { return getToken(JQuickConnectorParser.BOOLEAN, 0); }
		public TerminalNode DATE() { return getToken(JQuickConnectorParser.DATE, 0); }
		public DataTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterDataType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitDataType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitDataType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DataTypeContext dataType() throws RecognitionException {
		DataTypeContext _localctx = new DataTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_dataType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4064L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SourceFieldContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public SourceFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sourceField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterSourceField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitSourceField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitSourceField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SourceFieldContext sourceField() throws RecognitionException {
		SourceFieldContext _localctx = new SourceFieldContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_sourceField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			columnName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProcessorContext extends ParserRuleContext {
		public ProcessorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_processor; }
	 
		public ProcessorContext() { }
		public void copyFrom(ProcessorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JsonPathProcessorContext extends ProcessorContext {
		public TerminalNode PATH() { return getToken(JQuickConnectorParser.PATH, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickConnectorParser.LPAREN, 0); }
		public TerminalNode STRING_VALUE() { return getToken(JQuickConnectorParser.STRING_VALUE, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickConnectorParser.RPAREN, 0); }
		public JsonPathProcessorContext(ProcessorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterJsonPathProcessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitJsonPathProcessor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitJsonPathProcessor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FieldProcessorContext extends ProcessorContext {
		public TerminalNode FIELD() { return getToken(JQuickConnectorParser.FIELD, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickConnectorParser.LPAREN, 0); }
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JQuickConnectorParser.RPAREN, 0); }
		public FieldProcessorContext(ProcessorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterFieldProcessor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitFieldProcessor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitFieldProcessor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcessorContext processor() throws RecognitionException {
		ProcessorContext _localctx = new ProcessorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_processor);
		try {
			setState(52);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FIELD:
				_localctx = new FieldProcessorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				match(FIELD);
				setState(44);
				match(LPAREN);
				setState(45);
				columnName();
				setState(46);
				match(RPAREN);
				}
				break;
			case PATH:
				_localctx = new JsonPathProcessorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(PATH);
				setState(49);
				match(LPAREN);
				setState(50);
				match(STRING_VALUE);
				setState(51);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConnectorContext extends ParserRuleContext {
		public TerminalNode CONNECTOR_CODE() { return getToken(JQuickConnectorParser.CONNECTOR_CODE, 0); }
		public TerminalNode LPAREN() { return getToken(JQuickConnectorParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JQuickConnectorParser.RPAREN, 0); }
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JQuickConnectorParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JQuickConnectorParser.COMMA, i);
		}
		public ConnectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterConnector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitConnector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitConnector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConnectorContext connector() throws RecognitionException {
		ConnectorContext _localctx = new ConnectorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_connector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(CONNECTOR_CODE);
			setState(55);
			match(LPAREN);
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PROPERTY_NAME) {
				{
				setState(56);
				property();
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(57);
					match(COMMA);
					setState(58);
					property();
					}
					}
					setState(63);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(66);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyContext extends ParserRuleContext {
		public TerminalNode PROPERTY_NAME() { return getToken(JQuickConnectorParser.PROPERTY_NAME, 0); }
		public TerminalNode COLON() { return getToken(JQuickConnectorParser.COLON, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_property);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(PROPERTY_NAME);
			setState(69);
			match(COLON);
			setState(70);
			value();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode STRING_VALUE() { return getToken(JQuickConnectorParser.STRING_VALUE, 0); }
		public TerminalNode NUMBER_VALUE() { return getToken(JQuickConnectorParser.NUMBER_VALUE, 0); }
		public TerminalNode BOOLEAN_VALUE() { return getToken(JQuickConnectorParser.BOOLEAN_VALUE, 0); }
		public TerminalNode NULL_VALUE() { return getToken(JQuickConnectorParser.NULL_VALUE, 0); }
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_value);
		try {
			setState(77);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_VALUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				match(STRING_VALUE);
				}
				break;
			case NUMBER_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				match(NUMBER_VALUE);
				}
				break;
			case BOOLEAN_VALUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(74);
				match(BOOLEAN_VALUE);
				}
				break;
			case NULL_VALUE:
				enterOuterAlt(_localctx, 4);
				{
				setState(75);
				match(NULL_VALUE);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 5);
				{
				setState(76);
				var();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ColumnNameContext extends ParserRuleContext {
		public TerminalNode VAR_NAME() { return getToken(JQuickConnectorParser.VAR_NAME, 0); }
		public ColumnNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_columnName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterColumnName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitColumnName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitColumnName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColumnNameContext columnName() throws RecognitionException {
		ColumnNameContext _localctx = new ColumnNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_columnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(VAR_NAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarContext extends ParserRuleContext {
		public TerminalNode VAR_NAME() { return getToken(JQuickConnectorParser.VAR_NAME, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__0);
			setState(82);
			match(VAR_NAME);
			setState(83);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001#V\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002\u0002"+
		"\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002\u0005"+
		"\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007"+
		"\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005"+
		"\u0000\u0019\b\u0000\n\u0000\f\u0000\u001c\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u00045\b\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005<\b\u0005"+
		"\n\u0005\f\u0005?\t\u0005\u0003\u0005A\b\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007N\b\u0007\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0000\u0000\n\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0000\u0001\u0001\u0000\u0005\u000bS\u0000"+
		"\u0014\u0001\u0000\u0000\u0000\u0002!\u0001\u0000\u0000\u0000\u0004\'"+
		"\u0001\u0000\u0000\u0000\u0006)\u0001\u0000\u0000\u0000\b4\u0001\u0000"+
		"\u0000\u0000\n6\u0001\u0000\u0000\u0000\fD\u0001\u0000\u0000\u0000\u000e"+
		"M\u0001\u0000\u0000\u0000\u0010O\u0001\u0000\u0000\u0000\u0012Q\u0001"+
		"\u0000\u0000\u0000\u0014\u0015\u0005\f\u0000\u0000\u0015\u001a\u0003\u0002"+
		"\u0001\u0000\u0016\u0017\u0005\u001f\u0000\u0000\u0017\u0019\u0003\u0002"+
		"\u0001\u0000\u0018\u0016\u0001\u0000\u0000\u0000\u0019\u001c\u0001\u0000"+
		"\u0000\u0000\u001a\u0018\u0001\u0000\u0000\u0000\u001a\u001b\u0001\u0000"+
		"\u0000\u0000\u001b\u001d\u0001\u0000\u0000\u0000\u001c\u001a\u0001\u0000"+
		"\u0000\u0000\u001d\u001e\u0005\r\u0000\u0000\u001e\u001f\u0003\n\u0005"+
		"\u0000\u001f \u0005\u0000\u0000\u0001 \u0001\u0001\u0000\u0000\u0000!"+
		"\"\u0003\b\u0004\u0000\"#\u0005\u001e\u0000\u0000#$\u0003\u0006\u0003"+
		"\u0000$%\u0005\"\u0000\u0000%&\u0003\u0004\u0002\u0000&\u0003\u0001\u0000"+
		"\u0000\u0000\'(\u0007\u0000\u0000\u0000(\u0005\u0001\u0000\u0000\u0000"+
		")*\u0003\u0010\b\u0000*\u0007\u0001\u0000\u0000\u0000+,\u0005\u0003\u0000"+
		"\u0000,-\u0005 \u0000\u0000-.\u0003\u0010\b\u0000./\u0005!\u0000\u0000"+
		"/5\u0001\u0000\u0000\u000001\u0005\u0004\u0000\u000012\u0005 \u0000\u0000"+
		"23\u0005\u001c\u0000\u000035\u0005!\u0000\u00004+\u0001\u0000\u0000\u0000"+
		"40\u0001\u0000\u0000\u00005\t\u0001\u0000\u0000\u000067\u0005\u0013\u0000"+
		"\u00007@\u0005 \u0000\u00008=\u0003\f\u0006\u00009:\u0005\u001f\u0000"+
		"\u0000:<\u0003\f\u0006\u0000;9\u0001\u0000\u0000\u0000<?\u0001\u0000\u0000"+
		"\u0000=;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>A\u0001\u0000"+
		"\u0000\u0000?=\u0001\u0000\u0000\u0000@8\u0001\u0000\u0000\u0000@A\u0001"+
		"\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BC\u0005!\u0000\u0000C\u000b"+
		"\u0001\u0000\u0000\u0000DE\u0005\u001a\u0000\u0000EF\u0005\"\u0000\u0000"+
		"FG\u0003\u000e\u0007\u0000G\r\u0001\u0000\u0000\u0000HN\u0005\u001c\u0000"+
		"\u0000IN\u0005\u001d\u0000\u0000JN\u0005\u0010\u0000\u0000KN\u0005\u000e"+
		"\u0000\u0000LN\u0003\u0012\t\u0000MH\u0001\u0000\u0000\u0000MI\u0001\u0000"+
		"\u0000\u0000MJ\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000\u0000ML\u0001"+
		"\u0000\u0000\u0000N\u000f\u0001\u0000\u0000\u0000OP\u0005\u001b\u0000"+
		"\u0000P\u0011\u0001\u0000\u0000\u0000QR\u0005\u0001\u0000\u0000RS\u0005"+
		"\u001b\u0000\u0000ST\u0005\u0002\u0000\u0000T\u0013\u0001\u0000\u0000"+
		"\u0000\u0005\u001a4=@M";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}