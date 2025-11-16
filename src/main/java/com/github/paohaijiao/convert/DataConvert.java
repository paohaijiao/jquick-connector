/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
package com.github.paohaijiao.convert;

import com.github.paohaijiao.console.JConsole;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * packageName com.github.paohaijiao.convert
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/25
 */
public class DataConvert {

    protected static JConsole console = new JConsole();

    @SuppressWarnings("unchecked")
    public <T> T convertValue(Object value, Class<T> type) {
        if (value == null) {
            return null;
        }
        if (type.isInstance(value)) {
            return (T) value;
        }
        if (type == String.class) {
            return (T) value.toString();
        } else if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(value.toString());
        } else if (type == Long.class || type == long.class) {
            return (T) Long.valueOf(value.toString());
        } else if (type == Double.class || type == double.class) {
            return (T) Double.valueOf(value.toString());
        } else if (type == Boolean.class || type == boolean.class) {
            return (T) Boolean.valueOf(value.toString());
        } else if (type == Date.class) {
            return (T) parseDate(value.toString());
        } else if (type == LocalDate.class) {
            return (T) parseLocalDate(value.toString());
        } else if (type == LocalDateTime.class) {
            return (T) parseLocalDateTime(value.toString());
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }

    /**
     * 解析字符串为 java.util.Date
     */
    private Date parseDate(String dateStr) {
        String[] patterns = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMdd", "yyyyMMddHHmmss", "EEE MMM dd HH:mm:ss zzz yyyy"};
        for (String pattern : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setLenient(false);
                return sdf.parse(dateStr);
            } catch (ParseException e) {
                console.error("parser date error", e);
            }
        }
        throw new IllegalArgumentException("Unable to parse date: " + dateStr);
    }

    /**
     * 解析字符串为 LocalDate
     */
    private LocalDate parseLocalDate(String dateStr) {
        String[] patterns = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd", "yyyy.MM.dd"};
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                console.error("parser date error", e);
            }
        }
        throw new IllegalArgumentException("Unable to parse LocalDate: " + dateStr);
    }

    /**
     * 解析字符串为 LocalDateTime
     */
    private LocalDateTime parseLocalDateTime(String dateStr) {
        String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd'T'HH:mm:ss", "yyyyMMddHHmmss", "yyyy.MM.dd HH:mm:ss", "yyyy-MM-dd HH:mm"};
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDateTime.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                console.error("parser date error", e);
            }
        }
        throw new IllegalArgumentException("Unable to parse LocalDateTime: " + dateStr);
    }
}
