package com.github.paohaijiao.parser;

import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.executor.JQuickConnectorExecutor;
import com.github.paohaijiao.factory.ConnectorFactory;
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
        String query="SELECT\n" +
                "    field(id)->cid:Long,\n" +
                "    field(user_id)->user_id:Long,\n" +
                "    field(title)->title:String,\n" +
                "    field(content)->content:String,\n" +
                "    field(type)->type:String,\n" +
                "    field(tenant_id)->tenant_id:Long,\n" +
                "    field(created_time)->created_time:Date,\n" +
                "    field(is_deleted)->is_deleted:Boolean\n" +
                "FROM MYSQL(\n" +
                "    url: 'jdbc:mysql://192.168.32.144:3306/jquickbi?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true',\n" +
                "    username: 'root',\n" +
                "    password: '1qaz@WSX',\n" +
                "    sql: 'select * from business_activity',\n" +
                "    driver: 'com.mysql.jdbc.Driver'\n" +
                ")";
        ConnectorFactory factory = new ConnectorFactory();
        DataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("created_time: " + row.get("created_time") + ", title: " + row.getString("title"));
        });
    }

}
