package com.github.paohaijiao.xml.service;

import com.github.paohaijiao.statement.JQuickDataSet;
import com.github.paohaijiao.xml.param.Param;

import java.util.List;

public interface JQuickConnectorService {

    JQuickDataSet getConfigByYaml(@Param("name")String name) ;
}
