package com.github.paohaijiao.handler;

import com.github.paohaijiao.config.Configuration;
import com.github.paohaijiao.dataset.Row;
import java.util.List;

public abstract class AbsConnectorBaseHandler implements ConnectorHandler {

    /**
     * buildRow
     * @return
     */
    public abstract List<Row> buildRow(Configuration config);



}
