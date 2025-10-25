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

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.ColumnMeta;
import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.field.ConnectorProcessor;
import com.github.paohaijiao.holder.ConnectorFieldMappingHolder;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.query.ConnectorQueryParser;
import com.github.paohaijiao.registry.ConnectorRegistry;
import com.github.paohaijiao.registry.ConnectoryProcessorRegistry;
import com.github.paohaijiao.handler.ConnectorHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName com.github.paohaijiao.factory
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectorFactory {

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
        ConnectorParsedQuery parsedQuery = queryParser.parse(query);
        ConnectorHandler connector = ConnectorRegistry.getConnector(parsedQuery.getConnectorType());
        if (connector == null) {
            throw new IllegalArgumentException("Unsupported connector type: " + parsedQuery.getConnectorType());
        }
        ConnectorConfiguration config = new ConnectorConfiguration();
        parsedQuery.getConnectorProperties().forEach(config::setProperty);
        DataSet rawDataSet = connector.execute(config);
        return transformDataSet(rawDataSet, parsedQuery.getFieldMappings());
    }

    /**
     * 根据字段映射转换数据集
     */
    private DataSet transformDataSet(DataSet rawDataSet, List<ConnectorFieldMappingHolder> mappings) {
        List<ColumnMeta> newColumns = new ArrayList<>();
        for (ConnectorFieldMappingHolder mapping : mappings) {
            newColumns.add(new ColumnMeta(mapping.getTargetField(), mapping.getDataType(), mapping.getSourceField()));
        }
        List<Row> newRows = new ArrayList<>();
        for (Row rawRow : rawDataSet.getRows()) {
            Row newRow = new Row();
            for (ConnectorFieldMappingHolder mapping : mappings) {
                Object rawValue = rawRow.get(mapping.getSourceField());
                Object processedValue = mapping.getProcessor().process(rawValue);
                Object finalValue = convertType(processedValue, mapping.getDataType());
                newRow.put(mapping.getTargetField(), finalValue);
            }
            newRows.add(newRow);
        }
        return new DataSet(newColumns, newRows);
    }

    /**
     * 类型转换
     */
    private Object convertType(Object value, Class<?> targetType) {
        if (value == null) return null;
        if (targetType.isInstance(value)) {
            return value;
        }
        String strValue = value.toString();
        try {
            if (targetType == Integer.class || targetType == int.class) {
                return Integer.parseInt(strValue);
            } else if (targetType == Long.class || targetType == long.class) {
                return Long.parseLong(strValue);
            } else if (targetType == Double.class || targetType == double.class) {
                return Double.parseDouble(strValue);
            } else if (targetType == Boolean.class || targetType == boolean.class) {
                return Boolean.parseBoolean(strValue);
            } else if (targetType == String.class) {
                return strValue;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot convert value '" + value + "' to type " + targetType);
        }

        return value;
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
