package com.github.paohaijiao.registry;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.console.JConsoleConfig;
import com.github.paohaijiao.console.JConsoleConfigLoader;
import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JLogLevel;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.provider.JQuickConnectorTypeProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 支持动态注册、分类管理、插件机制、依赖检查等功能
 */
public class JQuickConnectorTypeFactory {

    protected static final JQuickConnectorTypeFactory INSTANCE = new JQuickConnectorTypeFactory();
    protected final Map<String, JQuickConnectorType> registry = new ConcurrentHashMap<>();
    protected final Map<JQuickConnectorCategory, List<JQuickConnectorType>> categoryIndex = new ConcurrentHashMap<>();
    protected final Map<String, String> aliasRegistry = new ConcurrentHashMap<>();
    protected final List<JQuickConnectorTypeProvider> providers = new CopyOnWriteArrayList<>();
    protected final Map<String, Set<String>> dependencyGraph = new ConcurrentHashMap<>();
    protected final Map<String, JQuickConnectorTypeMetadata> metadataRegistry = new ConcurrentHashMap<>();
    protected boolean initialized = false;

    private JQuickConnectorTypeFactory() {
    }

    public static JQuickConnectorTypeFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 构建类型构建器
     */
    public static ConnectorTypeBuilder buildType(String code, String name, JQuickConnectorCategory category) {
        return new ConnectorTypeBuilder(code, name, category);
    }

    /**
     * 初始化工厂，加载所有预定义类型和插件
     */
    public synchronized void initialize() {
        if (initialized) {
            return;
        }
        loadPredefinedTypes();
        loadPluginTypes();
        buildDependencyGraph();
        validateAllTypes();
        initialized = true;
    }

    /**
     * 加载预定义类型
     */
    private void loadPredefinedTypes() {
        registerType(buildType("CSV", "CSV", JQuickConnectorCategory.FILE).withAliases("csv", "text/csv").withMetadata(new JQuickConnectorTypeMetadata("1.0", "文本文件", "逗号分隔值文件")).build());
        registerType(buildType("EXCEL", "EXCEL", JQuickConnectorCategory.FILE).withAliases("excel", "xls", "xlsx").withMetadata(new JQuickConnectorTypeMetadata("1.0", "电子表格", "Microsoft Excel文件")).build());
        registerType(buildType("MYSQL", "MySQL", JQuickConnectorCategory.DATABASE).withAliases("mysql", "mariadb").withDependencies("JDBC").withMetadata(new JQuickConnectorTypeMetadata("8.0", "关系数据库", "MySQL数据库")).build());
        registerType(buildType("CLICKHOUSE", "ClickHouse", JQuickConnectorCategory.DATABASE).withAliases("clickhouse", "ch").withDependencies("JDBC").withMetadata(new JQuickConnectorTypeMetadata("22.0", "列式数据库", "ClickHouse分析型数据库")).build());
        registerType(buildType("KAFKA", "Kafka", JQuickConnectorCategory.MESSAGE_QUEUE).withAliases("kafka").withDependencies("JavaClient").withMetadata(new JQuickConnectorTypeMetadata("3.0", "消息系统", "Apache Kafka分布式流平台")).build());
        registerType(buildType("JSON", "JSON", JQuickConnectorCategory.MESSAGE_QUEUE).withAliases("json").withDependencies("Json").withMetadata(new JQuickConnectorTypeMetadata("3.0", "文件", "Json")).build());
    }

    /**
     * 通过SPI加载插件类型
     */
    private void loadPluginTypes() {
        JConsole console = JConsole.initConsoleEnvironment();
        ServiceLoader<JQuickConnectorTypeProvider> loader = ServiceLoader.load(JQuickConnectorTypeProvider.class);
        for (JQuickConnectorTypeProvider provider : loader) {
            try {
                providers.add(provider);
                JQuickConnectorType pluginType = provider.getConnectorType();
                registerType(pluginType);
                console.log(JLogLevel.INFO, "Loaded connector types from provider: " + provider.getClass().getName());
            } catch (Exception e) {
                console.log(JLogLevel.ERROR, "Failed to load connector types from provider: " + provider.getClass().getName());
                e.printStackTrace();
            }
        }
    }

    /**
     * 构建类型依赖关系图
     */
    private void buildDependencyGraph() {
        for (JQuickConnectorType type : registry.values()) {
            dependencyGraph.put(type.getCode(), new HashSet<>(type.getDependencies()));
        }
    }

    /**
     * 验证所有注册的类型
     */
    private void validateAllTypes() {
        for (JQuickConnectorType type : registry.values()) {
            validateType(type);
        }
    }

    /**
     * 注册类型
     */
    public void registerType(JQuickConnectorType type) {
        JConsole console = JConsole.initConsoleEnvironment();
        Objects.requireNonNull(type, "ConnectorType cannot be null");
        Objects.requireNonNull(type.getCode(), "ConnectorType code cannot be null");
        validateType(type);
        if (registry.containsKey(type.getCode())) {
            JAssert.throwNewException("ConnectorType with code '" + type.getCode() + "' already exists");
        }
        registry.put(type.getCode(), type);
        for (String alias : type.getAliases()) {
            if (aliasRegistry.containsKey(alias)) {
                console.log(JLogLevel.ERROR, "Warning: Alias '" + alias + "' already exists for type '" + aliasRegistry.get(alias) + "', will be overwritten by '" + type.getCode() + "'");
            }
            aliasRegistry.put(alias.toLowerCase(), type.getCode());
        }
        categoryIndex.computeIfAbsent(type.getCategory(), k -> new ArrayList<>()).add(type); // 更新分类索引
        if (type.getMetadata() != null) {
            metadataRegistry.put(type.getCode(), type.getMetadata());
        }
        dependencyGraph.put(type.getCode(), new HashSet<>(type.getDependencies()));
        console.log(JLogLevel.INFO, "Registered connector type: " + type.getCode() + " - " + type.getName());
    }

    /**
     * 验证类型
     */
    private void validateType(JQuickConnectorType type) {
        if (type.getCode().trim().isEmpty()) {
            JAssert.throwNewException("ConnectorType code cannot be empty");
        }
        if (!type.getCode().matches("^[A-Z][A-Z0-9_]*$")) {
            JAssert.throwNewException("ConnectorType code must match pattern: ^[A-Z][A-Z0-9_]*$");
        }
        if (type.getCategory() == null) {
            JAssert.throwNewException("ConnectorType category cannot be null");
        }
    }

    /**
     * 根据code获取类型
     */
    public JQuickConnectorType codeOf(String code) {
        if (!initialized) {
            initialize();
        }
        JQuickConnectorType type = registry.get(code);
        if (type != null) {
            return type;
        }
        String actualCode = aliasRegistry.get(code.toLowerCase());
        if (actualCode != null) {
            return registry.get(actualCode);
        }
        JAssert.throwNewException("不支持 " + code + " 该类型");
        return null;
    }

    public List<JQuickConnectorType> getAllTypes() {
        if (!initialized) {
            initialize();
        }
        return new ArrayList<>(registry.values());
    }

    /**
     * 根据分类获取类型
     */
    public List<JQuickConnectorType> getTypesByCategory(JQuickConnectorCategory category) {
        if (!initialized) {
            initialize();
        }
        return Collections.unmodifiableList(categoryIndex.getOrDefault(category, new ArrayList<>()));
    }

    public Set<JQuickConnectorCategory> getSupportedCategories() {
        if (!initialized) {
            initialize();
        }
        return Collections.unmodifiableSet(categoryIndex.keySet());
    }


    public boolean containsType(String code) {
        if (!initialized) {
            initialize();
        }
        return registry.containsKey(code) || aliasRegistry.containsKey(code.toLowerCase());
    }

    public JQuickConnectorTypeMetadata getMetadata(String code) {
        JQuickConnectorType type = codeOf(code);
        return metadataRegistry.get(type.getCode());
    }

    public Set<String> getDependencies(String code) {
        JQuickConnectorType type = codeOf(code);
        return Collections.unmodifiableSet(dependencyGraph.getOrDefault(type.getCode(), new HashSet<>()));
    }

    public synchronized void registerDynamicType(JQuickConnectorType type) {
        registerType(type);
    }

    public synchronized void unregisterType(String code) {
        JConsole console = JConsole.initConsoleEnvironment();
        JQuickConnectorType type = registry.remove(code);
        if (type != null) {
            for (String alias : type.getAliases()) {
                aliasRegistry.remove(alias.toLowerCase());
            }
            List<JQuickConnectorType> categoryList = categoryIndex.get(type.getCategory());
            if (categoryList != null) {
                categoryList.remove(type);
            }
            metadataRegistry.remove(code);
            dependencyGraph.remove(code);
            console.log(JLogLevel.INFO, "Unregistered connector type: " + type.getCode());
        }
    }

    /**
     * 重新加载所有类型
     */
    public synchronized void reload() {
        registry.clear();
        categoryIndex.clear();
        aliasRegistry.clear();
        dependencyGraph.clear();
        metadataRegistry.clear();
        initialized = false;
        initialize();
    }

    /**
     * 连接器类型构建器
     */
    public static class ConnectorTypeBuilder {

        private final String code;

        private final String name;

        private final JQuickConnectorCategory category;

        private final List<String> aliases = new ArrayList<>();

        private final List<String> dependencies = new ArrayList<>();

        private JQuickConnectorTypeMetadata metadata;

        public ConnectorTypeBuilder(String code, String name, JQuickConnectorCategory category) {
            this.code = code;
            this.name = name;
            this.category = category;
        }

        public ConnectorTypeBuilder withAliases(String... aliases) {
            this.aliases.addAll(Arrays.asList(aliases));
            return this;
        }

        public ConnectorTypeBuilder withDependencies(String... dependencies) {
            this.dependencies.addAll(Arrays.asList(dependencies));
            return this;
        }

        public ConnectorTypeBuilder withMetadata(JQuickConnectorTypeMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public JQuickConnectorType build() {
            return new JQuickConnectorType(code, name, category, aliases, dependencies, metadata);
        }
    }
}