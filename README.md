# JQuick Connector

一个强大的数据连接器框架，支持使用类SQL语法统一查询多种数据源，包括数据库、文件和REST API。

## 特性

- **统一查询接口**: 使用类SQL语法查询多样化数据源
- **多数据源支持**:
    - **数据库**: MySQL、PostgreSQL、Oracle、ClickHouse、H2等
    - **文件**: CSV、Excel、JSON
    - **REST API**: 基于jQuickCURL的HTTP请求
- **灵活字段映射**: 通过字段处理器提取和转换数据
- **类型安全**: 支持数据字段的强类型定义
- **上下文变量**: 支持使用变量的参数化查询

## 快速开始

### 基本查询语法

```sql
SELECT
    field(源字段) -> 目标字段:数据类型,
    path('$.json.路径') -> json字段:string
FROM 连接器类型(
    属性1: '值1',
    属性2: 123,
    属性3: {变量名}
)
```
### MySQL数据库查询示例
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
### CSV 文件查询示例
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
### JSON 文件查询示例
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
### REST API 查询示例
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
### Excel 文件查询示例
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
### Excel 文件查询示例
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
### 数据类型
支持的数据类型包括：
- Integer - 整数
- Long - 长整数
- Float - 浮点数
- Double - 双精度浮点数
- String - 文本数据
- Boolean - 布尔值
- Date - 日期时间值
- Object - 复杂对象（JSON）

### 字段处理器
#### Field 处理器
从扁平结构中提取数据（数据库列、CSV 字段），语法格式：
```sql
field(列名) -> 目标字段:数据类型
```
### 字段处理器
#### Field 处理器
从扁平结构中提取数据（数据库列、CSV 字段），语法格式：
```sql
field(列名) -> 目标字段:数据类型
```
### 支持的连接器
#### 数据库连接器
| 连接器类型 | 处理器类                  | 必需参数                | 可选参数                          |
|------------|---------------------------|-------------------------|-----------------------------------|
| MySQL      | MySqlConnectorHandler     | url, username, password, sql | driver, database, port            |
| PostgreSQL | PostgreSQLConnectorHandler | url, username, password, sql | driver, database, port            |
| Oracle     | OracleConnectorHandler    | url, username, password, sql | driver, serviceName, port         |
| ClickHouse | ClickHouseConnectorHandler | url, username, password, sql | driver, database, port            |
| H2         | H2ConnectorHandler        | url, username, password, sql | driver                            |
| DB2        | DB2ConnectorHandler       | url, username, password, sql | driver, database, port            |
| Derby      | DerbyConnectorHandler     | url, username, password, sql | driver                            |
| Hive       | HiveConnectorHandler      | url, username, password, sql | driver, database                  |
| Impala     | ImpalaConnectorHandler    | url, username, password, sql | driver, database                  |
| 金仓       | KingBaseConnectorHandler  | url, username, password, sql | driver, database, port            |
| SQLite     | SQliteConnectorHandler    | url, sql                | driver                            |
| Sybase     | SysbaseConnectorHandler   | url, username, password, sql | driver, database, port            |
| 达梦       | DaMengConnectorHandler    | url, username, password, sql | driver, database, port            |
| Informix   | InfomixConnectorHandler   | url, username, password, sql | driver, database, port            |

#### 文件连接器
文件连接器

| 连接器类型 | 处理器类                  | 必需参数 | 可选参数                          |
|------------|---------------------------|----------|-----------------------------------|
| CSV        | CsvConnectorHandler       | filepath | split, header, encoding           |
| Excel      | ExcelConnectorHandler     | filepath | sheet, header, startRow           |
| JSON       | JsonConnectorHandler      | filepath | searchPath, encoding              |

#### REST 连接器
| 连接器类型 | 处理器类                  | 必需参数 | 可选参数                         |
|------------|---------------------------|----------|------------------------------|
| CURL       | CurlConnectorHandler      | curl                    | searchPath  |