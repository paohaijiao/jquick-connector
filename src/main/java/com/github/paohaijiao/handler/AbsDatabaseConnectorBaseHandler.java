package com.github.paohaijiao.handler;


import com.github.paohaijiao.config.Configuration;
import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.query.ConnectorParsedQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsDatabaseConnectorBaseHandler extends AbsConnectorBaseHandler {

    private static final  String driver="driver";

    private static final  String url="url";

    private static final  String username="username";

    private static final  String password="password";

    private static final  String sql="sql";
    public Connection doConnection(Configuration config){
        String connectorDriverClass =config.getProperty(driver,String.class);
        JAssert.notEmptyStr(connectorDriverClass,"driver class require not null");
        String connectorUrl=config.getProperty(url,String.class);
        JAssert.notEmptyStr(connectorUrl,"url  require not null");
        String connectorUsername=config.getProperty(username,String.class);
        JAssert.notEmptyStr(connectorUsername,"username  require not null");
        String connectorPassword=config.getProperty(password,String.class);
        JAssert.notEmptyStr(connectorPassword,"password  require not null");
        String connectorSql=config.getProperty(sql,String.class);
        JAssert.notEmptyStr(connectorSql,"sql  require not null");
        try {
            Class.forName(connectorDriverClass);
            Connection connection = DriverManager.getConnection(connectorUrl, connectorUsername, connectorPassword);
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param query
     * @return
     */
    @Override
    public List<Row> buildRow(ConnectorParsedQuery query){
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        List<Row> rows = new ArrayList<>();
        Connection connection = doConnection(config);
        JAssert.notNull(connection,"connection is null");
        String connectorSql=config.getProperty(sql,String.class);
        JAssert.notEmptyStr(connectorSql,"sql  require not null");
        try {
            PreparedStatement stmt = connection.prepareStatement(connectorSql);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Row row = new Row();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                rows.add(row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        doRelease(connection);
        return rows;
    }
    public void doRelease(Connection connection){
        if(connection != null){
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
