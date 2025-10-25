package com.github.paohaijiao.handler;

import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.query.ConnectorParsedQuery;

import java.util.List;

public abstract class AbsConnectorBaseHandler implements ConnectorHandler {

    /**
     * buildRow
     * @return
     */
    public abstract List<Row> buildRow(ConnectorParsedQuery query);



}
