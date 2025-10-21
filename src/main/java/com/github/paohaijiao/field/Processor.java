package com.github.paohaijiao.field;

public interface Processor {

    Object process(Object value);

    String getName();
}
