package com.github.paohaijiao.config;

public interface ConnectorConfig {

    String getProperty(String key);

    <T> T getProperty(String key, Class<T> type);

    boolean hasProperty(String key);
}
