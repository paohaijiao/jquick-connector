package com.github.paohaijiao.handler.rest;

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickCurlExecutor;
import com.github.paohaijiao.handler.JQuickConnectorAbsBaseHandler;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.model.JResult;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.registry.JQuickConnectorTypeFactory;
import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.util.JQuickConnectorJSonExtractUtil;

import java.util.List;

public class JQuickConnectorCurlHandler extends JQuickConnectorAbsBaseHandler {

    protected static final String curl = "curl";

    protected static final String searchPath = "searchPath";

    @Override
    public List<JQuickRow> buildRow(JQuickConnectorParsedQuery query) {
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        String connectorcurl = config.getProperty(curl, String.class);
        JQuickCurlExecutor executor = new JQuickCurlExecutor();
        JResult jResult = executor.execute(connectorcurl);
        JAssert.notNull(jResult, "result required not null");
        JAssert.notNull(jResult.getString(), "result data required not null");
        String result = jResult.getString();
        String connectorSearchPath = config.getProperty(searchPath, String.class);
        List<JQuickRow> rows = JQuickConnectorJSonExtractUtil.buildRowsFromJSon(result, connectorSearchPath);
        return rows;
    }


    @Override
    public JQuickConnectorType getConnectorType() {
        JQuickConnectorTypeFactory connectorTypeFactory = JQuickConnectorTypeFactory.getInstance();
        JQuickConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(JQuickConnectorTypeEnums.CURL.getCode(), JQuickConnectorTypeEnums.CURL.getName(), JQuickConnectorCategory.FILE);
        ;
        JQuickConnectorType connectorType = connectorTypeBuilder.withAliases(JQuickConnectorTypeEnums.CURL.getCode(), JQuickConnectorTypeEnums.CURL.getMime()).withMetadata(new JQuickConnectorTypeMetadata("1.0", JQuickConnectorCategory.FILE.getDescription(), JQuickConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
