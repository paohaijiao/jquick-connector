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

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.executor.JQuickXMLExecutor;
import com.github.paohaijiao.handler.AbsFileConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.provider.ConnectorTypeProvider;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.registry.ConnectorTypeFactory;
import com.github.paohaijiao.util.JSonExtractUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * JSON 转 Row 列表工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/14
 */
public class XmlConnectorHandler extends AbsFileConnectorBaseHandler implements ConnectorTypeProvider {

    protected static final String encoding = "encoding";

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
        String connectorEncoding = config.getProperty(encoding, String.class);
        String charsetName = StringUtils.isEmpty(connectorEncoding) ? Charset.defaultCharset().name() : connectorEncoding;
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = Files.newInputStream(path)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charsetName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String connectorSearchPath = config.getProperty(searchPath, String.class);
        JQuickXMLExecutor executor = new JQuickXMLExecutor();
        JSONObject jsonObject=executor.execute(content.toString());
        String jsonString = jsonObject.toString();
        List<Row> rows = JSonExtractUtil.buildRowsFromJSon(jsonString, connectorSearchPath);
        return rows;
    }


    @Override
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory = ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(ConnectorTypeEnums.XML.getCode(), ConnectorTypeEnums.XML.getName(), ConnectorCategory.FILE);
        ConnectorType connectorType = connectorTypeBuilder.withAliases(ConnectorTypeEnums.XML.getCode(), ConnectorTypeEnums.XML.getMime()).withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.FILE.getDescription(), ConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
