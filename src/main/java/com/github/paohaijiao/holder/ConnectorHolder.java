package com.github.paohaijiao.holder;

import lombok.Data;


import java.util.Map;

@Data
public class ConnectorHolder {

    private String type;

    private Map<String, Object> map;
}
