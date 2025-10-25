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

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.enums.JLogLevel;
import com.github.paohaijiao.executor.JQuickConnectorExecutor;
import com.github.paohaijiao.param.JContext;

/**
 * packageName com.github.paohaijiao.query
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectorQueryParser {
    private JContext context;

    public ConnectorQueryParser(JContext context) {
        this.context = context;
    }
    public ConnectorQueryParser() {
        this.context = new JContext();
    }
    public ConnectorParsedQuery parse(String query) {
        JConsole console = new JConsole();
        JQuickConnectorExecutor executor = new JQuickConnectorExecutor(context);
        console.log(JLogLevel.INFO, "query: " + query);
        ConnectorParsedQuery parsedQuery=executor.execute(query);
        console.log(JLogLevel.INFO, "parsedQuery: " + parsedQuery);
        return parsedQuery;
    }
}
