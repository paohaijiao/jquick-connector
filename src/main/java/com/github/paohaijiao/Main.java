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
package com.github.paohaijiao;

import com.github.paohaijiao.data.DataSet;
import com.github.paohaijiao.factory.ConnectorFactory;

/**
 * packageName com.github.paohaijiao
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class Main {
    public static void main(String[] args) {
        ConnectorFactory factory = new ConnectorFactory();
        String query = "SELECT " +
                "field(id)->b:Integer, " +
                "field(name)->user_name:String " +
                "FROM MYSQL( " +
                "url: 'localhost', " +
                "port: 3306, " +
                "database: 'user_db', " +
                "table: 'users', " +
                "username: 'root', " +
                "password: '123456' " +
                ")";

        DataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("ID: " + row.getInt("b") + ", Name: " + row.getString("user_name"));
        });
    }
}
