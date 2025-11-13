package com.github.paohaijiao.handler.rest;

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JQuickCurlExecutor;
import com.github.paohaijiao.handler.AbsConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.model.JResult;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.registry.ConnectorTypeFactory;
import com.github.paohaijiao.util.JSonExtractUtil;

import java.util.List;

public class CurlConnectorHandler extends AbsConnectorBaseHandler {

    protected static final  String curl="curl";

    protected static final String searchPath = "searchPath";

    @Override
    public List<Row> buildRow(ConnectorParsedQuery query) {
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        String connectorcurl= config.getProperty(curl,String.class);
        JQuickCurlExecutor executor=new JQuickCurlExecutor();
        JResult jResult=executor.execute(connectorcurl);
        JAssert.notNull(jResult,"result required not null");
        JAssert.notNull(jResult.getString(),"result data required not null");
        String result=jResult.getString();
        String connectorSearchPath = config.getProperty(searchPath, String.class);
        List<Row> rows=JSonExtractUtil.buildRowsFromJSon(result,connectorSearchPath);
        return rows;
    }


    @Override
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory=ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder=connectorTypeFactory.buildType(ConnectorTypeEnums.CURL.getCode(), ConnectorTypeEnums.CURL.getName(), ConnectorCategory.FILE);;
        ConnectorType connectorType=connectorTypeBuilder.withAliases(ConnectorTypeEnums.CURL.getCode(), ConnectorTypeEnums.CURL.getMime()).withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.FILE.getDescription(),  ConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
