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
import com.github.paohaijiao.config.Configuration;
import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.query.ConnectorParsedQuery;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public abstract class AbsFileConnectorBaseHandler extends AbsConnectorBaseHandler {

    private static final  String filepath="filepath";
    protected static final  String header="header";

    public Path  doGetPath(Configuration config){
        String connectorPath =config.getProperty(filepath,String.class);
        JAssert.notEmptyStr(connectorPath,"filepath require not null");
        return  Paths.get(connectorPath);
    }

    public abstract List<Row> doParse(Path path,ConnectorParsedQuery query);

    /**
     *
     * @param query
     * @return
     */
    @Override
    public List<Row> buildRow(ConnectorParsedQuery query){
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        Path path = doGetPath(config);
        JAssert.notNull(path,"filepath require not null");
        List<Row> rows = doParse(path,query);
        return rows;
    }

}
