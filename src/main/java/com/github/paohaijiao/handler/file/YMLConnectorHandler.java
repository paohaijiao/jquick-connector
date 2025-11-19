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
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsFileConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.provider.ConnectorTypeProvider;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.registry.ConnectorTypeFactory;
import com.github.paohaijiao.util.JSonExtractUtil;

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
public class YMLConnectorHandler extends AbsFileConnectorBaseHandler implements ConnectorTypeProvider {


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
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        String connectorPath = config.getProperty(filepath, String.class);
        try {
            Object obj = yamlReader.readValue(new File(connectorPath), Object.class);
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            String connectorSearchPath = config.getProperty(searchPath, String.class);
            List<Row> rows = JSonExtractUtil.buildRowsFromJSon(json, connectorSearchPath);
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory = ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = ConnectorTypeFactory.buildType(ConnectorTypeEnums.YAML.getCode(), ConnectorTypeEnums.YAML.getName(), ConnectorCategory.FILE);
        ConnectorType connectorType = connectorTypeBuilder.withAliases(ConnectorTypeEnums.YAML.getCode(), ConnectorTypeEnums.YAML.getMime()).withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.FILE.getDescription(), ConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
