package com.github.paohaijiao.handler.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.paohaijiao.builder.JSONPathQueryBuilder;
import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.handler.AbsConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.model.JSONPathResult;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.registry.ConnectorTypeFactory;
import com.github.paohaijiao.util.JSonExtractUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisConnectorHandler extends AbsConnectorBaseHandler {

    protected static final String host = "host";
    protected static final String port = "port";
    protected static final String password = "password";
    protected static final String database = "database";
    protected static final String redisKey = "redisKey";
    protected static final String jsonPath= "jsonPath";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String redisValueToJsonString(Jedis redis, String key) {
        try {
            if (!redis.exists(key)) {
                ObjectNode errorNode = objectMapper.createObjectNode();
                errorNode.put("error", "键不存在");
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorNode);
            }
            String type = redis.type(key);
            switch (type) {
                case "string":
                    return stringToJson(redis, key);
                case "hash":
                    return hashToJson(redis, key);
                case "list":
                    return listToJson(redis, key);
                case "set":
                    return setToJson(redis, key);
                case "zset":
                    return zsetToJson(redis, key);
                default:
                    ObjectNode errorNode = objectMapper.createObjectNode();
                    errorNode.put("error", "不支持的类型: " + type);
                    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorNode);
            }
        } catch (JsonProcessingException e) {
            return "{\"error\": \"JSON处理失败: " + e.getMessage() + "\"}";
        }
    }

    private static String stringToJson(Jedis redis, String key) throws JsonProcessingException {
        String value = redis.get(key);
        try {
            Object jsonObj = objectMapper.readValue(value, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
        } catch (Exception e) {
            ObjectNode node = objectMapper.createObjectNode();
            node.put("value", value);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        }
    }

    private static String hashToJson(Jedis redis, String key) throws JsonProcessingException {
        Map<String, String> hashData = redis.hgetAll(key);
        ObjectNode resultNode = objectMapper.createObjectNode();
        for (Map.Entry<String, String> entry : hashData.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();
            if (isInteger(value)) {
                resultNode.put(field, Long.parseLong(value));
            } else if (isDouble(value)) {
                resultNode.put(field, Double.parseDouble(value));
            } else if (isBoolean(value)) {
                resultNode.put(field, Boolean.parseBoolean(value));
            } else {
                resultNode.put(field, value);
            }
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultNode);
    }

    private static String listToJson(Jedis redis, String key) throws JsonProcessingException {
        List<String> list = redis.lrange(key, 0, -1);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (String item : list) {
            addValueToArray(arrayNode, item);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
    }

    private static String setToJson(Jedis redis, String key) throws JsonProcessingException {
        Set<String> set = redis.smembers(key);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (String item : set) {
            addValueToArray(arrayNode, item);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
    }

    private static String zsetToJson(Jedis redis, String key) throws JsonProcessingException {
        List<String> zsetMembers = redis.zrange(key, 0, -1);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (String member : zsetMembers) {
            addValueToArray(arrayNode, member);
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
    }

    private static void addValueToArray(ArrayNode arrayNode, String value) {
        if (isInteger(value)) {
            arrayNode.add(Long.parseLong(value));
        } else if (isDouble(value)) {
            arrayNode.add(Double.parseDouble(value));
        } else if (isBoolean(value)) {
            arrayNode.add(Boolean.parseBoolean(value));
        } else {
            arrayNode.add(value);
        }
    }

    private static boolean isInteger(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }

    @Override
    public List<Row> buildRow(ConnectorParsedQuery query) {
        ConnectorConfiguration connectorConfiguration = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(connectorConfiguration::setProperty);
        String hostStr = connectorConfiguration.getProperty(host, String.class);
        Integer portValue = connectorConfiguration.getProperty(port, Integer.class);
        String passwordStr = connectorConfiguration.getProperty(password, String.class);
        String redisKeyStr = connectorConfiguration.getProperty(redisKey, String.class);
        Integer databaseValue = connectorConfiguration.getProperty(database, Integer.class);
        String jsonPathValue = connectorConfiguration.getProperty(jsonPath, String.class);
        if(StringUtils.isEmpty(jsonPathValue)){
            jsonPathValue="$";
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);
        config.setMaxIdle(32);
        config.setMinIdle(8);
        config.setMaxWait(Duration.ofSeconds(3));
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);
        int timeout = 20000;
        JedisPool jedisPool = new JedisPool(config, hostStr, portValue, timeout, passwordStr, databaseValue);
        Jedis redis = jedisPool.getResource();
        String value = redisValueToJsonString(redis, redisKeyStr);
        JSONPathResult result = JSONPathQueryBuilder.from(value).path(jsonPathValue).execute();
        if (result.isList()) {
            List<Row> rows = JSonExtractUtil.buildRowsFromJSon(value,jsonPathValue );
            return rows;
        } else {
            Object objct = result.getRawData();
            JAssert.isTrue(objct instanceof JSONObject, "不是Json 数据无法操作");
            Row row = objectMapper.convertValue(objct, Row.class);
            List<Row> rows = new ArrayList<>();
            rows.add(row);
            return rows;
        }


    }

    @Override
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory = ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = ConnectorTypeFactory.buildType(ConnectorTypeEnums.REDIS.getCode(), ConnectorTypeEnums.REDIS.getName(), ConnectorCategory.NOSQL);
        ConnectorType connectorType = connectorTypeBuilder
                .withAliases(ConnectorTypeEnums.REDIS.getCode(), ConnectorTypeEnums.REDIS.getMime())
                .withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.NOSQL.getDescription(), ConnectorCategory.NOSQL.getDescription())).build();
        return connectorType;
    }
}
