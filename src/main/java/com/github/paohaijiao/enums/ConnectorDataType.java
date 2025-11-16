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
package com.github.paohaijiao.enums;

import lombok.Getter;

import java.util.Date;

@Getter
public enum ConnectorDataType {

    Integer("Integer", Integer.class),

    Long("Long", Long.class),

    Float("Float", Float.class),

    Double("Double", Double.class),

    String("String", String.class),

    Boolean("Boolean", Boolean.class),

    Date("Date", Date.class),

    Object("Object", Object.class),

    ;
    private String code;

    private Class<?> clazz;

    ConnectorDataType(String code, Class<?> clazz) {
        this.code = code;
        this.clazz = clazz;
    }

    public static ConnectorDataType codeOf(String code) {
        for (ConnectorDataType type : ConnectorDataType.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new RuntimeException("不支持" + code + "该类型");
    }
}
