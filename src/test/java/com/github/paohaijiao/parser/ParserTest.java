package com.github.paohaijiao.parser;

import com.github.paohaijiao.executor.JQuickConnectorExecutor;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import org.junit.Test;

public class ParserTest {
    @Test
    public  void test() throws Exception {
        JContext context = new JContext();
        context.put("user", "test");
        JQuickConnectorExecutor executor = new JQuickConnectorExecutor(context);
        ConnectorParsedQuery parsedQuery=executor.execute("SELECT\n" +
                "    field(id)->b:Integer,\n" +
                "    field(c)->b:Integer\n" +
                "FROM MYSQL(\n" +
                "    url: 'localhost',\n" +
                "    port: 3306,\n" +
                "    database: 'user_db',\n" +
                "    sql: 'select * from a',\n" +
                "    username: 'root',\n" +
                "    password: '123456'\n" +
                ")");

        System.out.println(parsedQuery);

    }
    @Test
    public  void testMysql() throws Exception {
        JContext context = new JContext();
        context.put("user", "test");
        JQuickConnectorExecutor executor = new JQuickConnectorExecutor(context);
        ConnectorParsedQuery parsedQuery=executor.execute("SELECT\n" +
                "    field(id)->b:Integer,\n" +
                "    field(c)->b:Integer\n" +
                "FROM MYSQL(\n" +
                "    url: '192.168.32.143',\n" +
                "    port: 3306,\n" +
                "    database: 'user_db',\n" +
                "    sql: 'select * from a',\n" +
                "    username: 'root',\n" +
                "    password: '123456'\n" +
                ")");

        System.out.println(parsedQuery);

    }
}
