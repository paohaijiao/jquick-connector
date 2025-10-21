package com.github.paohaijiao.result;

import com.github.paohaijiao.config.ConnectorConfig;
import com.github.paohaijiao.data.DataSet;

public interface Connector {


    DataSet execute(ConnectorConfig config);

    String getType();
}
