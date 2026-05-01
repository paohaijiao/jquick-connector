package com.github.paohaijiao.xml;

import com.github.paohaijiao.statement.JQuickDataSet;
import com.github.paohaijiao.xml.factory.JQuickFactory;
import com.github.paohaijiao.xml.factory.JQuickXmlFactory;
import com.github.paohaijiao.xml.handler.JQuickParseHandler;
import com.github.paohaijiao.xml.service.JQuickConnectorService;
import org.junit.Test;

public class JQuickConnectorXmlTest {
    @Test
    public  void uploadWithPostParams() throws Exception {
        JQuickParseHandler parser = new JQuickConnectorXmlParseFactory();
        JQuickFactory factory = new JQuickXmlFactory(parser, "jquick-connector.xml");
        JQuickConnectorService userApi = factory.createApi(JQuickConnectorService.class);
        JQuickDataSet dataSet=userApi.getConfigByYaml("haha");
        dataSet.printTable();
    }
}
