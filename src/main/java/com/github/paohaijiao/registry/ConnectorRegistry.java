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
package com.github.paohaijiao.registry;

import com.github.paohaijiao.connector.sql.MySqlConnector;
import com.github.paohaijiao.result.Connector;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * packageName com.github.paohaijiao.registry
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectorRegistry {
    private static final Map<String, Connector> connectors = new HashMap<>();

    static {
        ServiceLoader<Connector> loader = ServiceLoader.load(Connector.class);
        for (Connector connector : loader) {
            registerConnector(connector.getType(), connector);
        }
//        registerConnector("MYSQL", new MySqlConnector());
    }

    public static void registerConnector(String type, Connector connector) {
        connectors.put(type.toUpperCase(), connector);
    }

    public static Connector getConnector(String type) {
        return connectors.get(type.toUpperCase());
    }
}
