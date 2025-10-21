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
package com.github.paohaijiao.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName com.github.paohaijiao.config
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
@Data
public class BaseConnectorConfig  implements ConnectorConfig {

    private Map<String, Object> properties = new HashMap<>();

    @Override
    public String getProperty(String key) {
        Object value = properties.get(key);
        return value != null ? value.toString() : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key, Class<T> type) {
        Object value = properties.get(key);
        if (value == null) return null;
        if (type.isInstance(value)) {
            return (T) value;
        }
        return convertValue(value, type);
    }

    @Override
    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    @SuppressWarnings("unchecked")
    private <T> T convertValue(Object value, Class<T> type) {
        if (type == String.class) {
            return (T) value.toString();
        } else if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(value.toString());
        } else if (type == Long.class || type == long.class) {
            return (T) Long.valueOf(value.toString());
        } else if (type == Double.class || type == double.class) {
            return (T) Double.valueOf(value.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            return (T) Boolean.valueOf(value.toString());
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }
}
