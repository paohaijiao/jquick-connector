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
package com.github.paohaijiao.field;

import com.github.paohaijiao.builder.JSONPathQueryBuilder;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.holder.ConnectorFieldMappingHolder;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.model.JSONPathResult;
import com.github.paohaijiao.parser.JQuickConnectorParser;
import org.apache.commons.lang3.StringUtils;

/**
 * packageName com.github.paohaijiao.field
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectorJsonPathProcessor implements ConnectorProcessor {


    @Override
    public Object process(Row row, ConnectorFieldMappingHolder mapping) {
        String sourceField = mapping.getSourceField();
        String path=trimQuotes(sourceField);
        JSONObject json = new JSONObject(row);
        JSONPathResult result = JSONPathQueryBuilder.from(json)
                .path(path)
                .execute();
        return result.getRawData();
    }
    public static String trimQuotes(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        boolean startsWithQuote = str.startsWith("\"") || str.startsWith("'");
        boolean endsWithQuote = str.endsWith("\"") || str.endsWith("'");
        if (startsWithQuote && endsWithQuote) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
    @Override
    public String getType() {
        return "jsonPath";
    }
}
