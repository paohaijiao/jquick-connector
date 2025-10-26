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
		STRING=9, BOOLEAN=10, DATE=11, OBJECT=12, SELECT=13, FROM=14, NULL_VALUE=15, 
		NULL=16, BOOLEAN_VALUE=17, TRUE=18, FALSE=19, VAR=20, STRING_VALUE=21, 
		NUMBER_VALUE=22, ARROW=23, COMMA=24, LPAREN=25, RPAREN=26, COLON=27, WS=28;
	public static final int
		RULE_select = 0, RULE_fieldMapping = 1, RULE_dataType = 2, RULE_targetField = 3, 
		RULE_processor = 4, RULE_connector = 5, RULE_connectorCode = 6, RULE_property = 7, 
		RULE_value = 8, RULE_columnName = 9, RULE_var = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"select", "fieldMapping", "dataType", "targetField", "processor", "connector", 
			"connectorCode", "property", "value", "columnName", "var"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "'->'", 
			"','", "'('", "')'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "FIELD", "PATH", "INTEGER", "LONG", "FLOAT", "DOUBLE", 
			"STRING", "BOOLEAN", "DATE", "OBJECT", "SELECT", "FROM", "NULL_VALUE", 
			"NULL", "BOOLEAN_VALUE", "TRUE", "FALSE", "VAR", "STRING_VALUE", "NUMBER_VALUE", 
			"ARROW", "COMMA", "LPAREN", "RPAREN", "COLON", "WS"
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
			setState(22);
			match(SELECT);
			setState(23);
			fieldMapping();
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(24);
				match(COMMA);
				setState(25);
				fieldMapping();
				}
				}
				setState(30);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(31);
			match(FROM);
			setState(32);
			connector();
			setState(33);
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
		public TargetFieldContext targetField() {
			return getRuleContext(TargetFieldContext.class,0);
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
			setState(35);
			processor();
			setState(36);
			match(ARROW);
			setState(37);
			targetField();
			{
			setState(38);
			match(COLON);
			setState(39);
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
		public TerminalNode OBJECT() { return getToken(JQuickConnectorParser.OBJECT, 0); }
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
			setState(41);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8160L) != 0)) ) {
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
	public static class TargetFieldContext extends ParserRuleContext {
		public ColumnNameContext columnName() {
			return getRuleContext(ColumnNameContext.class,0);
		}
		public TargetFieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_targetField; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterTargetField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitTargetField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitTargetField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TargetFieldContext targetField() throws RecognitionException {
		TargetFieldContext _localctx = new TargetFieldContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_targetField);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
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
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FIELD:
				_localctx = new FieldProcessorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				match(FIELD);
				setState(46);
				match(LPAREN);
				setState(47);
				columnName();
				setState(48);
				match(RPAREN);
				}
				break;
			case PATH:
				_localctx = new JsonPathProcessorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				match(PATH);
				setState(51);
				match(LPAREN);
				setState(52);
				match(STRING_VALUE);
				setState(53);
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
		public ConnectorCodeContext connectorCode() {
			return getRuleContext(ConnectorCodeContext.class,0);
		}
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
			setState(56);
			connectorCode();
			setState(57);
			match(LPAREN);
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==VAR) {
				{
				setState(58);
				property();
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(59);
					match(COMMA);
					setState(60);
					property();
					}
					}
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(68);
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
	public static class ConnectorCodeContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(JQuickConnectorParser.VAR, 0); }
		public ConnectorCodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connectorCode; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).enterConnectorCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JQuickConnectorListener ) ((JQuickConnectorListener)listener).exitConnectorCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JQuickConnectorVisitor ) return ((JQuickConnectorVisitor<? extends T>)visitor).visitConnectorCode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConnectorCodeContext connectorCode() throws RecognitionException {
		ConnectorCodeContext _localctx = new ConnectorCodeContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_connectorCode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(VAR);
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
		public TerminalNode VAR() { return getToken(JQuickConnectorParser.VAR, 0); }
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
		enterRule(_localctx, 14, RULE_property);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(VAR);
			setState(73);
			match(COLON);
			setState(74);
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
		enterRule(_localctx, 16, RULE_value);
		try {
			setState(81);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING_VALUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				match(STRING_VALUE);
				}
				break;
			case NUMBER_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				match(NUMBER_VALUE);
				}
				break;
			case BOOLEAN_VALUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(78);
				match(BOOLEAN_VALUE);
				}
				break;
			case NULL_VALUE:
				enterOuterAlt(_localctx, 4);
				{
				setState(79);
				match(NULL_VALUE);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 5);
				{
				setState(80);
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
		public TerminalNode VAR() { return getToken(JQuickConnectorParser.VAR, 0); }
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
		enterRule(_localctx, 18, RULE_columnName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			match(VAR);
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
		public TerminalNode VAR() { return getToken(JQuickConnectorParser.VAR, 0); }
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
		enterRule(_localctx, 20, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(T__0);
			setState(86);
			match(VAR);
			setState(87);
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
		"\u0004\u0001\u001cZ\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0005\u0000\u001b\b\u0000\n\u0000\f\u0000\u001e\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"7\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005>\b\u0005\n\u0005\f\u0005A\t\u0005\u0003\u0005C\b\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bR\b"+
		"\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0000\u0000"+
		"\u000b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0000\u0001"+
		"\u0001\u0000\u0005\fV\u0000\u0016\u0001\u0000\u0000\u0000\u0002#\u0001"+
		"\u0000\u0000\u0000\u0004)\u0001\u0000\u0000\u0000\u0006+\u0001\u0000\u0000"+
		"\u0000\b6\u0001\u0000\u0000\u0000\n8\u0001\u0000\u0000\u0000\fF\u0001"+
		"\u0000\u0000\u0000\u000eH\u0001\u0000\u0000\u0000\u0010Q\u0001\u0000\u0000"+
		"\u0000\u0012S\u0001\u0000\u0000\u0000\u0014U\u0001\u0000\u0000\u0000\u0016"+
		"\u0017\u0005\r\u0000\u0000\u0017\u001c\u0003\u0002\u0001\u0000\u0018\u0019"+
		"\u0005\u0018\u0000\u0000\u0019\u001b\u0003\u0002\u0001\u0000\u001a\u0018"+
		"\u0001\u0000\u0000\u0000\u001b\u001e\u0001\u0000\u0000\u0000\u001c\u001a"+
		"\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001f"+
		"\u0001\u0000\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001f \u0005"+
		"\u000e\u0000\u0000 !\u0003\n\u0005\u0000!\"\u0005\u0000\u0000\u0001\""+
		"\u0001\u0001\u0000\u0000\u0000#$\u0003\b\u0004\u0000$%\u0005\u0017\u0000"+
		"\u0000%&\u0003\u0006\u0003\u0000&\'\u0005\u001b\u0000\u0000\'(\u0003\u0004"+
		"\u0002\u0000(\u0003\u0001\u0000\u0000\u0000)*\u0007\u0000\u0000\u0000"+
		"*\u0005\u0001\u0000\u0000\u0000+,\u0003\u0012\t\u0000,\u0007\u0001\u0000"+
		"\u0000\u0000-.\u0005\u0003\u0000\u0000./\u0005\u0019\u0000\u0000/0\u0003"+
		"\u0012\t\u000001\u0005\u001a\u0000\u000017\u0001\u0000\u0000\u000023\u0005"+
		"\u0004\u0000\u000034\u0005\u0019\u0000\u000045\u0005\u0015\u0000\u0000"+
		"57\u0005\u001a\u0000\u00006-\u0001\u0000\u0000\u000062\u0001\u0000\u0000"+
		"\u00007\t\u0001\u0000\u0000\u000089\u0003\f\u0006\u00009B\u0005\u0019"+
		"\u0000\u0000:?\u0003\u000e\u0007\u0000;<\u0005\u0018\u0000\u0000<>\u0003"+
		"\u000e\u0007\u0000=;\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000"+
		"?=\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@C\u0001\u0000\u0000"+
		"\u0000A?\u0001\u0000\u0000\u0000B:\u0001\u0000\u0000\u0000BC\u0001\u0000"+
		"\u0000\u0000CD\u0001\u0000\u0000\u0000DE\u0005\u001a\u0000\u0000E\u000b"+
		"\u0001\u0000\u0000\u0000FG\u0005\u0014\u0000\u0000G\r\u0001\u0000\u0000"+
		"\u0000HI\u0005\u0014\u0000\u0000IJ\u0005\u001b\u0000\u0000JK\u0003\u0010"+
		"\b\u0000K\u000f\u0001\u0000\u0000\u0000LR\u0005\u0015\u0000\u0000MR\u0005"+
		"\u0016\u0000\u0000NR\u0005\u0011\u0000\u0000OR\u0005\u000f\u0000\u0000"+
		"PR\u0003\u0014\n\u0000QL\u0001\u0000\u0000\u0000QM\u0001\u0000\u0000\u0000"+
		"QN\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000QP\u0001\u0000\u0000"+
		"\u0000R\u0011\u0001\u0000\u0000\u0000ST\u0005\u0014\u0000\u0000T\u0013"+
		"\u0001\u0000\u0000\u0000UV\u0005\u0001\u0000\u0000VW\u0005\u0014\u0000"+
		"\u0000WX\u0005\u0002\u0000\u0000X\u0015\u0001\u0000\u0000\u0000\u0005"+
		"\u001c6?BQ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}