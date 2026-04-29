package com.github.paohaijiao.handler.database;

import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.handler.JQuickConnectorAbsDatabaseBaseHandler;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.provider.JQuickConnectorTypeProvider;
import com.github.paohaijiao.registry.JQuickConnectorTypeFactory;

public class JQuickConnectorPostgreSQLHandler extends JQuickConnectorAbsDatabaseBaseHandler implements JQuickConnectorTypeProvider {


    @Override
    public JQuickConnectorType getConnectorType() {
        JQuickConnectorTypeFactory connectorTypeFactory = JQuickConnectorTypeFactory.getInstance();
        JQuickConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(JQuickConnectorTypeEnums.POSTGRESQL.getCode(), JQuickConnectorTypeEnums.POSTGRESQL.getName(), JQuickConnectorCategory.DATABASE);
        ;
        JQuickConnectorType connectorType = connectorTypeBuilder.withAliases(JQuickConnectorTypeEnums.POSTGRESQL.getCode(), JQuickConnectorTypeEnums.POSTGRESQL.getCode()).withMetadata(new JQuickConnectorTypeMetadata("1.0", JQuickConnectorCategory.DATABASE.getDescription(), JQuickConnectorCategory.DATABASE.getDescription())).build();
        return connectorType;
    }
}
