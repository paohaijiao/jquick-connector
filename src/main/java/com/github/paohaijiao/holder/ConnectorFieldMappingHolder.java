package com.github.paohaijiao.holder;

import com.github.paohaijiao.field.ConnectorProcessor;
import lombok.Data;

@Data
public class ConnectorFieldMappingHolder {

    private ConnectorProcessor processor;

    private String sourceField;

    private Class<?> dataType;

    private String targetField;
}
