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
import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.executor.JQuickXMLExecutor;
import com.github.paohaijiao.handler.JQuickConnectorAbsFileBaseHandler;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.provider.JQuickConnectorTypeProvider;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.registry.JQuickConnectorTypeFactory;
import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.util.JQuickConnectorJSonExtractUtil;
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
public class JQuickConnectorXmlHandler extends JQuickConnectorAbsFileBaseHandler implements JQuickConnectorTypeProvider {

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
    public List<JQuickRow> doParse(Path path, JQuickConnectorParsedQuery query) {
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
        List<JQuickRow> rows = JQuickConnectorJSonExtractUtil.buildRowsFromJSon(jsonString, connectorSearchPath);
        return rows;
    }


    @Override
    public JQuickConnectorType getConnectorType() {
        JQuickConnectorTypeFactory connectorTypeFactory = JQuickConnectorTypeFactory.getInstance();
        JQuickConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(JQuickConnectorTypeEnums.XML.getCode(), JQuickConnectorTypeEnums.XML.getName(), JQuickConnectorCategory.FILE);
        JQuickConnectorType connectorType = connectorTypeBuilder.withAliases(JQuickConnectorTypeEnums.XML.getCode(), JQuickConnectorTypeEnums.XML.getMime()).withMetadata(new JQuickConnectorTypeMetadata("1.0", JQuickConnectorCategory.FILE.getDescription(), JQuickConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
