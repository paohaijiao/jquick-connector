package com.github.paohaijiao.handler.impl;

import com.github.paohaijiao.config.Configuration;
import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.ConnectorHandler;

public class MySqlConnectorHandler implements ConnectorHandler {
    @Override
    public DataSet execute(Configuration config) {
        ConnectorTypeFactory
        return null;
    }

    @Override
    public String getType() {
        return ConnectorTypeEnums.MYSQL.getCode();
    }
}
