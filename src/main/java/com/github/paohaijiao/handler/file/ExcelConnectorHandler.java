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
package com.github.paohaijiao.handler.file;

import com.github.paohaijiao.config.ConnectorConfiguration;
import com.github.paohaijiao.enums.ConnectorTypeEnums;
import com.github.paohaijiao.handler.AbsFileConnectorBaseHandler;
import com.github.paohaijiao.query.ConnectorParsedQuery;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * packageName com.github.paohaijiao.handler.file
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/26
 */
public class ExcelConnectorHandler extends AbsFileConnectorBaseHandler {

    protected static final String sheet = "sheet";

    /**
     * 将 Excel 文件转换为 List<Row>
     *
     * @param excelFile Excel 文件
     * @param sheetName 工作表
     * @param hasHeader 是否包含表头
     * @return List<Row> 对象列表
     * @throws IOException 如果文件读取失败
     */
    public static List<com.github.paohaijiao.dataset.Row> convert(File excelFile, String sheetName, boolean hasHeader) throws IOException {
        try (FileInputStream fis = new FileInputStream(excelFile)) {
            return convert(fis, excelFile.getName(), sheetName, hasHeader);
        }
    }

    /**
     * 从输入流读取 Excel 数据并转换为 List<Row>（带文件名用于类型推断）
     *
     * @param inputStream 输入流
     * @param fileName    文件名（用于推断格式）
     * @param sheetName   工作表
     * @param hasHeader   是否包含表头
     * @return List<Row> 对象列表
     * @throws IOException 如果读取失败
     */
    public static List<com.github.paohaijiao.dataset.Row> convert(InputStream inputStream, String fileName, String sheetName, boolean hasHeader) throws IOException {
        byte[] bytes = toByteArray(inputStream);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            Workbook workbook = createWorkbook(bis, fileName);
            return convert(workbook, sheetName, hasHeader);
        }
    }

    /**
     * 从 Workbook 读取 Excel 数据并转换为 List<Row>
     *
     * @param workbook  Workbook 对象
     * @param sheetName 工作表
     * @param hasHeader 是否包含表头
     * @return List<Row> 对象列表
     */
    public static List<com.github.paohaijiao.dataset.Row> convert(Workbook workbook, String sheetName, boolean hasHeader) {
        List<com.github.paohaijiao.dataset.Row> result = new ArrayList<>();
        try {
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = sheet.iterator();
            String[] headers = null;
            int rowNumber = 0;
            while (rowIterator.hasNext()) {
                org.apache.poi.ss.usermodel.Row poiRow = rowIterator.next();
                rowNumber++;
                if (isRowEmpty(poiRow)) {
                    continue;
                }
                List<Object> values = getRowValues(poiRow);
                if (hasHeader && headers == null) {
                    headers = new String[values.size()];
                    for (int i = 0; i < values.size(); i++) {
                        headers[i] = String.valueOf(values.get(i));
                    }
                    continue;
                }
                if (headers == null) {
                    headers = generateDefaultHeaders(values.size());
                }
                com.github.paohaijiao.dataset.Row row = new com.github.paohaijiao.dataset.Row();
                for (int i = 0; i < Math.min(headers.length, values.size()); i++) {
                    Object value = values.get(i);
                    row.put(headers[i], value);
                }
                result.add(row);
            }
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 获取所有工作表名称
     *
     * @param excelFilePath Excel 文件路径
     * @return 工作表名称列表
     * @throws IOException 如果文件读取失败
     */
    public static List<String> getSheetNames(String excelFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(excelFilePath)) {
            byte[] bytes = toByteArray(fis);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
                Workbook workbook = createWorkbook(bis, excelFilePath);
                List<String> sheetNames = new ArrayList<>();
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    sheetNames.add(workbook.getSheetName(i));
                }
                workbook.close();
                return sheetNames;
            }
        }
    }

    /**
     * 创建 Workbook 对象
     *
     * @param inputStream 输入流
     * @param fileName    文件名（用于推断格式）
     * @return Workbook 对象
     * @throws IOException 如果文件格式不支持
     */
    private static Workbook createWorkbook(InputStream inputStream, String fileName) throws IOException {
        if (fileName != null) {
            if (fileName.toLowerCase().endsWith(".xlsx")) {
                return new XSSFWorkbook(inputStream);
            } else if (fileName.toLowerCase().endsWith(".xls")) {
                return new HSSFWorkbook(inputStream);
            }
        }
        try {
            return new XSSFWorkbook(inputStream);
        } catch (Exception e1) {
            try {
                if (inputStream instanceof ByteArrayInputStream) {
                    ((ByteArrayInputStream) inputStream).reset();
                } else {
                    throw new IOException("需要支持 reset 的输入流或提供文件名");
                }
                return new HSSFWorkbook(inputStream);
            } catch (Exception e2) {
                throw new IOException("无法识别 Excel 文件格式，请确保文件是有效的 .xls 或 .xlsx 格式", e2);
            }
        }
    }

    /**
     * 将输入流转换为字节数组
     *
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException 如果读取失败
     */
    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    /**
     * 获取行的所有单元格值
     *
     * @param row POI Row 对象
     * @return 单元格值列表
     */
    private static List<Object> getRowValues(org.apache.poi.ss.usermodel.Row row) {
        List<Object> values = new ArrayList<>();
        if (row == null) {
            return values;
        }
        int lastCellNum = row.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            values.add(getCellValue(cell));
        }
        return values;
    }

    /**
     * 获取单元格值
     *
     * @param cell 单元格
     * @return 单元格值
     */
    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (int) numericValue) {
                        if (numericValue <= Integer.MAX_VALUE && numericValue >= Integer.MIN_VALUE) {
                            return (int) numericValue;
                        } else {
                            return (long) numericValue;
                        }
                    } else {
                        return numericValue;
                    }
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                try {
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cellValue = evaluator.evaluate(cell);
                    switch (cellValue.getCellType()) {
                        case NUMERIC:
                            return cellValue.getNumberValue();
                        case STRING:
                            return cellValue.getStringValue();
                        case BOOLEAN:
                            return cellValue.getBooleanValue();
                        default:
                            return cell.getCellFormula();
                    }
                } catch (Exception e) {
                    return cell.getCellFormula();
                }
            case BLANK:
                return null;
            default:
                return null;
        }
    }

    /**
     * 检查行是否为空
     *
     * @param row POI Row 对象
     * @return 是否为空
     */
    private static boolean isRowEmpty(org.apache.poi.ss.usermodel.Row row) {
        if (row == null) {
            return true;
        }

        int lastCellNum = row.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                Object value = getCellValue(cell);
                if (value != null && !value.toString().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 生成默认列名
     *
     * @param count 列数量
     * @return 默认列名数组
     */
    private static String[] generateDefaultHeaders(int count) {
        String[] headers = new String[count];
        for (int i = 0; i < count; i++) {
            headers[i] = "column" + (i + 1);
        }
        return headers;
    }

    @Override
    public String getType() {
        return ConnectorTypeEnums.EXCEL.getCode();
    }

    @Override
    public List<com.github.paohaijiao.dataset.Row> doParse(Path path, ConnectorParsedQuery query) {
        ConnectorConfiguration config = new ConnectorConfiguration();
        query.getConnectorProperties().forEach(config::setProperty);
        Boolean h = config.getProperty(header, Boolean.class);
        String connectorSheetName = config.getProperty(sheet, String.class);
        try {
            return convert(path.toFile(), connectorSheetName, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
