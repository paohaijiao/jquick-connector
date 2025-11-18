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
public class XmlTest {

    @Test
    public void testJson() throws Exception {
        JContext context = new JContext();
        String query = "SELECT\n" +
                "    path('$.attributes.id[0]')->id:Long,\n" +
                "    path('$.user.name[0]')->name:string,\n" +
                "    path('$.user.email[0]')->email:string,\n" +
                "    path('$.user.age[0]')->age:string,\n" +
                "    path('$.user.department[0]')->department:object \n" +
                "    path('$.user.salary[0]')->salary:object \n" +
                "    path('$.user.hireDate[0]')->hireDate:object \n" +
                "FROM XML(\n" +
                "    filepath: 'D:\\my\\jquick-connector\\src\\test\\resources\\file\\user.xml',\n" +
                "    searchPath: '$.users'\n" +
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
