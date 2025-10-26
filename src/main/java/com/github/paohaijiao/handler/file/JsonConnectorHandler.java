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

import com.github.paohaijiao.builder.JSONPathQueryBuilder;
import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JSONExecutor;
import com.github.paohaijiao.handler.AbsFileConnectorBaseHandler;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.model.JSONPathResult;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JSON 转 Row 列表工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/14
 */
public class JsonConnectorHandler extends AbsFileConnectorBaseHandler {

    protected static final String encoding = "encoding";
    protected static final String searchPath = "searchPath";

    @Override
    public String getType() {
        return ConnectorTypeEnums.JSON.getCode();
    }
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
        Boolean h = config.getProperty(header, Boolean.class);
        String connectorEncoding = config.getProperty(encoding, String.class);
        String charsetName= StringUtils.isEmpty(connectorEncoding)?Charset.defaultCharset().name():connectorEncoding;
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = Files.newInputStream(path)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charsetName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONExecutor executor = new JSONExecutor();
        Object json= executor.execute(content.toString());
        if (json instanceof JSONObject) {
            JSONObject jsonObject= (JSONObject) json;
            String connectorSearchPath = config.getProperty(searchPath, String.class);
            JSONPathResult result = JSONPathQueryBuilder.from(jsonObject).path(connectorSearchPath).execute();
            JAssert.isTrue(result.isList(),"only list result can be parsed");
            return convert(result.getAsList());
        }else {
            JAssert.throwNewException("not support this data type["+content.toString()+"]");
        }
        return new ArrayList<>() ;
    }
    public static List<Row> convert(List<Object> objectList) {
        if (objectList == null || objectList.isEmpty()) {
            return new ArrayList<>();
        }
        return objectList.stream().map(JsonConnectorHandler::convertObjectToRow).filter(obj -> true).collect(Collectors.toList());
    }

    /**
     * 转换单个对象到 Row
     */
    public static Row convertObjectToRow(Object obj) {
        if (obj == null) {
            return new Row();
        }
        Row row = new Row();
        try {
            if (obj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) obj;
                row.putAll(map);
            } else if (obj instanceof Row) {
                return (Row) obj;
            } else {
                Map<String, String> properties = BeanUtils.describe(obj);
                properties.forEach((key, value) -> {
                    if (!"class".equals(key) && value != null) {
                        row.put(key, value);
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("对象转换失败: " + e.getMessage(), e);
        }
        return row;
    }


}
