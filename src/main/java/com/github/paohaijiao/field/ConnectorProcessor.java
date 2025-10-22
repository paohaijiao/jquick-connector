package com.github.paohaijiao.field;

public interface ConnectorProcessor {

    Object process(Object value);

    String getName();
}
