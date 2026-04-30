package com.github.paohaijiao.xml.service;

import com.github.paohaijiao.dataset.JQuickConnectorDataSet;
import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.xml.param.Param;

import java.util.List;

public interface JQuickConnectorService {

    JQuickConnectorDataSet getConfigByYaml(@Param("name")String name) ;
}
