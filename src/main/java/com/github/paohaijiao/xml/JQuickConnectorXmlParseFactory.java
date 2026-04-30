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

import com.github.paohaijiao.field.JQuickConnectorProcessor;
import com.github.paohaijiao.handler.database.JQuickConnectorHandler;
import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.xml.element.JQuickXmlElement;
import com.github.paohaijiao.xml.handler.JQuickParseHandler;
import com.github.paohaijiao.xml.invocation.JQuickXmlInvocationHandler;

/**
 * packageName com.github.paohaijiao.xml
 *
 * @author Martin
 * @version 1.0.0
 * @since 2026/4/11
 */
public class JQuickConnectorXmlParseFactory implements JQuickParseHandler {

    private JContext context=new JContext();

    private String type;

    private JQuickConnectorHandler connector;

    private String name;

    private JQuickConnectorProcessor processor;

    public JQuickConnectorXmlParseFactory() {
    }
    /**
     * 构造方法 - 使用type和connector成对参数
     *
     * @param jcontext   J上下文
     * @param type      类型
     * @param connector 连接器处理器
     */
    public JQuickConnectorXmlParseFactory(JContext jcontext, String type, JQuickConnectorHandler connector) {
         this(jcontext,type,connector,null,null);
    }

    /**
     * 构造方法 - 使用name和processor成对参数
     *
     * @param jcontext   J上下文
     * @param name      名称
     * @param processor 处理器
     */
    public JQuickConnectorXmlParseFactory(JContext jcontext, String name, JQuickConnectorProcessor processor) {
        this(jcontext,null,null,name,processor);
    }

    /**
     * 完整构造方法 - 支持所有参数
     *
     * @param jcontext   J上下文
     * @param type      类型（与connector成对）
     * @param connector 连接器处理器（与type成对）
     * @param name      名称（与processor成对）
     * @param processor 处理器（与name成对）
     */
    public JQuickConnectorXmlParseFactory(JContext jcontext, String type, JQuickConnectorHandler connector, String name, JQuickConnectorProcessor processor) {
        if (null != jcontext && !jcontext.isEmpty()) {
            context.putAll(jcontext);
        }
        this.type = type;
        this.connector = connector;
        this.name = name;
        this.processor = processor;
    }

    @Override
    public JQuickXmlElement createJQuickXmlElement() {
        return new JQuickConnectorXmlElement();
    }

    @Override
    public JQuickXmlInvocationHandler createlInvocationHandler() {
        return new JQuickConnectorXmlInvocationHandler(context, type, connector, name, processor);
    }
}
