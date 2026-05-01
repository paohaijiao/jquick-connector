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

import com.github.paohaijiao.factory.JQuickConnectorFactory;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.statement.JQuickDataSet;
import org.junit.Test;

/**
 * packageName com.github.paohaijiao
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/03/13
 */
public class RestTest {

    @Test
    public void testGetRequest() throws Exception {
        JContext context = new JContext();
        String query = "SELECT " +
                "    field(id)->id:string, " +
                "    field(title)->title:string, " +
                "    field(body)->body:string, " +
                "    field(userId)->userId:string " +
                "FROM REST( " +
                "    url: 'https://jsonplaceholder.typicode.com/posts/1', " +
                "    method: 'GET', " +
                "    searchPath: '$' " +
                ")";
        JQuickConnectorFactory factory = new JQuickConnectorFactory();
        JQuickDataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("id: " + row.get("id") + ", title: " + row.getString("title"));
        });
    }

    @Test
    public void testGetRequestWithParams() throws Exception {
        JContext context = new JContext();
        String query = "SELECT " +
                "    field(id)->id:string, " +
                "    field(title)->title:string, " +
                "    field(body)->body:string, " +
                "    field(userId)->userId:string " +
                "FROM REST( " +
                "    url: 'https://jsonplaceholder.typicode.com/posts', " +
                "    method: 'GET', " +
                "    params: 'userId=1', " +
                "    searchPath: '$' " +
                ")";
        JQuickConnectorFactory factory = new JQuickConnectorFactory();
        JQuickDataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("id: " + row.get("id") + ", title: " + row.getString("title"));
        });
    }

    @Test
    public void testPostRequest() throws Exception {
        JContext context = new JContext();
        String query = "SELECT " +
                "    field(id)->id:string, " +
                "    field.title->title:string, " +
                "    field.body->body:string, " +
                "    field.userId->userId:string " +
                "FROM REST( " +
                "    url: 'https://jsonplaceholder.typicode.com/posts', " +
                "    method: 'POST', " +
                "    headers: 'Content-Type=application/json', " +
                "    body: '{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}', " +
                "    searchPath: '$' " +
                ")";
        JQuickConnectorFactory factory = new JQuickConnectorFactory();
        JQuickDataSet dataSet = factory.executeQuery(query);
        dataSet.getRows().forEach(row -> {
            System.out.println("id: " + row.get("id") + ", title: " + row.getString("title"));
        });
    }
}
