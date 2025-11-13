/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
package com.github.paohaijiao.enums;
import lombok.Getter;

@Getter
public enum ConnectorTypeEnums {

    // 文件型
    CSV("CSV", "CSV","text/csv"),
    EXCEL("EXCEL", "EXCEL", "application/vnd.ms-excel"),
    JSON("JSON", "JSON", "application/json"),
    XML("XML", "XML", "application/xml"),
    PROPERTIES("PROPERTIES", "属性文件", "text/x-properties"),
    YAML("YAML", "YAML", "text/yaml"),

    // 数据库型
    MYSQL("MYSQL", "MySQL", "application/mysql"),
    ORACLE("ORACLE", "Oracle","application/oracle"),
    POSTGRESQL("POSTGRESQL", "PostgreSQL", "application/postgresql"),
    SQL_SERVER("SQL_SERVER", "SQL Server", "application/sqlserver"),
    DB2("DB2", "DB2", "application/db2"),
    SYBASE("SYBASE", "Sybase", "application/sybase"),
    SQLITE("SQLITE", "SQLite", "application/sqlite"),
    H2("H2", "H2", "application/h2"),
    DERBY("DERBY", "Derby", "application/derby"),
    INFORMIX("INFORMIX", "Informix", "application/informix"),
    KINGBASE("KINGBASE", "KingbaseES", "application/kingbase"),
    DAMENG("DAMENG", "达梦数据库", "application/dameng"),
    HIVE("HIVE", "Hive", "application/hive"),
    IMPALA("IMPALA", "Impala", "application/impala"),
    CLICKHOUSE("CLICKHOUSE", "ClickHouse", "application/clickhouse"),
    GREENPLUM("GREENPLUM", "Greenplum", "application/greenplum"),

    // NoSQL
    MONGODB("MONGODB", "MongoDB", "application/mongodb"),
    REDIS("REDIS", "Redis", "application/redis"),
    CASSANDRA("CASSANDRA", "Cassandra", "application/cassandra"),
    HBASE("HBASE", "HBase", "application/hbase"),
    NEO4J("NEO4J", "Neo4j", "application/neo4j"),

    // 大数据与云
    HADOOP_HDFS("HADOOP_HDFS", "Hadoop HDFS", "application/hdfs"),
    S3("S3", "Amazon S3", "application/s3"),
    BIGQUERY("BIGQUERY", "Google BigQuery", "application/bigquery"),
    SNOWFLAKE("SNOWFLAKE", "Snowflake", "application/snowflake"),
    KAFKA("KAFKA", "Kafka", "application/kafka"),

    // API 与 Web
    CURL("CURL", "CURL", "application/curl"),
    SOAP("SOAP", "SOAP Web Service", "application/soap+xml"),

    RABBITMQ("RABBITMQ", "RabbitMQ", "application/rabbitmq"),
    ACTIVEMQ("ACTIVEMQ", "ActiveMQ", "application/activemq"),

    // 其他
    LDAP("LDAP", "LDAP", "application/ldap"),
    SALESFORCE("SALESFORCE", "Salesforce", "application/salesforce"),
    SAP("SAP", "SAP", "application/sap"),

    // 自定义或脚本
    JAVASCRIPT("JAVASCRIPT", "JavaScript", "application/javascript"),
    GROOVY("GROOVY", "Groovy", "application/groovy"),
    PYTHON("PYTHON", "Python", "application/python");

    private String code;

    private String name;

    private String mime;

    ConnectorTypeEnums(String code, String name,String mime){
        this.code = code;
        this.name = name;
        this.mime = mime;
    }
    public static ConnectorTypeEnums codeOf(String code){
        for (ConnectorTypeEnums type : ConnectorTypeEnums.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new RuntimeException("不支持" + code+"该类型");
    }
}
