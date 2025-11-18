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

import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.factory.ConnectorFactory;
import com.github.paohaijiao.param.JContext;
import org.junit.Test;

/**
 * packageName com.github.paohaijiao
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/26
 */
public class PropertyTest {

    @Test
    public void testJson() throws Exception {
        JContext context = new JContext();
        String query = "SELECT\n" +
                "    path('$.database.username')->username:string,\n" +
                "    path('$.app.version')->name:string,\n" +
                "    path('$.database.url')->email:string,\n" +
                "    path('$.database.username')->age:string,\n" +
                "    path('$.database.password')->department:object \n" +
                "    path('$.server.port')->salary:object \n" +
                "    path('$.logging.level')->hireDate:object \n" +
                "FROM PROPERTIES(\n" +
                "    filepath: 'D:\\my\\jquick-connector\\src\\test\\resources\\file\\config.properties',\n" +
                "    searchPath: '$'\n" +
                ")";
        System.out.println(query);
        ConnectorFactory factory = new ConnectorFactory();
        DataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("name: " + row.get("name") + ", email: " + row.getString("email") +
                    ", age: " + row.getObject("age"));
        });
    }
}
