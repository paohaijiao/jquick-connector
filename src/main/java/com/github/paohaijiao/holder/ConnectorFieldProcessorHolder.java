package com.github.paohaijiao.holder;

import com.github.paohaijiao.field.ConnectorProcessor;
import lombok.Data;

@Data
public class ConnectorFieldProcessorHolder {

    private ConnectorProcessor processor;

    private String value;
}
