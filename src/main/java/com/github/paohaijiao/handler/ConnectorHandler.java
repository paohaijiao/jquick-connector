package com.github.paohaijiao.handler;

import com.github.paohaijiao.config.ConnectorConfig;
import com.github.paohaijiao.data.DataSet;

public interface ConnectorHandler {


    DataSet execute(ConnectorConfig config);

    String getType();
}
