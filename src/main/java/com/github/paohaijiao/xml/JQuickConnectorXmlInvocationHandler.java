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
package com.github.paohaijiao.xml;

import com.github.paohaijiao.config.JQuickCurlConfig;
import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.dataset.JQuickConnectorDataSet;
import com.github.paohaijiao.enums.JLogLevel;
import com.github.paohaijiao.exception.JAntlrExecutionException;
import com.github.paohaijiao.executor.JQuickCurlExecutor;
import com.github.paohaijiao.factory.JQuickConnectorFactory;
import com.github.paohaijiao.field.JQuickConnectorProcessor;
import com.github.paohaijiao.handler.JQuickConnectorHandler;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;

import java.lang.reflect.Method;

/**
 * packageName com.github.paohaijiao.xml
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/4/11
 */
public class JQuickConnectorXmlInvocationHandler extends JQuickXmlInvocationHandler {

    private JConsole console=new JConsole();

    private JContext context=new JContext();

    private String type;

    private JQuickConnectorHandler connector=null;

    private String name;

    private JQuickConnectorProcessor processor=null;
    public JQuickConnectorXmlInvocationHandler() {
        this(null, null, null, null, null);
    }
    /**
     * 构造方法 - type和connector必须成对出现
     *
     * @param jcontext   J上下文
     * @param type       类型（不能为null且不能为空字符串）
     * @param connector  连接器处理器（不能为null）
     * @throws IllegalArgumentException 当type为null或空字符串，或connector为null时抛出
     */
    public JQuickConnectorXmlInvocationHandler(JContext jcontext, String type, JQuickConnectorHandler connector) {
        this(jcontext, type, connector, null, null);
    }
    /**
     * 构造方法 - name和processor必须成对出现
     *
     * @param jcontext   J上下文
     * @param name       名称（不能为null且不能为空字符串）
     * @param processor  处理器（不能为null）
     * @throws IllegalArgumentException 当name为null或空字符串，或processor为null时抛出
     */
    public JQuickConnectorXmlInvocationHandler(JContext jcontext, String name, JQuickConnectorProcessor processor) {
        this(jcontext, null, null, name, processor);
    }


    /**
     * 完整构造方法 - 支持所有参数，但必须满足type+connector或name+processor至少有一组成对出现
     *
     * @param jcontext   J上下文
     * @param type       类型（与connector成对）
     * @param connector  连接器处理器（与type成对）
     * @param name       名称（与processor成对）
     * @param processor  处理器（与name成对）
     * @throws IllegalArgumentException 当type和connector不同时为null且不同时非null，
     *                                  或name和processor不同时为null且不同时非null时抛出
     */
    public JQuickConnectorXmlInvocationHandler(JContext jcontext, String type, JQuickConnectorHandler connector, String name, JQuickConnectorProcessor processor) {
        boolean hasType = (type != null && !type.trim().isEmpty());
        boolean hasConnector = (connector != null);
        if (hasType != hasConnector) {
            throw new IllegalArgumentException("type和connector必须成对出现：要么同时提供，要么同时为null");
        }
        boolean hasName = (name != null && !name.trim().isEmpty());
        boolean hasProcessor = (processor != null);
        if (hasName != hasProcessor) {
            throw new IllegalArgumentException("name和processor必须成对出现：要么同时提供，要么同时为null");
        }
        if (null != jcontext && !jcontext.isEmpty()) {
            context.putAll(jcontext);
        }
        this.type = type;
        this.connector = connector;
        this.name = name;
        this.processor = processor;
    }

    @Override
    protected Object loadResult(String lexer, JContext context, Method method, Object[] args) {
        JQuickCurlConfig config=JQuickCurlConfig.getInstance();
        JQuickCurlExecutor executor = new JQuickCurlExecutor(context,config);
        executor.addErrorListener(error -> {String message=String.format("Failed: Line %d:%d - %s%n",  error.getLine(), error.getCharPosition(), error.getMessage());console.log(JLogLevel.ERROR,message);});
        try {
            String curlString=replaceVariables(lexer,context);
            console.log(JLogLevel.INFO,"Merged curl command:"+ curlString);
            JQuickConnectorFactory factory = new JQuickConnectorFactory(context,type,connector,name,processor);
            JQuickConnectorDataSet dataSet = factory.executeQuery(lexer);
            return dataSet;
        } catch (JAntlrExecutionException e) {
            console.error("Curl execution error", e);
            e.getErrors().forEach(err ->{
                console.info(" - " + err.getMessage());
            });
        }
        return null;
    }
}
