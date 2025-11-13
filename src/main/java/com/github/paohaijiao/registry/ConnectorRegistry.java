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

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.handler.ConnectorHandler;
import com.github.paohaijiao.meta.ConnectorMetadata;
import com.github.paohaijiao.spi.ServiceLoader;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Martin
 * @version 2.0.0
 * @since 2025/10/25
 */
public class ConnectorRegistry {

    public static JConsole console=new JConsole();

    private static final Map<String, ConnectorMetadata> CONNECTOR_METADATA_MAP = new ConcurrentHashMap<>();

    private static final Map<Class<?>, List<ConnectorMetadata>> TYPE_HIERARCHY_MAP = new ConcurrentHashMap<>();

    private static final List<RegistryListener> LISTENERS = new CopyOnWriteArrayList<>();

    private static volatile RegistryState state = RegistryState.INITIALIZING;

    static {
        initializeRegistry();
    }

    /**
     * 初始化注册表
     */
    private static void initializeRegistry() {
        changeState(RegistryState.INITIALIZING, RegistryState.READY);
        List<ConnectorHandler> loadedHandlers = ServiceLoader.loadServices(ConnectorHandler.class);
        for (ConnectorHandler handler : loadedHandlers) {
            registerConnector(handler.getConnectorType().getCode(), handler);
        }
        changeState(RegistryState.READY, RegistryState.READY);
    }

    /**
     * 注册连接器
     */
    public static synchronized void registerConnector(String type, ConnectorHandler connector) {
        checkRegistryState();
        Objects.requireNonNull(type, "Connector type cannot be null");
        Objects.requireNonNull(connector, "Connector handler cannot be null");
        String normalizedType = normalizeType(type);
        if (CONNECTOR_METADATA_MAP.containsKey(normalizedType)) {
            throw new IllegalStateException("Connector with type '" + normalizedType + "' is already registered");
        }
        ConnectorMetadata metadata = new ConnectorMetadata(normalizedType, connector);
        CONNECTOR_METADATA_MAP.put(normalizedType, metadata);
        updateTypeHierarchy(connector.getClass(), metadata);
        notifyConnectorRegistered(normalizedType, connector);
    }

    public static ConnectorHandler getConnector(String type) {
        checkRegistryState();
        String normalizedType = normalizeType(type);
        ConnectorMetadata metadata = CONNECTOR_METADATA_MAP.get(normalizedType);
        if (metadata != null && metadata.isActive()) {
            metadata.incrementUsage();
            return metadata.getHandler();
        }
        return null;
    }

    /**
     * 获取连接器元数据
     */
    public static ConnectorMetadata getConnectorMetadata(String type) {
        checkRegistryState();
        return CONNECTOR_METADATA_MAP.get(normalizeType(type));
    }

    /**
     * 注销连接器
     */
    public static synchronized boolean unregisterConnector(String type) {
        checkRegistryState();
        String normalizedType = normalizeType(type);
        ConnectorMetadata metadata = CONNECTOR_METADATA_MAP.remove(normalizedType);
        if (metadata != null) {
            metadata.setActive(false);
            removeFromTypeHierarchy(metadata);
            notifyConnectorUnregistered(normalizedType, metadata.getHandler());
            return true;
        }
        return false;
    }

    /**
     * 检查连接器是否存在
     */
    public static boolean containsConnector(String type) {
        checkRegistryState();
        ConnectorMetadata metadata = CONNECTOR_METADATA_MAP.get(normalizeType(type));
        return metadata != null && metadata.isActive();
    }

    /**
     * 获取所有已注册的连接器类型
     */
    public static Set<String> getRegisteredTypes() {
        checkRegistryState();
        return Collections.unmodifiableSet(CONNECTOR_METADATA_MAP.values().stream()
                .filter(ConnectorMetadata::isActive)
                .map(ConnectorMetadata::getType)
                .collect(Collectors.toSet()));
    }

    /**
     * 获取所有活跃的连接器
     */
    public static Collection<ConnectorHandler> getActiveConnectors() {
        checkRegistryState();
        return Collections.unmodifiableList(
                CONNECTOR_METADATA_MAP.values().stream()
                        .filter(ConnectorMetadata::isActive)
                        .map(ConnectorMetadata::getHandler)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 根据条件查找连接器
     */
    public static List<ConnectorHandler> findConnectors(Predicate<ConnectorMetadata> predicate) {
        checkRegistryState();
        return Collections.unmodifiableList(CONNECTOR_METADATA_MAP.values().stream()
                .filter(ConnectorMetadata::isActive)
                .map(ConnectorMetadata::getHandler)
                .collect(Collectors.toList()));
    }

    /**
     * 按使用次数排序获取连接器
     */
    public static List<ConnectorHandler> getConnectorsByUsage() {
        checkRegistryState();
        return Collections.unmodifiableList(CONNECTOR_METADATA_MAP.values().stream()
                .filter(ConnectorMetadata::isActive)
                .sorted(Comparator.comparingInt(ConnectorMetadata::getUsageCount).reversed())
                .map(ConnectorMetadata::getHandler)
                .collect(Collectors.toList()));
    }

    /**
     * 根据类型层次结构查找连接器
     */
    public static <T extends ConnectorHandler> List<T> getConnectorsByType(Class<T> handlerType) {
        checkRegistryState();
        Objects.requireNonNull(handlerType, "Handler type cannot be null");
        List<ConnectorMetadata> metadataList = TYPE_HIERARCHY_MAP.getOrDefault(handlerType, Collections.emptyList());
        return Collections.unmodifiableList(metadataList.stream()
                .filter(ConnectorMetadata::isActive)
                .map(ConnectorMetadata::getHandler)
                .map(handlerType::cast)
                .collect(Collectors.toList()));
    }

    /**
     * 添加注册表监听器
     */
    public static void addRegistryListener(RegistryListener listener) {
        LISTENERS.add(Objects.requireNonNull(listener, "Registry listener cannot be null"));
    }

    /**
     * 移除注册表监听器
     */
    public static boolean removeRegistryListener(RegistryListener listener) {
        return LISTENERS.remove(listener);
    }

    /**
     * 获取注册表状态
     */
    public static RegistryState getRegistryState() {
        return state;
    }

    /**
     * 销毁注册表
     */
    public static synchronized void destroy() {
        if (state == RegistryState.DESTROYED) {
            return;
        }

        changeState(state, RegistryState.DESTROYED);
        CONNECTOR_METADATA_MAP.clear();
        TYPE_HIERARCHY_MAP.clear();
        LISTENERS.clear();
    }

    /**
     * 获取注册表统计信息
     */
    public static Map<String, Object> getRegistryStats() {
        checkRegistryState();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalConnectors", CONNECTOR_METADATA_MAP.size());
        stats.put("activeConnectors", getActiveConnectors().size());
        stats.put("registeredTypes", getRegisteredTypes().size());
        stats.put("listenersCount", LISTENERS.size());
        long totalUsage = CONNECTOR_METADATA_MAP.values().stream()
                .mapToInt(ConnectorMetadata::getUsageCount)
                .sum();
        stats.put("totalUsageCount", totalUsage);
        return Collections.unmodifiableMap(stats);
    }

    private static String normalizeType(String type) {
        return type.toUpperCase();
    }

    private static void updateTypeHierarchy(Class<?> handlerClass, ConnectorMetadata metadata) {
        Class<?> currentClass = handlerClass;
        while (currentClass != null && ConnectorHandler.class.isAssignableFrom(currentClass)) {
            TYPE_HIERARCHY_MAP.computeIfAbsent(currentClass, k -> new CopyOnWriteArrayList<>()).add(metadata);
            currentClass = currentClass.getSuperclass();
        }
    }
    private static void removeFromTypeHierarchy(ConnectorMetadata metadata) {
        TYPE_HIERARCHY_MAP.values().forEach(list -> list.remove(metadata));
        TYPE_HIERARCHY_MAP.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }
    private static void notifyConnectorRegistered(String type, ConnectorHandler handler) {
        LISTENERS.forEach(listener -> {
            try {
                listener.onConnectorRegistered(type, handler);
            } catch (Exception e) {
                console.error("notifyConnectorRegistered error",e);
            }
        });
    }

    private static void notifyConnectorUnregistered(String type, ConnectorHandler handler) {
        LISTENERS.forEach(listener -> {
            try {
                listener.onConnectorUnregistered(type, handler);
            } catch (Exception e) {
                console.error("notifyConnectorUnregistered error",e);
            }
        });
    }

    private static void changeState(RegistryState expectedOldState, RegistryState newState) {
        RegistryState oldState = state;
        state = newState;
        if (oldState != newState) {
            LISTENERS.forEach(listener -> {
                try {
                    listener.onRegistryStateChanged(oldState, newState);
                } catch (Exception e) {
                    console.error("Error notifying state change: ",e);
                }
            });
        }
    }

    private static void checkRegistryState() {
        if (state == RegistryState.DESTROYED) {
            throw new IllegalStateException("ConnectorRegistry has been destroyed");
        }
    }

    /**
     * 注册表状态枚举
     */
    public enum RegistryState {
        INITIALIZING, READY, DESTROYED
    }

    /**
     * 注册表监听器接口
     */
    public interface RegistryListener {
        void onConnectorRegistered(String type, ConnectorHandler handler);
        void onConnectorUnregistered(String type, ConnectorHandler handler);
        void onRegistryStateChanged(RegistryState oldState, RegistryState newState);
    }
}