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
import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.enums.JLogLevel;
import com.github.paohaijiao.handler.JQuickConnectorAbsFileBaseHandler;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.provider.JQuickConnectorTypeProvider;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.registry.JQuickConnectorTypeFactory;
import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.util.JQuickConnectorJSonExtractUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * JSON 转 Row 列表工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/14
 */
public class JQuickConnectorPropertiesHandler extends JQuickConnectorAbsFileBaseHandler implements JQuickConnectorTypeProvider {

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
    public List<JQuickRow> doParse(Path path, JQuickConnectorParsedQuery query) {
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        String connectorPath = config.getProperty(filepath, String.class);
        try {
            String content =convertToNestedJson(connectorPath);
            console.log(JLogLevel.INFO,"json: " + content);
            String connectorSearchPath = config.getProperty(searchPath, String.class);
            List<JQuickRow> rows = JQuickConnectorJSonExtractUtil.buildRowsFromJSon(content, connectorSearchPath);
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
    public JQuickConnectorType getConnectorType() {
        JQuickConnectorTypeFactory connectorTypeFactory = JQuickConnectorTypeFactory.getInstance();
        JQuickConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(JQuickConnectorTypeEnums.PROPERTIES.getCode(), JQuickConnectorTypeEnums.PROPERTIES.getName(), JQuickConnectorCategory.FILE);
        JQuickConnectorType connectorType = connectorTypeBuilder.withAliases(JQuickConnectorTypeEnums.PROPERTIES.getCode(), JQuickConnectorTypeEnums.PROPERTIES.getMime()).withMetadata(new JQuickConnectorTypeMetadata("1.0", JQuickConnectorCategory.FILE.getDescription(), JQuickConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
