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

import com.github.paohaijiao.dataset.JQuickConnectorColumnMeta;
import com.github.paohaijiao.dataset.JQuickConnectorDataSet;
import com.github.paohaijiao.holder.JQuickConnectorFieldMappingHolder;
import com.github.paohaijiao.statement.JQuickRow;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSet转换工具类
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/13
 */
public class JQuickConnectorDataSetAssembler {

    /**
     * 将List<Row>根据字段映射配置转换为DataSet
     *
     * @param rows          原始数据行列表
     * @param fieldMappings 字段映射配置列表
     * @return 转换后的DataSet
     */
    public static JQuickConnectorDataSet convert(List<JQuickRow> rows, List<JQuickConnectorFieldMappingHolder> fieldMappings) {
        if (rows == null || fieldMappings == null) {
            throw new IllegalArgumentException("Rows and fieldMappings cannot be null");
        }
        List<JQuickConnectorColumnMeta> columns = buildColumnMeta(fieldMappings);
        List<JQuickRow> processedRows = processRows(rows, fieldMappings);
        return new JQuickConnectorDataSet(columns, processedRows);
    }

    /**
     * 构建ColumnMeta列表
     */
    private static List<JQuickConnectorColumnMeta> buildColumnMeta(List<JQuickConnectorFieldMappingHolder> fieldMappings) {
        List<JQuickConnectorColumnMeta> columns = new ArrayList<>();
        for (JQuickConnectorFieldMappingHolder mapping : fieldMappings) {
            String columnName = mapping.getTargetField() != null ? mapping.getTargetField() : mapping.getSourceField();
            Class<?> dataType = mapping.getDataType() != null ? mapping.getDataType() : Object.class;
            String source = mapping.getSourceField();
            columns.add(new JQuickConnectorColumnMeta(columnName, dataType, source));
        }
        return columns;
    }

    /**
     * 处理每一行数据，应用字段映射和处理器
     */
    private static List<com.github.paohaijiao.statement.JQuickRow> processRows(List<JQuickRow> rows, List<JQuickConnectorFieldMappingHolder> fieldMappings) {
        List<com.github.paohaijiao.statement.JQuickRow> processedRows = new ArrayList<>();
        for (JQuickRow row : rows) {
            JQuickRow processedRow = new JQuickRow();
            for (JQuickConnectorFieldMappingHolder mapping : fieldMappings) {
                Object value = mapping.getProcessor().process(row, mapping);
                processedRow.put(mapping.getTargetField(), value);
            }
            processedRows.add(processedRow);
        }

        return processedRows;
    }

    /**
     * 批量转换多个数据集的便捷方法
     *
     * @param rowsList      多个原始数据行列表
     * @param fieldMappings 字段映射配置列表
     * @return 转换后的DataSet列表
     */
    public static List<JQuickConnectorDataSet> convertBatch(List<List<JQuickRow>> rowsList, List<JQuickConnectorFieldMappingHolder> fieldMappings) {
        List<JQuickConnectorDataSet> dataSets = new ArrayList<>();
        for (List<JQuickRow> rows : rowsList) {
            dataSets.add(convert(rows, fieldMappings));
        }
        return dataSets;
    }
}
