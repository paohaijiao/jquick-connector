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
package com.github.paohaijiao.handler;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.query.ConnectorParsedQuery;

import java.util.List;

public abstract class AbsConnectorBaseHandler implements ConnectorHandler {

    protected JConsole console=new JConsole();

    protected JContext context=new JContext();

    /**
     * buildRow
     * @return
     */
    public abstract List<Row> buildRow(ConnectorParsedQuery query);



}
