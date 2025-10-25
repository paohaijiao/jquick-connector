package com.github.paohaijiao.handler;

import com.github.paohaijiao.data.ColumnMeta;
import com.github.paohaijiao.data.Row;

import java.util.List;

public abstract class AbsConnectorBaseHandler implements ConnectorHandler {
    /**
     * buildColumns
     * @return
     */
    public abstract List<ColumnMeta> buildColumns();

    /**
     * buildRow
     * @return
     */
    public abstract List<Row> buildRow();
}
