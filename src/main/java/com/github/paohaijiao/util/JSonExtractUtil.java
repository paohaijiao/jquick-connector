package com.github.paohaijiao.util;

import com.github.paohaijiao.builder.JSONPathQueryBuilder;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.executor.JSONExecutor;
import com.github.paohaijiao.model.JSONObject;
import com.github.paohaijiao.model.JSONPathResult;
import com.github.paohaijiao.statement.JQuickRow;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JSonExtractUtil {

    public static List<JQuickRow> buildRowsFromJSon(String content, String searchPath) {
        JSONExecutor executor = new JSONExecutor();
        Object json = executor.execute(content.toString());
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            JSONPathResult result = JSONPathQueryBuilder.from(jsonObject).path(searchPath).execute();
            if(result.isList()){
                return convert(result.getAsList());
            }else{
                Object object= result.getRawData();
                JSONObject jsonData= (JSONObject) object;
                List<JQuickRow> list= new ArrayList<>();
                JQuickRow row = new JQuickRow(jsonData);
                list.add(row);
                return list;
            }

        } else {
            JAssert.throwNewException("not support this data type[" + content.toString() + "]");
        }
        return new ArrayList<>();
    }

    public static List<JQuickRow> convert(List<Object> objectList) {
        if (objectList == null || objectList.isEmpty()) {
            return new ArrayList<>();
        }
        return objectList.stream().map(JSonExtractUtil::convertObjectToRow).filter(obj -> true).collect(Collectors.toList());
    }

    /**
     * 转换单个对象到 Row
     */
    public static JQuickRow convertObjectToRow(Object obj) {
        if (obj == null) {
            return new JQuickRow();
        }
        JQuickRow row = new JQuickRow();
        try {
            if (obj instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) obj;
                row.putAll(map);
            } else if (obj instanceof JQuickRow) {
                return (JQuickRow) obj;
            } else {
                Map<String, String> properties = BeanUtils.describe(obj);
                properties.forEach((key, value) -> {
                    if (!"class".equals(key) && value != null) {
                        row.put(key, value);
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("对象转换失败: " + e.getMessage(), e);
        }
        return row;
    }

}
