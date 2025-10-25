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
package com.github.paohaijiao.assembler;

import com.github.paohaijiao.dataset.ColumnMeta;
import com.github.paohaijiao.dataset.DataSet;
import com.github.paohaijiao.dataset.Row;
import com.github.paohaijiao.exception.JAssert;
import com.github.paohaijiao.holder.ConnectorFieldMappingHolder;
import com.github.paohaijiao.field.ConnectorProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSet转换工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/13
 */
public class DataSetAssembler {

    /**
     * 将List<Row>根据字段映射配置转换为DataSet
     *
     * @param rows 原始数据行列表
     * @param fieldMappings 字段映射配置列表
     * @return 转换后的DataSet
     */
    public static DataSet convert(List<Row> rows, List<ConnectorFieldMappingHolder> fieldMappings) {
        if (rows == null || fieldMappings == null) {
            throw new IllegalArgumentException("Rows and fieldMappings cannot be null");
        }
        List<ColumnMeta> columns = buildColumnMeta(fieldMappings);
        List<Row> processedRows = processRows(rows, fieldMappings);
        return new DataSet(columns, processedRows);
    }
    /**
     * 构建ColumnMeta列表
     */
    private static List<ColumnMeta> buildColumnMeta(List<ConnectorFieldMappingHolder> fieldMappings) {
        List<ColumnMeta> columns = new ArrayList<>();
        for (ConnectorFieldMappingHolder mapping : fieldMappings) {
            String columnName = mapping.getTargetField() != null ? mapping.getTargetField() : mapping.getSourceField();
            Class<?> dataType = mapping.getDataType() != null ? mapping.getDataType() : Object.class;
            String source = mapping.getSourceField();
            columns.add(new ColumnMeta(columnName, dataType, source));
        }
        return columns;
    }

    /**
     * 处理每一行数据，应用字段映射和处理器
     */
    private static List<Row> processRows(List<Row> rows, List<ConnectorFieldMappingHolder> fieldMappings) {
        List<Row> processedRows = new ArrayList<>();
        for (Row row : rows) {
            Row processedRow = new Row();
            for (ConnectorFieldMappingHolder mapping : fieldMappings) {
                Object value=mapping.getProcessor().process(row,mapping);
                processedRow.put(mapping.getTargetField(), value);
            }
            processedRows.add(processedRow);
        }

        return processedRows;
    }

    /**
     * 批量转换多个数据集的便捷方法
     *
     * @param rowsList 多个原始数据行列表
     * @param fieldMappings 字段映射配置列表
     * @return 转换后的DataSet列表
     */
    public static List<DataSet> convertBatch(List<List<Row>> rowsList, List<ConnectorFieldMappingHolder> fieldMappings) {
        List<DataSet> dataSets = new ArrayList<>();
        for (List<Row> rows : rowsList) {
            dataSets.add(convert(rows, fieldMappings));
        }

        return dataSets;
    }
}
