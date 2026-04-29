package com.github.paohaijiao.handler.file;

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.enums.JQuickConnectorCategory;
import com.github.paohaijiao.enums.JQuickConnectorTypeEnums;
import com.github.paohaijiao.handler.JQuickConnectorAbsFileBaseHandler;
import com.github.paohaijiao.meta.JQuickConnectorType;
import com.github.paohaijiao.meta.JQuickConnectorTypeMetadata;
import com.github.paohaijiao.provider.JQuickConnectorTypeProvider;
import com.github.paohaijiao.query.JQuickConnectorParsedQuery;
import com.github.paohaijiao.registry.JQuickConnectorTypeFactory;
import com.github.paohaijiao.statement.JQuickRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JQuickConnectorCsvHandler extends JQuickConnectorAbsFileBaseHandler implements JQuickConnectorTypeProvider {

    protected static final String split = "split";

    private static List<JQuickRow> convert(BufferedReader reader, String split, boolean hasHeader) throws IOException {
        List<JQuickRow> result = new ArrayList<>();
        String[] headers = null;
        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] values = line.split(split);
            if (hasHeader && headers == null) {
                headers = values;
                continue;
            }
            if (headers == null) {
                headers = generateDefaultHeaders(values.length);
            }
            JQuickRow row = new JQuickRow();
            for (int i = 0; i < Math.min(headers.length, values.length); i++) {
                String value = values[i].trim();
                row.put(headers[i], value);
            }
            result.add(row);
        }
        return result;
    }

    private static String[] generateDefaultHeaders(int count) {
        String[] headers = new String[count];
        for (int i = 0; i < count; i++) {
            headers[i] = "column" + (i + 1);
        }
        return headers;
    }

    @Override
    public List<JQuickRow> doParse(Path path, JQuickConnectorParsedQuery query) {
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        Boolean h = config.getProperty(header, Boolean.class);
        String splitStr = config.getProperty(split, String.class);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return convert(reader, splitStr, h);
        } catch (IOException e) {
            console.error("csv parse error", e);
        }
        return new ArrayList<>();
    }

    @Override
    public JQuickConnectorType getConnectorType() {
        JQuickConnectorTypeFactory connectorTypeFactory = JQuickConnectorTypeFactory.getInstance();
        JQuickConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(JQuickConnectorTypeEnums.CSV.getCode(), JQuickConnectorTypeEnums.CSV.getName(), JQuickConnectorCategory.FILE);
        JQuickConnectorType connectorType = connectorTypeBuilder.withAliases(JQuickConnectorTypeEnums.CSV.getCode(), JQuickConnectorTypeEnums.CSV.getMime()).withMetadata(new JQuickConnectorTypeMetadata("1.0", JQuickConnectorCategory.FILE.getDescription(), JQuickConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
