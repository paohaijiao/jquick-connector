package com.github.paohaijiao.handler;

import com.github.paohaijiao.dataset.ColumnMeta;
import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.dataset.Row;
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

    public DataSet buildDataSet() {
        DataSet.Builder builder = DataSet.builder();
        List<ColumnMeta> list= this.buildColumns();
        list.forEach(e->{
            builder.addColumn(e.getName(), e.getType(), e.getSource());
        });
        List<Row> rows= this.buildRow();
        rows.forEach(builder::addRow);
        return builder.build();
    }
}
