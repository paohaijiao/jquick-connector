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
package com.github.paohaijiao.query;

import com.github.paohaijiao.field.Processor;
import lombok.Data;

import java.util.List;
import java.util.Map;
/**
 * packageName com.github.paohaijiao.query
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
@Data
public class ParsedQuery {

    private List<FieldMapping> fieldMappings;

    private String connectorType;

    private Map<String, Object> connectorProperties;

    @Data
    public static class FieldMapping {
        private Processor processor;
        private String sourceField;
        private Class<?> dataType;
        private String targetField;
    }
}
