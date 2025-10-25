package com.github.paohaijiao.enums;

public enum ConnectorCategory {

    FILE("文件型"),

    DATABASE("数据库型"),

    NOSQL("NoSQL"),

    MESSAGE_QUEUE("消息队列"),

    CLOUD_STORAGE("云存储"),

    API("API服务"),

    CUSTOM("自定义");

    private final String description;

    ConnectorCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
