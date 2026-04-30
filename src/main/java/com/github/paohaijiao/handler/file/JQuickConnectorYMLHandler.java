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
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.paohaijiao.config.JQuickConnectorConfiguration;
import com.github.paohaijiao.config.JQuickConnectorConfigurationImpl;
import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.handler.JQuickConnectorAbsFileBaseHandler;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.provider.JQuickConnectorTypeProvider;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.registry.JQuickConnectorTypeFactory;
import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.util.JQuickConnectorJSonExtractUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON 转 Row 列表工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/14
 */
public class JQuickConnectorYMLHandler extends JQuickConnectorAbsFileBaseHandler implements JQuickConnectorTypeProvider {


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
        JQuickConnectorConfiguration config = new JQuickConnectorConfigurationImpl();
        query.getConnectorProperties().forEach(config::setProperty);
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        String connectorPath = config.getProperty(filepath, String.class);
        try {
            Object obj = yamlReader.readValue(new File(connectorPath), Object.class);
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            String connectorSearchPath = config.getProperty(searchPath, String.class);
            List<JQuickRow> rows = JQuickConnectorJSonExtractUtil.buildRowsFromJSon(json, connectorSearchPath);
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public JQuickConnectorType getConnectorType() {
        JQuickConnectorTypeFactory connectorTypeFactory = JQuickConnectorTypeFactory.getInstance();
        JQuickConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = JQuickConnectorTypeFactory.buildType(JQuickConnectorTypeEnums.YAML.getCode(), JQuickConnectorTypeEnums.YAML.getName(), JQuickConnectorCategory.FILE);
        JQuickConnectorType connectorType = connectorTypeBuilder.withAliases(JQuickConnectorTypeEnums.YAML.getCode(), JQuickConnectorTypeEnums.YAML.getMime()).withMetadata(new JQuickConnectorTypeMetadata("1.0", JQuickConnectorCategory.FILE.getDescription(), JQuickConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
