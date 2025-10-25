package com.github.paohaijiao.handler;


import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.exception.JAssert;

import java.sql.*;

public abstract class AbsDatabaseConnectorBaseHandler extends AbsConnectorBaseHandler {

    private static final  String driver="driver";

    private static final  String url="url";

    private static final  String username="username";

    private static final  String password="password";

    private static final  String sql="sql";
    /**
     *
     * @param config
     * @return
     */
    public ResultSet doResultSet(ConnectorConfiguration config){
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
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            DataSet dataSet = doDataSet(rs);
            rs.close();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private DataSet doDataSet(ResultSet rs) throws SQLException {
        DataSet.Builder builder = DataSet.builder();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            String source = metaData.getTableName(i);
//            Class<?> columnType = getJavaType(metaData.getColumnType(i));
//            builder.addColumn(columnName, columnType, source);
        }
        while (rs.next()) {
            Row row = new Row();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                row.put(columnName, value);
            }
            builder.addRow(row);
        }
        return builder.build();
    }
}
