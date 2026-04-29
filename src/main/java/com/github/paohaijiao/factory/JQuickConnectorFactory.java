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
package com.github.paohaijiao.factory;

import com.github.paohaijiao.assembler.JQuickConnectorDataSetAssembler;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.dataset.JQuickConnectorDataSet;

import com.github.paohaijiao.field.JQuickConnectorProcessor;
import com.github.paohaijiao.handler.JQuickConnectorHandler;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.query.JQuickConnectorQueryParser;
import com.github.paohaijiao.registry.JQuickConnectorRegistry;
import com.github.paohaijiao.registry.JQuickConnectorProcessorRegistry;
import com.github.paohaijiao.statement.JQuickRow;

import java.util.List;

/**
 * packageName com.github.paohaijiao.factory
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class JQuickConnectorFactory {

    public final static JConsole console = new JConsole();

    private final JQuickConnectorQueryParser queryParser;

    private final JContext context;

    public JQuickConnectorFactory() {
        this.context = new JContext();
        this.queryParser = new JQuickConnectorQueryParser(this.context);

    }

    public JQuickConnectorFactory(JContext context) {
        this.context = context;
        this.queryParser = new JQuickConnectorQueryParser(this.context);
    }

    /**
     * 注册自定义连接器
     */
    public static void registerConnector(String type, JQuickConnectorHandler connector) {
        JQuickConnectorRegistry.registerConnector(type, connector);
    }

    /**
     * 注册自定义字段处理器
     */
    public static void registerProcessor(String name, JQuickConnectorProcessor processor) {
        JQuickConnectorProcessorRegistry.registerProcessor(name, processor);
    }

    /**
     * 执行查询并返回DataSet
     */
    public JQuickConnectorDataSet executeQuery(String query) {
        JQuickConnectorParsedQuery connectorParsedQuery = queryParser.parse(query);
        JQuickConnectorHandler connector = JQuickConnectorRegistry.getConnector(connectorParsedQuery.getConnectorType());
        if (connector == null) {
            throw new IllegalArgumentException("unsupported connector type: " + connectorParsedQuery.getConnectorType());
        }
        List<JQuickRow> rows = connector.buildRow(connectorParsedQuery);
        JQuickConnectorDataSet rawDataSet = JQuickConnectorDataSetAssembler.convert(rows, connectorParsedQuery.getFieldMappings());
        return rawDataSet;
    }
}
