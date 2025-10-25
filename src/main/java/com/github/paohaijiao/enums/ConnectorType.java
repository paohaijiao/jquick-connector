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
public enum ConnectorType {

    // 文件型
    CSV("CSV", "CSV"),
    EXCEL("EXCEL", "EXCEL"),
    JSON("JSON", "JSON"),
    XML("XML", "XML"),
    PROPERTIES("PROPERTIES", "属性文件"),
    YAML("YAML", "YAML"),

    // 数据库型
    MYSQL("MYSQL", "MySQL"),
    ORACLE("ORACLE", "Oracle"),
    POSTGRESQL("POSTGRESQL", "PostgreSQL"),
    SQL_SERVER("SQL_SERVER", "SQL Server"),
    DB2("DB2", "DB2"),
    SYBASE("SYBASE", "Sybase"),
    SQLITE("SQLITE", "SQLite"),
    H2("H2", "H2"),
    DERBY("DERBY", "Derby"),
    INFORMIX("INFORMIX", "Informix"),
    KINGBASE("KINGBASE", "KingbaseES"),
    DAMENG("DAMENG", "达梦数据库"),
    HIVE("HIVE", "Hive"),
    IMPALA("IMPALA", "Impala"),
    CLICKHOUSE("CLICKHOUSE", "ClickHouse"),
    GREENPLUM("GREENPLUM", "Greenplum"),

    // NoSQL
    MONGODB("MONGODB", "MongoDB"),
    REDIS("REDIS", "Redis"),
    CASSANDRA("CASSANDRA", "Cassandra"),
    HBASE("HBASE", "HBase"),
    NEO4J("NEO4J", "Neo4j"),

    // 大数据与云
    HADOOP_HDFS("HADOOP_HDFS", "Hadoop HDFS"),
    S3("S3", "Amazon S3"),
    BIGQUERY("BIGQUERY", "Google BigQuery"),
    SNOWFLAKE("SNOWFLAKE", "Snowflake"),
    KAFKA("KAFKA", "Kafka"),

    // API 与 Web
    CURL("CURL", "CURL"),
    SOAP("SOAP", "SOAP Web Service"),

    RABBITMQ("RABBITMQ", "RabbitMQ"),
    ACTIVEMQ("ACTIVEMQ", "ActiveMQ"),

    // 其他
    LDAP("LDAP", "LDAP"),
    SALESFORCE("SALESFORCE", "Salesforce"),
    SAP("SAP", "SAP"),

    // 自定义或脚本
    JAVASCRIPT("JAVASCRIPT", "JavaScript"),
    GROOVY("GROOVY", "Groovy"),
    PYTHON("PYTHON", "Python");
    private String code;
    private String name;

    ConnectorType(String code, String name){
        this.code = code;
        this.name = name;
    }
    public static ConnectorType codeOf(String code){
        for (ConnectorType type : ConnectorType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new RuntimeException("不支持" + code+"该类型");
    }
}
