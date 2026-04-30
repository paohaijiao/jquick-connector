package com.github.paohaijiao.xml.service;

import com.github.paohaijiao.statement.JQuickRow;
import com.github.paohaijiao.xml.param.Param;

import java.util.List;

public interface JQuickConnectorService {

    List<JQuickRow> getConfigByYaml(@Param("name")String name) ;
}
