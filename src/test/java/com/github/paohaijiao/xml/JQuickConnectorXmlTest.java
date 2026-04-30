package com.github.paohaijiao.xml;

import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.xml.factory.JQuickFactory;
import com.github.paohaijiao.xml.factory.JQuickXmlFactory;
import com.github.paohaijiao.xml.handler.JQuickParseHandler;
import com.github.paohaijiao.xml.service.JQuickConnectorService;
import org.junit.Test;

import java.util.List;

public class JQuickConnectorXmlTest {
    @Test
    public  void uploadWithPostParams() throws Exception {
        JQuickParseHandler parser = new JQuickConnectorXmlParseFactory();
        JQuickFactory factory = new JQuickXmlFactory(parser, "Jquick-connector.xml");
        JQuickConnectorService userApi = factory.createApi(JQuickConnectorService.class);
        List<JQuickRow> r1=userApi.getConfigByYaml("haha");
        System.out.println(r1);
    }
}
