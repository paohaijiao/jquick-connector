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
public class CurlTest {


    @Test
    public  void testJson() throws Exception {
        JContext context = new JContext();
        String query="SELECT\n" +
                "    field(id)->id:string,\n" +
                "    field(name)->name:string,\n" +
                "    field(manager)->manager:object \n"+
                "FROM JSON(\n" +
                "    filepath: 'D:\\my\\jquick-connector\\src\\test\\resources\\file\\data.json',\n" +
                "    searchPath: '$.departments'\n" +
                ")";
        ConnectorFactory factory = new ConnectorFactory();
        DataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("id: " + row.get("id") + ", name: " + row.getString("name")+ ", manager: " + row.getObject("manager"));
        });
    }
}
