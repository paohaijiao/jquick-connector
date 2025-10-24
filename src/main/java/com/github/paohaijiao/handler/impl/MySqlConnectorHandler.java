package com.github.paohaijiao.handler.impl;

import com.github.paohaijiao.config.ConnectorConfig;
import com.github.paohaijiao.data.DataSet;
import com.github.paohaijiao.enums.ConnectorType;
import com.github.paohaijiao.handler.ConnectorHandler;

public class MySqlConnectorHandler implements ConnectorHandler {
    @Override
    public DataSet execute(ConnectorConfig config) {
        return null;
    }

    @Override
    public String getType() {
        return ConnectorType.MYSQL.getCode();
    }
}
