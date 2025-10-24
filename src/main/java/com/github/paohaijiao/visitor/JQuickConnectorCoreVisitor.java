package com.github.paohaijiao.visitor;

import com.github.paohaijiao.param.JContext;
import com.github.paohaijiao.parser.JQuickConnectorBaseVisitor;
import lombok.Data;

@Data
public class JQuickConnectorCoreVisitor extends JQuickConnectorBaseVisitor {

    protected JContext context=new JContext();
}
