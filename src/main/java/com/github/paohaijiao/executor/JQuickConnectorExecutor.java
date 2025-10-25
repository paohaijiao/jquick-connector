/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
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
