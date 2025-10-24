package com.github.paohaijiao.enums;
import lombok.Getter;

import java.util.Date;

@Getter
public enum ConnectorDataType {

    Integer("Integer", Integer.class),
    Long("Long", Long.class),
    Float("Float", Float.class),
    Double("Double", Double.class),
    String("String", String.class),
    Boolean("Boolean", Boolean.class),
    Date("Date", Date.class),
   ;
    private String code;

    private Class<?> clazz;

    ConnectorDataType(String code, Class<?> clazz){
        this.code = code;
        this.clazz = clazz;
    }
    public static ConnectorDataType codeOf(String code){
        for (ConnectorDataType type : ConnectorDataType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new RuntimeException("不支持" + code+"该类型");
    }
}
