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

import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.holder.ConnectorFieldMappingHolder;

/**
 * packageName com.github.paohaijiao.field
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectorFieldProcessor implements ConnectorProcessor {

    @Override
    public Object process(Row row, ConnectorFieldMappingHolder mapping) {
        String sourceField = mapping.getSourceField();
        boolean existsColumn=row.containsKey(sourceField);
        JAssert.isTrue(existsColumn,"the coloumn[" +sourceField+"] not exists");
        Object value = row.get(sourceField);
        return value;
    }

    @Override
    public String getType() {
        return "field";
    }
}
