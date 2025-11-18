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
package com.github.paohaijiao.handler.file;

/**
 * packageName com.github.paohaijiao.handler.file
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/26
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.enums.JLogLevel;
import com.github.paohaijiao.executor.JQuickXMLExecutor;
import com.github.paohaijiao.handler.AbsFileConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.provider.ConnectorTypeProvider;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.registry.ConnectorTypeFactory;
import com.github.paohaijiao.util.JSonExtractUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * JSON 转 Row 列表工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/14
 */
public class PropertiesConnectorHandler extends AbsFileConnectorBaseHandler implements ConnectorTypeProvider {

    private static final ObjectMapper mapper = new ObjectMapper();


    protected static final String searchPath = "searchPath";

    /**
     * 将 JSON 文件转换为 List<Row>
     *
     * @param path JSON 文件路径
     * @return List<Row> 对象列表
     * @throws IOException 如果文件读取失败或 JSON 解析失败
     */
    @Override
    public List<Row> doParse(Path path, ConnectorParsedQuery query) {
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        String connectorPath = config.getProperty(filepath, String.class);
        try {
            String content =convertToNestedJson(connectorPath);
            console.log(JLogLevel.INFO,"json: " + content);
            String connectorSearchPath = config.getProperty(searchPath, String.class);
            List<Row> rows = JSonExtractUtil.buildRowsFromJSon(content, connectorSearchPath);
            return rows;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    private static void setNestedValue(Map<String, Object> map, String key, Object value) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = map;

        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new HashMap<>());
        }

        current.put(keys[keys.length - 1], value);
    }
    private static Map<String, Object> convertToNestedMap(Properties properties) {
        Map<String, Object> result = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            setNestedValue(result, key, properties.getProperty(key));
        }
        return result;
    }
    public static String convertToNestedJson(String propertiesFilePath) throws IOException {
        Properties properties = loadProperties(propertiesFilePath);
        Map<String, Object> nestedMap = convertToNestedMap(properties);
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(nestedMap);
    }
    private static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
        return properties;
    }





    @Override
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory = ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(ConnectorTypeEnums.PROPERTIES.getCode(), ConnectorTypeEnums.PROPERTIES.getName(), ConnectorCategory.FILE);
        ConnectorType connectorType = connectorTypeBuilder.withAliases(ConnectorTypeEnums.PROPERTIES.getCode(), ConnectorTypeEnums.PROPERTIES.getMime()).withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.FILE.getDescription(), ConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
