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

import com.github.paohaijiao.assembler.DataSetAssembler;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.field.ConnectorProcessor;
import com.github.paohaijiao.handler.ConnectorHandler;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.query.ConnectorQueryParser;
import com.github.paohaijiao.registry.ConnectorRegistry;
import com.github.paohaijiao.registry.ConnectoryProcessorRegistry;

import java.util.List;

/**
 * packageName com.github.paohaijiao.factory
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectorFactory {

    public final static JConsole console=new JConsole();

    private final ConnectorQueryParser queryParser;

    private final JContext context;

    public ConnectorFactory() {
        this.context = new JContext();
        this.queryParser = new ConnectorQueryParser(this.context );

    }
    public ConnectorFactory(JContext context) {
        this.context = context;
        this.queryParser = new ConnectorQueryParser(this.context );
    }
    /**
     * 执行查询并返回DataSet
     */
    public DataSet executeQuery(String query) {
        ConnectorParsedQuery connectorParsedQuery = queryParser.parse(query);
        ConnectorHandler connector = ConnectorRegistry.getConnector(connectorParsedQuery.getConnectorType());
        if (connector == null) {
            throw new IllegalArgumentException("unsupported connector type: " + connectorParsedQuery.getConnectorType());
        }
        List<Row> rows = connector.buildRow(connectorParsedQuery);
        DataSet rawDataSet=DataSetAssembler.convert(rows,connectorParsedQuery.getFieldMappings());
        return rawDataSet;
    }

    /**
     * 注册自定义连接器
     */
    public static void registerConnector(String type, ConnectorHandler connector) {
        ConnectorRegistry.registerConnector(type, connector);
    }

    /**
     * 注册自定义字段处理器
     */
    public static void registerProcessor(String name, ConnectorProcessor processor) {
        ConnectoryProcessorRegistry.registerProcessor(name, processor);
    }
}
