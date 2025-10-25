package com.github.paohaijiao.handler.database;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsDatabaseConnectorBaseHandler;

public class ClickHouseConnectorHandler extends AbsDatabaseConnectorBaseHandler {

    @Override
    public String getType() {
        return ConnectorTypeEnums.CLICKHOUSE.getCode();
    }
}
