package com.github.paohaijiao.executor;

import com.github.paohaijiao.antlr.impl.JAbstractAntlrExecutor;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickConnectorLexer;
import com.github.paohaijiao.parser.JQuickConnectorParser;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.visitor.JQuickConnectorCommonVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;

public class JQuickConnectorExecutor extends JAbstractAntlrExecutor<String, ConnectorParsedQuery> {

    private JContext context=new JContext();

    public JQuickConnectorExecutor(JContext context){
        this.context = context;
    }
    public JQuickConnectorExecutor( ){
        this.context = new JContext();
    }
    @Override
    protected Lexer createLexer(CharStream input) {
        return new JQuickConnectorLexer(input);
    }

    @Override
    protected Parser createParser(TokenStream tokens) {
        return new JQuickConnectorParser(tokens);
    }

    @Override
    protected ConnectorParsedQuery parse(Parser parser) throws JAntlrExecutionException {
        JQuickConnectorParser calcParser = (JQuickConnectorParser) parser;
        JQuickConnectorParser.SelectContext tree = calcParser.select();
        JQuickConnectorCommonVisitor visitor = new JQuickConnectorCommonVisitor(this.context);
        return visitor.visitSelect(tree);
    }
}
