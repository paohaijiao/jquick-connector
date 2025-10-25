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
package com.github.paohaijiao.registry;

import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.field.ConnectorProcessor;
import com.github.paohaijiao.field.ConnectorFieldProcessor;
import com.github.paohaijiao.field.ConnectorJsonPathProcessor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Martin
 * @version 2.0.0
 * @since 2025/10/21
 */
public class ConnectoryProcessorRegistry {

    private static final Map<String, ConnectorProcessor> processors = new ConcurrentHashMap<>();

    private static final List<ConnectorProcessor> prioritizedProcessors = new ArrayList<>();

    private static final Map<String, String> aliasToNameMap = new ConcurrentHashMap<>();

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();


    static {
        registerProcessor("field", new ConnectorFieldProcessor(), 1);
        registerProcessor("jsonPath", new ConnectorJsonPathProcessor(), 2);
//        registerAlias("field", "f");
//        registerAlias("jsonPath", "json");
    }

    /**
     * 注册处理器（带优先级）
     *
     * @param name     处理器名称
     * @param processor 处理器实例
     * @param priority  优先级，数值越小优先级越高
     */
    public static void registerProcessor(String name, ConnectorProcessor processor, int priority) {
        lock.writeLock().lock();
        try {
            String normalizedName = name.toLowerCase();
            if (processors.containsKey(normalizedName)) {
                JAssert.throwNewException("Processor already registered: " + name);
            }
            processors.put(normalizedName, processor);
            prioritizedProcessors.add(processor);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 注册处理器（默认优先级）
     */
    public static void registerProcessor(String name, ConnectorProcessor processor) {
        registerProcessor(name, processor, Integer.MAX_VALUE);
    }

    /**
     * 注册别名
     */
    public static void registerAlias(String name, String alias) {
        aliasToNameMap.put(alias.toLowerCase(), name.toLowerCase());
    }

    /**
     * 根据名称或别名获取处理器
     */
    public static ConnectorProcessor getProcessor(String name) {
        lock.readLock().lock();
        try {
            String normalizedName = resolveName(name);
            ConnectorProcessor processor = processors.get(normalizedName);
            if (processor != null) {
                return processor;
            }
            for (ConnectorProcessor p : processors.values()) {
                if (p.getType().equalsIgnoreCase(name)) {
                    return p;
                }
            }
            JAssert.throwNewException("No such processor: " + name);
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 获取所有已注册的处理器名称
     */
    public static Set<String> getRegisteredProcessorNames() {
        return Collections.unmodifiableSet(processors.keySet());
    }

    /**
     * 获取按优先级排序的处理器列表
     */
    public static List<ConnectorProcessor> getPrioritizedProcessors() {
        return Collections.unmodifiableList(prioritizedProcessors);
    }

    /**
     * 移除处理器
     */
    public static void unregisterProcessor(String name) {
        lock.writeLock().lock();
        try {
            String normalizedName = resolveName(name);
            ConnectorProcessor removed = processors.remove(normalizedName);
            if (removed != null) {
                prioritizedProcessors.remove(removed);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void clear() {
        lock.writeLock().lock();
        try {
            processors.clear();
            prioritizedProcessors.clear();
            aliasToNameMap.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static String resolveName(String name) {
        String normalized = name.toLowerCase();
        return aliasToNameMap.getOrDefault(normalized, normalized);
    }


    public static boolean isProcessorRegistered(String name) {
        lock.readLock().lock();
        try {
            String normalizedName = resolveName(name);
            return processors.containsKey(normalizedName);
        } finally {
            lock.readLock().unlock();
        }
    }


    public static Map<String, Object> getRegistryStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProcessors", processors.size());
        stats.put("totalAliases", aliasToNameMap.size());
        stats.put("processorNames", new ArrayList<>(processors.keySet()));
        stats.put("aliasMappings", new HashMap<>(aliasToNameMap));
        return stats;
    }
}