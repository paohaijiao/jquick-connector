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
    String curl="`curl -X GET \\ \n"+
            "'http://xxxxxxx/getProjec"+
            "  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36' \\\n" +
            "  --insecure`";


    @Test
    public  void testJson() throws Exception {
        System.out.println(curl);
        JContext context = new JContext();
        String query="SELECT\n" +
                "    field(projectId)->projectId:string,\n" +
                "    field(projectName)->projectName:string,\n" +
                "    field(projectStage)->projectStage:object \n"+
                "FROM CURL(\n" +
                "    curl: "+curl+",\n" +
                "    searchPath: '$.data'\n" +
                ")";
        ConnectorFactory factory = new ConnectorFactory();
        DataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("projectId: " + row.get("projectId") + ", projectName: " + row.getString("projectName"));
        });
    }
}
