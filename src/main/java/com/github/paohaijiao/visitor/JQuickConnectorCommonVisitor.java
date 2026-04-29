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
package com.github.paohaijiao.visitor;

import com.github.paohaijiao.enums.JQuickConnectorDataType;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.field.JQuickConnectorFieldProcessor;
import com.github.paohaijiao.field.JQuickConnectorJsonPathProcessor;
import com.github.paohaijiao.holder.JQuickConnectorFieldMappingHolder;
import com.github.paohaijiao.holder.JQuickConnectorFieldProcessorHolder;
import com.github.paohaijiao.holder.JQuickConnectorHolder;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickConnectorParser;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.util.JStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JQuickConnectorCommonVisitor extends JQuickConnectorCoreVisitor {

    public JQuickConnectorCommonVisitor(JContext context) {
        this.context = context;
    }

    public JQuickConnectorCommonVisitor() {
        this.context = new JContext();
    }

    @Override
    public JQuickConnectorParsedQuery visitSelect(JQuickConnectorParser.SelectContext ctx) {
        JQuickConnectorParsedQuery connectorParsedQuery = new JQuickConnectorParsedQuery();
        JAssert.isFalse(ctx.fieldMapping().isEmpty(), "select clause not empty");
        List<JQuickConnectorFieldMappingHolder> fieldMappings = new ArrayList<>();
        for (JQuickConnectorParser.FieldMappingContext fieldMappingContext : ctx.fieldMapping()) {
            JQuickConnectorFieldMappingHolder connectorFieldMappingHolder = visitFieldMapping(fieldMappingContext);
            fieldMappings.add(connectorFieldMappingHolder);
        }
        JAssert.notNull(ctx.connector(), "connector clause not null");
        JQuickConnectorHolder connectorHolder = visitConnector(ctx.connector());
        connectorParsedQuery.setConnectorType(connectorHolder.getType());
        connectorParsedQuery.setConnectorProperties(connectorHolder.getMap());
        connectorParsedQuery.setFieldMappings(fieldMappings);
        return connectorParsedQuery;
    }

    @Override
    public JQuickConnectorFieldMappingHolder visitFieldMapping(JQuickConnectorParser.FieldMappingContext ctx) {
        JAssert.notNull(ctx.processor(), "processor not null");
        JAssert.notNull(ctx.targetField(), "target column not null");
        JAssert.notNull(ctx.dataType(), "dataType not null");
        JQuickConnectorFieldProcessorHolder connectorFieldProcessorHolder = (JQuickConnectorFieldProcessorHolder) visit(ctx.processor());
        JQuickConnectorFieldMappingHolder connectorFieldMappingHolder = new JQuickConnectorFieldMappingHolder();
        connectorFieldMappingHolder.setSourceField(connectorFieldProcessorHolder.getValue());
        connectorFieldMappingHolder.setTargetField(ctx.targetField().getText());
        connectorFieldMappingHolder.setProcessor(connectorFieldProcessorHolder.getProcessor());
        Class<?> dataType = visitDataType(ctx.dataType());
        connectorFieldMappingHolder.setDataType(dataType);
        return connectorFieldMappingHolder;
    }

    @Override
    public Class<?> visitDataType(JQuickConnectorParser.DataTypeContext ctx) {
        String dataType = ctx.getText();
        JAssert.notNull(dataType, "dataType require not null");
        JQuickConnectorDataType connectorDataType = JQuickConnectorDataType.codeOf(dataType);
        JAssert.notNull(connectorDataType, "ConnectorDataType not null");
        return connectorDataType.getClazz();
    }

    @Override
    public JQuickConnectorFieldProcessorHolder visitFieldProcessor(JQuickConnectorParser.FieldProcessorContext ctx) {
        JAssert.notNull(ctx.columnName(), "the columnName not null");
        JAssert.notNull(ctx.columnName().VAR(), "the var Name not null");
        JQuickConnectorFieldProcessorHolder connectorFieldProcessorHolder = new JQuickConnectorFieldProcessorHolder();
        String column = ctx.columnName().VAR().getText();
        connectorFieldProcessorHolder.setValue(column);
        connectorFieldProcessorHolder.setProcessor(new JQuickConnectorFieldProcessor());
        return connectorFieldProcessorHolder;
    }

    @Override
    public JQuickConnectorFieldProcessorHolder visitJsonPathProcessor(JQuickConnectorParser.JsonPathProcessorContext ctx) {
        JAssert.notNull(ctx.STRING_VALUE(), "the jsonPath not null");
        JQuickConnectorFieldProcessorHolder connectorFieldProcessorHolder = new JQuickConnectorFieldProcessorHolder();
        String jsonPath = ctx.STRING_VALUE().getText();
        connectorFieldProcessorHolder.setValue(jsonPath);
        connectorFieldProcessorHolder.setProcessor(new JQuickConnectorJsonPathProcessor());
        return connectorFieldProcessorHolder;
    }

    @Override
    public JQuickConnectorHolder visitConnector(JQuickConnectorParser.ConnectorContext ctx) {
        JAssert.notNull(ctx.connectorCode(), "connector type require not null");
        JQuickConnectorTypeEnums type = JQuickConnectorTypeEnums.codeOf(ctx.connectorCode().getText());
        JAssert.notNull(type, "connector type not validate");
        JQuickConnectorHolder connectorHolder = new JQuickConnectorHolder();
        connectorHolder.setType(type.getCode());
        Map<String, Object> map = new HashMap<>();
        for (JQuickConnectorParser.PropertyContext propertyContext : ctx.property()) {
            Map<String, Object> property = visitProperty(propertyContext);
            map.putAll(property);
        }
        connectorHolder.setMap(map);
        return connectorHolder;
    }

    @Override
    public Map<String, Object> visitProperty(JQuickConnectorParser.PropertyContext ctx) {
        JAssert.notNull(ctx.VAR(), "property name require not null");
        JAssert.notNull(ctx.value(), "property value require not null");
        String name = ctx.VAR().getText();
        Object value = visitValue(ctx.value());
        Map<String, Object> map = new HashMap<>();
        map.put(name, value);
        return map;
    }

    @Override
    public Object visitValue(JQuickConnectorParser.ValueContext ctx) {
        if (null != ctx.STRING_VALUE()) {
            return JStringUtils.trim(ctx.STRING_VALUE().getText());
        } else if (null != ctx.NUMBER_VALUE()) {
            String value = JStringUtils.trim(ctx.NUMBER_VALUE().getText());
            if (value.contains(".")) {
                return Double.valueOf(value);
            } else {
                return Long.valueOf(value);
            }
        } else if (null != ctx.BOOLEAN_VALUE()) {
            String value = JStringUtils.trim(ctx.BOOLEAN_VALUE().getText());
            return Boolean.valueOf(value);
        } else if (null != ctx.var()) {
            return visitVar(ctx.var());
        } else {
            return null;
        }
    }

    @Override
    public String visitColumnName(JQuickConnectorParser.ColumnNameContext ctx) {
        JAssert.notNull(ctx.VAR(), "column Name require not null");
        return ctx.VAR().getText();
    }

    @Override
    public Object visitVar(JQuickConnectorParser.VarContext ctx) {
        JAssert.notNull(ctx.VAR(), "var Name require not null");
        String key = ctx.VAR().getText();
        JAssert.isFalse(context.containsKey(key), "the variable[" + key + "]require not null");
        return context.get(key);
    }
}
