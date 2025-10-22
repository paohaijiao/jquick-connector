package com.github.paohaijiao.handler;

import com.github.paohaijiao.config.ConnectorBaseConfig;
import com.github.paohaijiao.data.ColumnMeta;
import com.github.paohaijiao.data.Row;

import java.util.List;

public abstract class AbsConnectorBaseHandler implements ConnectorHandler {

    public abstract List<ColumnMeta> buildColumnMetaList();

    public abstract List<Row> buildRow();
}
