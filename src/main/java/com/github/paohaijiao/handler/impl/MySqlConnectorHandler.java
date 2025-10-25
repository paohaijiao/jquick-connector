package com.github.paohaijiao.handler.impl;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsDatabaseConnectorBaseHandler;

public class MySqlConnectorHandler extends AbsDatabaseConnectorBaseHandler {



    @Override
    public String getType() {
        return ConnectorTypeEnums.MYSQL.getCode();
    }
}
