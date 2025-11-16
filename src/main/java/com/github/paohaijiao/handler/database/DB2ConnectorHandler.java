package com.github.paohaijiao.handler.database;

import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsDatabaseConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.provider.ConnectorTypeProvider;
import com.github.paohaijiao.registry.ConnectorTypeFactory;

public class DB2ConnectorHandler extends AbsDatabaseConnectorBaseHandler implements ConnectorTypeProvider {


    @Override
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory = ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(ConnectorTypeEnums.DB2.getCode(), ConnectorTypeEnums.DB2.getName(), ConnectorCategory.DATABASE);
        ;
        ConnectorType connectorType = connectorTypeBuilder.withAliases(ConnectorTypeEnums.DB2.getCode(), ConnectorTypeEnums.DB2.getCode()).withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.DATABASE.getDescription(), ConnectorCategory.DATABASE.getDescription())).build();
        return connectorType;
    }
}
