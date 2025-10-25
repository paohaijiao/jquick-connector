package com.github.paohaijiao.handler.database;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsDatabaseConnectorBaseHandler;

public class H2ConnectorHandler extends AbsDatabaseConnectorBaseHandler {

    @Override
    public String getType() {
        return ConnectorTypeEnums.H2.getCode();
    }
}
