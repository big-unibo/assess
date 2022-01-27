// Generated from Assess.g4 by ANTLR 4.8

package it.unibo.antlr.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AssessParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, IN=23, AND=24, NOT=25, 
		TRUE=26, FALSE=27, GT=28, GE=29, LT=30, LE=31, EQ=32, DECIMAL=33, INT=34, 
		ID=35, WS=36;
	public static final int
		RULE_assess = 0, RULE_id = 1, RULE_benchmark = 2, RULE_function = 3, RULE_label = 4, 
		RULE_interval = 5, RULE_clause = 6, RULE_condition = 7, RULE_value = 8, 
		RULE_comparator = 9, RULE_binary = 10, RULE_bool = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"assess", "id", "benchmark", "function", "label", "interval", "clause", 
			"condition", "value", "comparator", "binary", "bool"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'with'", "'WITH'", "'for'", "'FOR'", "'by'", "','", "'assess'", 
			"'ASSESS'", "'against'", "'AGAINST'", "'using'", "'USING'", "'labels'", 
			"'scaled'", "'past'", "'('", "')'", "'{'", "'}'", "'['", "']'", "':'", 
			null, null, null, null, null, "'>'", "'>='", "'<'", "'<='", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, "IN", 
			"AND", "NOT", "TRUE", "FALSE", "GT", "GE", "LT", "LE", "EQ", "DECIMAL", 
			"INT", "ID", "WS"
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
	public String getGrammarFileName() { return "Assess.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AssessParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class AssessContext extends ParserRuleContext {
		public IdContext cube;
		public ClauseContext sc;
		public IdContext id;
		public List<IdContext> gc = new ArrayList<IdContext>();
		public List<IdContext> mcs = new ArrayList<IdContext>();
		public BenchmarkContext bc;
		public FunctionContext as;
		public LabelContext l;
		public TerminalNode EOF() { return getToken(AssessParser.EOF, 0); }
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<ClauseContext> clause() {
			return getRuleContexts(ClauseContext.class);
		}
		public ClauseContext clause(int i) {
			return getRuleContext(ClauseContext.class,i);
		}
		public BenchmarkContext benchmark() {
			return getRuleContext(BenchmarkContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public AssessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterAssess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitAssess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitAssess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssessContext assess() throws RecognitionException {
		AssessContext _localctx = new AssessContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assess);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			_la = _input.LA(1);
			if ( !(_la==T__0 || _la==T__1) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(25);
			((AssessContext)_localctx).cube = id();
			setState(28);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(26);
				_la = _input.LA(1);
				if ( !(_la==T__2 || _la==T__3) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(27);
				((AssessContext)_localctx).sc = clause();
				}
				break;
			}
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(30);
				match(T__4);
				setState(31);
				((AssessContext)_localctx).id = id();
				((AssessContext)_localctx).gc.add(((AssessContext)_localctx).id);
				setState(36);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(32);
					match(T__5);
					setState(33);
					((AssessContext)_localctx).id = id();
					((AssessContext)_localctx).gc.add(((AssessContext)_localctx).id);
					}
					}
					setState(38);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2 || _la==T__3) {
				{
				setState(41);
				_la = _input.LA(1);
				if ( !(_la==T__2 || _la==T__3) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(42);
				((AssessContext)_localctx).sc = clause();
				}
			}

			setState(45);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__7) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(46);
			((AssessContext)_localctx).id = id();
			((AssessContext)_localctx).mcs.add(((AssessContext)_localctx).id);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(47);
				match(T__5);
				setState(48);
				((AssessContext)_localctx).id = id();
				((AssessContext)_localctx).mcs.add(((AssessContext)_localctx).id);
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8 || _la==T__9) {
				{
				setState(54);
				_la = _input.LA(1);
				if ( !(_la==T__8 || _la==T__9) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(55);
				((AssessContext)_localctx).bc = benchmark();
				}
			}

			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10 || _la==T__11) {
				{
				setState(58);
				_la = _input.LA(1);
				if ( !(_la==T__10 || _la==T__11) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(59);
				((AssessContext)_localctx).as = function();
				}
			}

			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(62);
				_la = _input.LA(1);
				if ( !(_la==T__12) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(63);
				((AssessContext)_localctx).l = label();
				}
			}

			setState(66);
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

	public static class IdContext extends ParserRuleContext {
		public String name;
		public Token ID;
		public TerminalNode ID() { return getToken(AssessParser.ID, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			((IdContext)_localctx).ID = match(ID);
			 ((IdContext)_localctx).name =  (((IdContext)_localctx).ID!=null?((IdContext)_localctx).ID.getText():null); 
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

	public static class BenchmarkContext extends ParserRuleContext {
		public BenchmarkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_benchmark; }
	 
		public BenchmarkContext() { }
		public void copyFrom(BenchmarkContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParentContext extends BenchmarkContext {
		public IdContext attr;
		public IdContext weight;
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public ParentContext(BenchmarkContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterParent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitParent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitParent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SiblingContext extends BenchmarkContext {
		public IdContext attr;
		public ComparatorContext op;
		public ValueContext value;
		public List<ValueContext> val = new ArrayList<ValueContext>();
		public IdContext weight;
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public SiblingContext(BenchmarkContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterSibling(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitSibling(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitSibling(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PastContext extends BenchmarkContext {
		public Token val;
		public TerminalNode DECIMAL() { return getToken(AssessParser.DECIMAL, 0); }
		public PastContext(BenchmarkContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterPast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitPast(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitPast(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TargetContext extends BenchmarkContext {
		public Token val;
		public TerminalNode DECIMAL() { return getToken(AssessParser.DECIMAL, 0); }
		public TargetContext(BenchmarkContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitTarget(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitTarget(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BenchmarkContext benchmark() throws RecognitionException {
		BenchmarkContext _localctx = new BenchmarkContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_benchmark);
		int _la;
		try {
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new TargetContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				((TargetContext)_localctx).val = match(DECIMAL);
				}
				break;
			case 2:
				_localctx = new SiblingContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				((SiblingContext)_localctx).attr = id();
				setState(73);
				((SiblingContext)_localctx).op = comparator();
				setState(74);
				((SiblingContext)_localctx).value = value();
				((SiblingContext)_localctx).val.add(((SiblingContext)_localctx).value);
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(75);
					match(T__13);
					setState(76);
					((SiblingContext)_localctx).weight = id();
					}
				}

				}
				break;
			case 3:
				_localctx = new ParentContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(79);
				((ParentContext)_localctx).attr = id();
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(80);
					match(T__13);
					setState(81);
					((ParentContext)_localctx).weight = id();
					}
				}

				}
				break;
			case 4:
				_localctx = new PastContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				match(T__14);
				setState(85);
				((PastContext)_localctx).val = match(DECIMAL);
				}
				break;
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

	public static class FunctionContext extends ParserRuleContext {
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
	 
		public FunctionContext() { }
		public void copyFrom(FunctionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NestedFunctionContext extends FunctionContext {
		public IdContext fun;
		public FunctionContext function;
		public List<FunctionContext> functions = new ArrayList<FunctionContext>();
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public NestedFunctionContext(FunctionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterNestedFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitNestedFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitNestedFunction(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParametricFunctionContext extends FunctionContext {
		public IdContext fun;
		public Token ID;
		public List<Token> params = new ArrayList<Token>();
		public Token DECIMAL;
		public Token _tset266;
		public Token _tset277;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(AssessParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(AssessParser.ID, i);
		}
		public List<TerminalNode> DECIMAL() { return getTokens(AssessParser.DECIMAL); }
		public TerminalNode DECIMAL(int i) {
			return getToken(AssessParser.DECIMAL, i);
		}
		public ParametricFunctionContext(FunctionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterParametricFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitParametricFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitParametricFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function);
		int _la;
		try {
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new NestedFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				((NestedFunctionContext)_localctx).fun = id();
				setState(89);
				match(T__15);
				setState(90);
				((NestedFunctionContext)_localctx).function = function();
				((NestedFunctionContext)_localctx).functions.add(((NestedFunctionContext)_localctx).function);
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(91);
					match(T__5);
					setState(92);
					((NestedFunctionContext)_localctx).function = function();
					((NestedFunctionContext)_localctx).functions.add(((NestedFunctionContext)_localctx).function);
					}
					}
					setState(97);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(98);
				match(T__16);
				}
				break;
			case 2:
				_localctx = new ParametricFunctionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(100);
				((ParametricFunctionContext)_localctx).fun = id();
				setState(101);
				match(T__15);
				setState(102);
				((ParametricFunctionContext)_localctx)._tset266 = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==DECIMAL || _la==ID) ) {
					((ParametricFunctionContext)_localctx)._tset266 = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				((ParametricFunctionContext)_localctx).params.add(((ParametricFunctionContext)_localctx)._tset266);
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(103);
					match(T__5);
					setState(104);
					((ParametricFunctionContext)_localctx)._tset277 = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==DECIMAL || _la==ID) ) {
						((ParametricFunctionContext)_localctx)._tset277 = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					((ParametricFunctionContext)_localctx).params.add(((ParametricFunctionContext)_localctx)._tset277);
					}
					}
					setState(109);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(110);
				match(T__16);
				}
				break;
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

	public static class LabelContext extends ParserRuleContext {
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
	 
		public LabelContext() { }
		public void copyFrom(LabelContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class UselessContext extends LabelContext {
		public List<IntervalContext> interval() {
			return getRuleContexts(IntervalContext.class);
		}
		public IntervalContext interval(int i) {
			return getRuleContext(IntervalContext.class,i);
		}
		public UselessContext(LabelContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterUseless(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitUseless(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitUseless(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Label_intContext extends LabelContext {
		public IdContext lbl;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Label_intContext(LabelContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterLabel_int(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitLabel_int(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitLabel_int(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_label);
		int _la;
		try {
			setState(126);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new Label_intContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				((Label_intContext)_localctx).lbl = id();
				}
				break;
			case T__17:
				_localctx = new UselessContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				match(T__17);
				setState(116);
				interval();
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(117);
					match(T__5);
					setState(118);
					interval();
					}
					}
					setState(123);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(124);
				match(T__18);
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

	public static class IntervalContext extends ParserRuleContext {
		public IntervalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_interval; }
	 
		public IntervalContext() { }
		public void copyFrom(IntervalContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Label_funContext extends IntervalContext {
		public Token fr;
		public Token to;
		public IdContext l;
		public List<TerminalNode> DECIMAL() { return getTokens(AssessParser.DECIMAL); }
		public TerminalNode DECIMAL(int i) {
			return getToken(AssessParser.DECIMAL, i);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Label_funContext(IntervalContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterLabel_fun(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitLabel_fun(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitLabel_fun(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntervalContext interval() throws RecognitionException {
		IntervalContext _localctx = new IntervalContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_interval);
		int _la;
		try {
			_localctx = new Label_funContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_la = _input.LA(1);
			if ( !(_la==T__15 || _la==T__19) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(129);
			((Label_funContext)_localctx).fr = match(DECIMAL);
			setState(130);
			match(T__5);
			setState(131);
			((Label_funContext)_localctx).to = match(DECIMAL);
			setState(132);
			_la = _input.LA(1);
			if ( !(_la==T__16 || _la==T__20) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(133);
			match(T__21);
			setState(134);
			((Label_funContext)_localctx).l = id();
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

	public static class ClauseContext extends ParserRuleContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<BinaryContext> binary() {
			return getRuleContexts(BinaryContext.class);
		}
		public BinaryContext binary(int i) {
			return getRuleContext(BinaryContext.class,i);
		}
		public ClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClauseContext clause() throws RecognitionException {
		ClauseContext _localctx = new ClauseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			condition();
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(137);
				binary();
				setState(138);
				condition();
				}
				}
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ConditionContext extends ParserRuleContext {
		public IdContext attr;
		public ComparatorContext op;
		public ValueContext value;
		public List<ValueContext> val = new ArrayList<ValueContext>();
		public Token in;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public TerminalNode IN() { return getToken(AssessParser.IN, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_condition);
		int _la;
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(145);
				((ConditionContext)_localctx).attr = id();
				setState(146);
				((ConditionContext)_localctx).op = comparator();
				setState(147);
				((ConditionContext)_localctx).value = value();
				((ConditionContext)_localctx).val.add(((ConditionContext)_localctx).value);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(149);
				((ConditionContext)_localctx).attr = id();
				setState(150);
				((ConditionContext)_localctx).in = match(IN);
				setState(151);
				match(T__15);
				setState(152);
				((ConditionContext)_localctx).value = value();
				((ConditionContext)_localctx).val.add(((ConditionContext)_localctx).value);
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(153);
					match(T__5);
					setState(154);
					((ConditionContext)_localctx).value = value();
					((ConditionContext)_localctx).val.add(((ConditionContext)_localctx).value);
					}
					}
					setState(159);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(160);
				match(T__16);
				}
				break;
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AssessParser.ID, 0); }
		public TerminalNode DECIMAL() { return getToken(AssessParser.DECIMAL, 0); }
		public TerminalNode INT() { return getToken(AssessParser.INT, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_value);
		try {
			setState(168);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(164);
				match(ID);
				}
				break;
			case DECIMAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				match(DECIMAL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 3);
				{
				setState(166);
				match(INT);
				}
				break;
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 4);
				{
				setState(167);
				bool();
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

	public static class ComparatorContext extends ParserRuleContext {
		public TerminalNode GE() { return getToken(AssessParser.GE, 0); }
		public TerminalNode LE() { return getToken(AssessParser.LE, 0); }
		public TerminalNode EQ() { return getToken(AssessParser.EQ, 0); }
		public TerminalNode GT() { return getToken(AssessParser.GT, 0); }
		public TerminalNode LT() { return getToken(AssessParser.LT, 0); }
		public ComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterComparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitComparator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitComparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparatorContext comparator() throws RecognitionException {
		ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_comparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GE) | (1L << LT) | (1L << LE) | (1L << EQ))) != 0)) ) {
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

	public static class BinaryContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(AssessParser.AND, 0); }
		public BinaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterBinary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitBinary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitBinary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinaryContext binary() throws RecognitionException {
		BinaryContext _localctx = new BinaryContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_binary);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(AND);
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

	public static class BoolContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(AssessParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(AssessParser.FALSE, 0); }
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AssessListener ) ((AssessListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AssessVisitor ) return ((AssessVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3&\u00b3\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\5\2\37\n\2\3\2\3\2\3\2\3\2\7\2%\n"+
		"\2\f\2\16\2(\13\2\5\2*\n\2\3\2\3\2\5\2.\n\2\3\2\3\2\3\2\3\2\7\2\64\n\2"+
		"\f\2\16\2\67\13\2\3\2\3\2\5\2;\n\2\3\2\3\2\5\2?\n\2\3\2\3\2\5\2C\n\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4P\n\4\3\4\3\4\3\4\5\4U\n"+
		"\4\3\4\3\4\5\4Y\n\4\3\5\3\5\3\5\3\5\3\5\7\5`\n\5\f\5\16\5c\13\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\7\5l\n\5\f\5\16\5o\13\5\3\5\3\5\5\5s\n\5\3\6\3"+
		"\6\3\6\3\6\3\6\7\6z\n\6\f\6\16\6}\13\6\3\6\3\6\5\6\u0081\n\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\7\b\u008f\n\b\f\b\16\b\u0092\13"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u009e\n\t\f\t\16\t\u00a1"+
		"\13\t\3\t\3\t\5\t\u00a5\n\t\3\n\3\n\3\n\3\n\5\n\u00ab\n\n\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\r\3\2\3\4\3"+
		"\2\5\6\3\2\t\n\3\2\13\f\3\2\r\16\3\2\17\17\4\2##%%\4\2\22\22\26\26\4\2"+
		"\23\23\27\27\3\2\36\"\3\2\34\35\2\u00be\2\32\3\2\2\2\4F\3\2\2\2\6X\3\2"+
		"\2\2\br\3\2\2\2\n\u0080\3\2\2\2\f\u0082\3\2\2\2\16\u008a\3\2\2\2\20\u00a4"+
		"\3\2\2\2\22\u00aa\3\2\2\2\24\u00ac\3\2\2\2\26\u00ae\3\2\2\2\30\u00b0\3"+
		"\2\2\2\32\33\t\2\2\2\33\36\5\4\3\2\34\35\t\3\2\2\35\37\5\16\b\2\36\34"+
		"\3\2\2\2\36\37\3\2\2\2\37)\3\2\2\2 !\7\7\2\2!&\5\4\3\2\"#\7\b\2\2#%\5"+
		"\4\3\2$\"\3\2\2\2%(\3\2\2\2&$\3\2\2\2&\'\3\2\2\2\'*\3\2\2\2(&\3\2\2\2"+
		") \3\2\2\2)*\3\2\2\2*-\3\2\2\2+,\t\3\2\2,.\5\16\b\2-+\3\2\2\2-.\3\2\2"+
		"\2./\3\2\2\2/\60\t\4\2\2\60\65\5\4\3\2\61\62\7\b\2\2\62\64\5\4\3\2\63"+
		"\61\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66:\3\2\2\2\67\65"+
		"\3\2\2\289\t\5\2\29;\5\6\4\2:8\3\2\2\2:;\3\2\2\2;>\3\2\2\2<=\t\6\2\2="+
		"?\5\b\5\2><\3\2\2\2>?\3\2\2\2?B\3\2\2\2@A\t\7\2\2AC\5\n\6\2B@\3\2\2\2"+
		"BC\3\2\2\2CD\3\2\2\2DE\7\2\2\3E\3\3\2\2\2FG\7%\2\2GH\b\3\1\2H\5\3\2\2"+
		"\2IY\7#\2\2JK\5\4\3\2KL\5\24\13\2LO\5\22\n\2MN\7\20\2\2NP\5\4\3\2OM\3"+
		"\2\2\2OP\3\2\2\2PY\3\2\2\2QT\5\4\3\2RS\7\20\2\2SU\5\4\3\2TR\3\2\2\2TU"+
		"\3\2\2\2UY\3\2\2\2VW\7\21\2\2WY\7#\2\2XI\3\2\2\2XJ\3\2\2\2XQ\3\2\2\2X"+
		"V\3\2\2\2Y\7\3\2\2\2Z[\5\4\3\2[\\\7\22\2\2\\a\5\b\5\2]^\7\b\2\2^`\5\b"+
		"\5\2_]\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2\2ca\3\2\2\2de\7\23"+
		"\2\2es\3\2\2\2fg\5\4\3\2gh\7\22\2\2hm\t\b\2\2ij\7\b\2\2jl\t\b\2\2ki\3"+
		"\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2np\3\2\2\2om\3\2\2\2pq\7\23\2\2qs"+
		"\3\2\2\2rZ\3\2\2\2rf\3\2\2\2s\t\3\2\2\2t\u0081\5\4\3\2uv\7\24\2\2v{\5"+
		"\f\7\2wx\7\b\2\2xz\5\f\7\2yw\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3"+
		"\2\2\2}{\3\2\2\2~\177\7\25\2\2\177\u0081\3\2\2\2\u0080t\3\2\2\2\u0080"+
		"u\3\2\2\2\u0081\13\3\2\2\2\u0082\u0083\t\t\2\2\u0083\u0084\7#\2\2\u0084"+
		"\u0085\7\b\2\2\u0085\u0086\7#\2\2\u0086\u0087\t\n\2\2\u0087\u0088\7\30"+
		"\2\2\u0088\u0089\5\4\3\2\u0089\r\3\2\2\2\u008a\u0090\5\20\t\2\u008b\u008c"+
		"\5\26\f\2\u008c\u008d\5\20\t\2\u008d\u008f\3\2\2\2\u008e\u008b\3\2\2\2"+
		"\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\17"+
		"\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0094\5\4\3\2\u0094\u0095\5\24\13\2"+
		"\u0095\u0096\5\22\n\2\u0096\u00a5\3\2\2\2\u0097\u0098\5\4\3\2\u0098\u0099"+
		"\7\31\2\2\u0099\u009a\7\22\2\2\u009a\u009f\5\22\n\2\u009b\u009c\7\b\2"+
		"\2\u009c\u009e\5\22\n\2\u009d\u009b\3\2\2\2\u009e\u00a1\3\2\2\2\u009f"+
		"\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009f\3\2"+
		"\2\2\u00a2\u00a3\7\23\2\2\u00a3\u00a5\3\2\2\2\u00a4\u0093\3\2\2\2\u00a4"+
		"\u0097\3\2\2\2\u00a5\21\3\2\2\2\u00a6\u00ab\7%\2\2\u00a7\u00ab\7#\2\2"+
		"\u00a8\u00ab\7$\2\2\u00a9\u00ab\5\30\r\2\u00aa\u00a6\3\2\2\2\u00aa\u00a7"+
		"\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00a9\3\2\2\2\u00ab\23\3\2\2\2\u00ac"+
		"\u00ad\t\13\2\2\u00ad\25\3\2\2\2\u00ae\u00af\7\32\2\2\u00af\27\3\2\2\2"+
		"\u00b0\u00b1\t\f\2\2\u00b1\31\3\2\2\2\26\36&)-\65:>BOTXamr{\u0080\u0090"+
		"\u009f\u00a4\u00aa";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}