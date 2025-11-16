package com.github.paohaijiao.handler.file;

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.enums.ConnectorCategory;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsFileConnectorBaseHandler;
import com.github.paohaijiao.meta.ConnectorType;
import com.github.paohaijiao.meta.ConnectorTypeMetadata;
import com.github.paohaijiao.provider.ConnectorTypeProvider;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import com.github.paohaijiao.registry.ConnectorTypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvConnectorHandler extends AbsFileConnectorBaseHandler implements ConnectorTypeProvider {

    protected static final String split = "split";

    private static List<Row> convert(BufferedReader reader, String split, boolean hasHeader) throws IOException {
        List<Row> result = new ArrayList<>();
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
            Row row = new Row();
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
    public List<Row> doParse(Path path, ConnectorParsedQuery query) {
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
    public ConnectorType getConnectorType() {
        ConnectorTypeFactory connectorTypeFactory = ConnectorTypeFactory.getInstance();
        ConnectorTypeFactory.ConnectorTypeBuilder connectorTypeBuilder = connectorTypeFactory.buildType(ConnectorTypeEnums.CSV.getCode(), ConnectorTypeEnums.CSV.getName(), ConnectorCategory.FILE);
        ;
        ConnectorType connectorType = connectorTypeBuilder.withAliases(ConnectorTypeEnums.CSV.getCode(), ConnectorTypeEnums.CSV.getMime()).withMetadata(new ConnectorTypeMetadata("1.0", ConnectorCategory.FILE.getDescription(), ConnectorCategory.FILE.getDescription())).build();
        return connectorType;
    }
}
