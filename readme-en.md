# JQuick Connector

[简体中文](./README.md)| English
A powerful data connector framework that supports unified querying of multiple data sources using SQL-like syntax,
including databases, files, and REST APIs.

## Features

- **Unified Query Interface**: Query diverse data sources using SQL-like syntax
- **Multiple Data Source Support**:
    - **Databases**: MySQL、PostgreSQL、Oracle、ClickHouse、H2 etc
    - **Files**: CSV、Excel、JSON
    - **REST API**: HTTP requests based on jQuickCURL
- **Flexible Field Mapping**: Extract and transform data through field processors
- **Type Safet**: Supports strong typing definition of data fields
- **Context Variables**: Supports parameterized queries using variables

## Quick Start

### Basic Query Syntax

```sql
SELECT
    field(source field) -> target field:数据类型,
    path('$.json.path') -> json field:string
FROM connector type(
    attribute1: 'value1',
    attribute2: 123,
    attribute3: {变量名}
)
```

### MySQL Database Query Example

```sql
SELECT
    field(id) -> cid:Long,
    field(user_id) -> user_id:Long,
    field(title) -> title:String,
    field(content) -> content:String,
    field(type) -> type:String,
    field(tenant_id) -> tenant_id:Long,
    field(created_time) -> created_time:Date,
    field(is_deleted) -> is_deleted:Boolean
FROM MYSQL(
    url: 'jdbc:mysql://localhost:3306/database',
    username: 'root',
    password: 'password',
    sql: 'select * from business_activity',
    driver: 'com.mysql.jdbc.Driver'
)
```

### CSV File Query Example

```sql
SELECT
  field(id) -> id:Long,
        field(name) -> name:String,
        field(age) -> age:Integer,
        field(salary) -> salary:Double,
        field(is_active) -> is_active:Boolean,
        field(birth_date) -> birth_date:Date
FROM CSV(
        filepath: 'D:/data/data.csv',
        split: ',',
        header: 'true'
     )
```

### JSON File Query Example

```sql
SELECT
        field(id) -> id:string,
        field(name) -> name:string,
        path('$.manager.title') -> title:string,
        field(manager) -> manager:object
FROM JSON(
        filepath: 'D:/data/data.json',
        searchPath: '$.departments'
     )
```

### REST API Query Example

```sql
SELECT
         field(projectId) -> projectId:string,
        field(projectName) -> projectName:string,
        field(projectStage) -> projectStage:object
FROM CURL(
        curl: `curl -X GET 'http://api.example.com/data' -H 'User-Agent: Mozilla/5.0'`,
        searchPath: '$.data'
     )
```

### Excel File Query Example

```sql
SELECT
        field(is_active) -> is_active:boolean,
        field(birth_date) -> birth_date:date,
        field(name) -> name:string,
        field(id) -> id:Long,
        field(salary) -> salary:double,
        field(age) -> age:integer
FROM excel(
        filepath: 'D:/data/data.xlsx',
        sheet: 'Sheet1',
        header: 'true'
     )
```

### Relational Database (e.g., Mysql) Query Example

```sql
SELECT
        field(id) -> b:Integer,
        field(c) -> b:Integer
FROM MYSQL(
        url: 'localhost',
        port: 3306,
        database: 'user_db',
  sql: 'select * from a',
        username: {db_user},
    password: {db_password}
)
```

### Data Types

Supported data types include:：

- Integer - Integer number
- Long - Long integer
- Float - Floating-point number
- Double - Double-precision floating-point number
- String - Text data
- Boolean - Boolean value
- Date - Date and time value
- Object - Complex object (such as JSON)

### Field Processors

#### Field Processor

Extract data from flat structures (database columns, CSV fields) with the syntax:

```sql
field(column name) -> target field:data type
```

### Field Processors

#### Field Processor

Extract data from flat structures (database columns, CSV fields) with the syntax:

```sql
field(column name) -> target field:data type
```

### Supported Connectors

#### Database Connectors

| Connector Type | Processor Class | Required Parameters | Optional Parameters
|
|------------|---------------------------|-------------------------|-----------------------------------|
| MySQL | MySqlConnectorHandler | url, username, password, sql | driver, database, port |
| PostgreSQL | PostgreSQLConnectorHandler | url, username, password, sql | driver, database, port |
| Oracle | OracleConnectorHandler | url, username, password, sql | driver, serviceName, port |
| ClickHouse | ClickHouseConnectorHandler | url, username, password, sql | driver, database, port |
| H2 | H2ConnectorHandler | url, username, password, sql | driver |
| DB2 | DB2ConnectorHandler | url, username, password, sql | driver, database, port |
| Derby | DerbyConnectorHandler | url, username, password, sql | driver |
| Hive | HiveConnectorHandler | url, username, password, sql | driver, database |
| Impala | ImpalaConnectorHandler | url, username, password, sql | driver, database |
| 金仓 | KingBaseConnectorHandler | url, username, password, sql | driver, database, port |
| SQLite | SQliteConnectorHandler | url, sql | driver |
| Sybase | SysbaseConnectorHandler | url, username, password, sql | driver, database, port |
| 达梦 | DaMengConnectorHandler | url, username, password, sql | driver, database, port |
| Informix | InfomixConnectorHandler | url, username, password, sql | driver, database, port |

#### File Connectors

File Connectors

| Connector Type	 | Processor Class	      | Required Parameters	 | Optional Parameters     |
|-----------------|-----------------------|----------------------|-------------------------|
| CSV             | CsvConnectorHandler   | filepath             | split, header, encoding |
| Excel           | ExcelConnectorHandler | filepath             | sheet, header, startRow |
| JSON            | JsonConnectorHandler  | filepath             | searchPath, encoding    |

#### REST Connector

| Connector Type	 | Processor Class	     | Required Parameters	 | Optional Parameters |
|-----------------|----------------------|----------------------|---------------------|
| CURL            | CurlConnectorHandler | curl                 | searchPath          |