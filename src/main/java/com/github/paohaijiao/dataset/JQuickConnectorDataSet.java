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
package com.github.paohaijiao.dataset;

import com.github.paohaijiao.console.JConsole;
import com.github.paohaijiao.statement.JQuickRow;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * packageName com.github.paohaijiao.dataset
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/8/13
 */
public class JQuickConnectorDataSet {

    private final List<JQuickConnectorColumnMeta> columns;

    private final List<JQuickRow> rows;

    private final JConsole console=new JConsole();

    public JQuickConnectorDataSet(List<JQuickConnectorColumnMeta> columns, List<JQuickRow> rows) {
        this.columns = Collections.unmodifiableList(new ArrayList<>(columns));
        this.rows = Collections.unmodifiableList(new ArrayList<>(rows));
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<JQuickConnectorColumnMeta> getColumns() {
        return columns;
    }

    public List<JQuickRow> getRows() {
        return rows;
    }

    public Stream<Object> getColumnValues(String columnName) {
        return rows.stream().map(row -> row.get(columnName));
    }

    public List<String> getColumnNames() {
        return columns.stream().map(JQuickConnectorColumnMeta::getName).collect(Collectors.toList());
    }

    public int size() {
        return rows.size();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }
    /**
     * Gets the first row of the dataset.
     *
     * @return the first row, or null if dataset is empty
     */
    public JQuickRow first() {
        return isEmpty() ? null : rows.get(0);
    }
    /**
     * Gets the last row of the dataset.
     *
     * @return the last row, or null if dataset is empty
     */
    public JQuickRow last() {
        return isEmpty() ? null : rows.get(rows.size() - 1);
    }
    /**
     * Gets the row at the specified index.
     *
     * @param index the row index
     * @return the row
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public JQuickRow get(int index) {
        if (index < 0 || index >= rows.size()) {
            throw new IndexOutOfBoundsException(
                    String.format("Row index: %d, Size: %d", index, rows.size())
            );
        }
        return rows.get(index);
    }
    /**
     * Gets a sub-dataset from start index (inclusive) to end index (exclusive).
     *
     * @param startIndex start index (inclusive)
     * @param endIndex end index (exclusive)
     * @return a new dataset with the specified range of rows
     */
    public JQuickConnectorDataSet subDataset(int startIndex, int endIndex) {
        List<JQuickRow> subRows = rows.subList(startIndex, Math.min(endIndex, rows.size()));
        return new JQuickConnectorDataSet(columns, new ArrayList<>(subRows));
    }
    /**
     * Gets the first n rows as a new dataset.
     *
     * @param n number of rows to take
     * @return a new dataset with the first n rows
     */
    public JQuickConnectorDataSet limit(int n) {
        return subDataset(0, n);
    }
    /**
     * Skips the first n rows and returns the remaining.
     *
     * @param n number of rows to skip
     * @return a new dataset with rows after skipping n rows
     */
    public JQuickConnectorDataSet skip(int n) {
        return subDataset(n, rows.size());
    }

    /**
     * Counts the number of rows that satisfy the predicate.
     *
     * @param predicate the condition to test
     * @return the count of matching rows
     */
    public long count(Predicate<JQuickRow> predicate) {
        return rows.stream().filter(predicate).count();
    }
    /**
     * Sums the values of a numeric column.
     *
     * @param columnName the column name
     * @return the sum, or 0 if column doesn't exist or has no numeric values
     */
    public double sum(String columnName) {
        return rows.stream()
                .mapToDouble(row -> {
                    Number value = row.getAs(columnName, Number.class);
                    return value != null ? value.doubleValue() : 0.0;
                })
                .sum();
    }
    /**
     * Averages the values of a numeric column.
     *
     * @param columnName the column name
     * @return the average, or 0 if no rows
     */
    public double avg(String columnName) {
        if (isEmpty()) return 0.0;
        return sum(columnName) / size();
    }
    /**
     * Gets the maximum value of a numeric column.
     *
     * @param columnName the column name
     * @return the maximum value, or null if no rows or no numeric values
     */
    public Double max(String columnName) {
        return rows.stream()
                .map(row -> row.getAs(columnName, Number.class))
                .filter(Objects::nonNull)
                .mapToDouble(Number::doubleValue)
                .max()
                .orElse(0.0);
    }
    /**
     * Gets the minimum value of a numeric column.
     *
     * @param columnName the column name
     * @return the minimum value, or null if no rows or no numeric values
     */
    public Double min(String columnName) {
        return rows.stream()
                .map(row -> row.getAs(columnName, Number.class))
                .filter(Objects::nonNull)
                .mapToDouble(Number::doubleValue)
                .min()
                .orElse(0.0);
    }
    /**
     * Groups the dataset by a column value.
     *
     * @param columnName the column to group by
     * @return a map from group key to list of rows
     */
    public Map<Object, List<JQuickRow>> groupBy(String columnName) {
        return rows.stream().collect(Collectors.groupingBy(row -> row.get(columnName)));
    }
    /**
     * Groups the dataset by a key extractor function.
     *
     * @param keyExtractor function to extract the grouping key
     * @param <K> the key type
     * @return a map from group key to list of rows
     */
    public <K> Map<K, List<JQuickRow>> groupBy(Function<JQuickRow, K> keyExtractor) {
        return rows.stream().collect(Collectors.groupingBy(keyExtractor));
    }
    /**
     * Groups by a column and aggregates using the provided collector.
     *
     * @param columnName the column to group by
     * @param collector the collector for aggregation
     * @param <K> the key type
     * @param <A> the accumulator type
     * @param <R> the result type
     * @return a map from group key to aggregation result
     */
    public <K, A, R> Map<K, R> groupByAndAggregate(String columnName, java.util.stream.Collector<JQuickRow, A, R> collector) {
        return rows.stream().collect(Collectors.groupingBy(
                row -> (K) row.get(columnName),
                collector
        ));
    }
    /**
     * Sorts the dataset by a column in ascending order.
     *
     * @param columnName the column to sort by
     * @return a new sorted dataset
     */
    public JQuickConnectorDataSet orderBy(String columnName) {
        return orderBy(columnName, true);
    }
    /**
     * Sorts the dataset by a column.
     *
     * @param columnName the column to sort by
     * @param ascending true for ascending, false for descending
     * @return a new sorted dataset
     */
    public JQuickConnectorDataSet orderBy(String columnName, boolean ascending) {
        List<JQuickRow> sorted = new ArrayList<>(rows);
        Comparator<JQuickRow> comparator = Comparator.comparing(
                (JQuickRow row) -> {
                    Object value = row.get(columnName);
                    if (value == null) return null;
                    return value;
                },
                Comparator.nullsLast((Comparator<Object>) (a, b) -> {
                    if (a instanceof Comparable && b instanceof Comparable) {
                        return ((Comparable) a).compareTo(b);
                    }
                    return a.toString().compareTo(b.toString());
                })
        );
        if (!ascending) {
            comparator = comparator.reversed();
        }
        sorted.sort(comparator);
        return new JQuickConnectorDataSet(columns, sorted);
    }
    /**
     * Sorts by multiple columns.
     *
     * @param columnNames the columns to sort by (order matters)
     * @return a new sorted dataset
     */
    public JQuickConnectorDataSet orderBy(String... columnNames) {
        List<JQuickRow> sorted = new ArrayList<>(rows);

        Comparator<JQuickRow> comparator = (row1, row2) -> {
            for (String columnName : columnNames) {
                Object v1 = row1.get(columnName);
                Object v2 = row2.get(columnName);
                if (v1 == null && v2 == null) continue;  // Handle nulls (nulls last)
                if (v1 == null) return 1;
                if (v2 == null) return -1;
                int result;
                if (v1 instanceof Comparable && v2 instanceof Comparable) {// Compare based on type
                    @SuppressWarnings("unchecked")
                    Comparable<Object> c1 = (Comparable<Object>) v1;
                    result = c1.compareTo(v2);
                } else {
                    result = v1.toString().compareTo(v2.toString());
                }
                if (result != 0) return result;
            }
            return 0;
        };

        sorted.sort(comparator);
        return new JQuickConnectorDataSet(columns, sorted);
    }
    /**
     * Returns a dataset with duplicate rows removed (based on all columns).
     *
     * @return a new dataset with distinct rows
     */
    public JQuickConnectorDataSet distinct() {
        List<JQuickRow> distinct = rows.stream()
                .distinct()
                .collect(Collectors.toList());
        return new JQuickConnectorDataSet(columns, distinct);
    }
    /**
     * Returns a dataset with duplicate rows removed based on specified columns.
     *
     * @param columnNames the columns to consider for uniqueness
     * @return a new dataset with distinct rows by the specified columns
     */
    public JQuickConnectorDataSet distinctBy(String... columnNames) {
        Set<List<Object>> seen = new HashSet<>();
        List<JQuickRow> distinct = new ArrayList<>();
        for (JQuickRow row : rows) {
            List<Object> key = new ArrayList<>();
            for (String columnName : columnNames) {
                key.add(row.get(columnName));
            }
            if (seen.add(key)) {
                distinct.add(row);
            }
        }
        return new JQuickConnectorDataSet(columns, distinct);
    }
    /**
     * Concatenates this dataset with another dataset.
     *
     * @param other the other dataset
     * @return a new dataset containing rows from both datasets
     */
    public JQuickConnectorDataSet concat(JQuickConnectorDataSet other) {
        List<JQuickRow> combined = new ArrayList<>(rows);
        combined.addAll(other.getRows());
        return new JQuickConnectorDataSet(columns, combined);
    }
    /**
     * Inner join with another dataset on a column.
     *
     * @param other the other dataset
     * @param thisColumn column from this dataset
     * @param otherColumn column from the other dataset
     * @return a new dataset with joined rows
     */
    public JQuickConnectorDataSet innerJoin(JQuickConnectorDataSet other, String thisColumn, String otherColumn) {
        List<JQuickRow> result = new ArrayList<>();
        // Build index for other dataset for performance
        Map<Object, List<JQuickRow>> otherIndex = other.groupBy(otherColumn);
        for (JQuickRow row : rows) {
            Object key = row.get(thisColumn);
            List<JQuickRow> matchingRows = otherIndex.get(key);
            if (matchingRows != null) {
                for (JQuickRow otherRow : matchingRows) {
                    JQuickRow merged = new JQuickRow();
                    merged.putAll(row);
                    merged.putAll(otherRow);
                    result.add(merged);
                }
            }
        }
        // Merge column metadata
        List<JQuickConnectorColumnMeta> mergedColumns = new ArrayList<>(columns);
        mergedColumns.addAll(other.getColumns());
        return new JQuickConnectorDataSet(mergedColumns, result);
    }
    /**
     * Converts the dataset to a list of maps.
     *
     * @return list of maps representing the rows
     */
    public List<Map<String, Object>> toMapList() {
        return rows.stream().map(JQuickRow::toMap).collect(Collectors.toList());
    }
    /**
     * Converts the dataset to a list of objects of the specified class.
     *
     * @param clazz the target class
     * @param <T> the type parameter
     * @return list of converted objects
     */
    public <T> List<T> toBeanList(Class<T> clazz) {
        return rows.stream()
                .map(row -> row.toBean(clazz))
                .collect(Collectors.toList());
    }
    /**
     * Prints the dataset as a table to console.
     */
    public void printTable() {
        printTable(Integer.MAX_VALUE);
    }
    /**
     * Prints the dataset as a table to console with row limit using JConsole.
     *
     * @param maxRows maximum number of rows to print
     */
    public void printTable(int maxRows) {
        if (columns.isEmpty()) {
            console.warn("Empty dataset (no columns)");
            return;
        }
        if (rows.isEmpty()) {
            console.info("Empty dataset (no rows)");
            return;
        }
        // Calculate column widths
        Map<String, Integer> columnWidths = new LinkedHashMap<>();
        for (JQuickConnectorColumnMeta col : columns) {
            String colName = col.getName();
            int width = colName.length();
            for (int i = 0; i < Math.min(maxRows, rows.size()); i++) {
                Object value = rows.get(i).get(colName);
                String strValue = value != null ? value.toString() : "null";
                width = Math.max(width, strValue.length());
            }
            columnWidths.put(colName, Math.min(width, 50)); // Cap at 50 chars
        }
        // Build format string
        StringBuilder formatBuilder = new StringBuilder("|");
        for (int width : columnWidths.values()) {
            formatBuilder.append(" %-").append(width).append("s |");
        }
        String rowFormat = formatBuilder.toString();
        // Build separator line
        StringBuilder separatorBuilder = new StringBuilder("+");
        for (int width : columnWidths.values()) {
            separatorBuilder.append("-").append(repeatChar('-', width)).append("-+");
        }
        String separator = separatorBuilder.toString();
        // Print table header
        console.info(separator);
        console.info(String.format(rowFormat, columnWidths.keySet().toArray()));
        console.info(separator);
        // Print rows
        int displayedRows = 0;
        for (int i = 0; i < Math.min(maxRows, rows.size()); i++) {
            JQuickRow row = rows.get(i);
            Object[] values = new Object[columnWidths.size()];
            int idx = 0;
            for (String colName : columnWidths.keySet()) {
                Object value = row.get(colName);
                String strValue = value != null ? value.toString() : "null";
                int width = columnWidths.get(colName);
                if (strValue.length() > width) {
                    strValue = strValue.substring(0, width - 3) + "...";
                }
                values[idx++] = strValue;
            }
            console.info(String.format(rowFormat, values));
            displayedRows++;
        }
        // Print footer
        console.info(separator);
        if (rows.size() > maxRows) {
            console.warn("... and " + (rows.size() - maxRows) + " more rows (total: " + rows.size() + " rows)");
        } else {
            console.info("Total: " + rows.size() + " rows");
        }
    }
    /**
     * Prints a summary of the dataset.
     */
    public void printSummary() {
        if (columns.isEmpty()) {
            console.warn("Empty dataset (no columns)");
            return;
        }
        console.info("=== Dataset Summary ===");
        console.info("Columns: " + columns.size());
        for (JQuickConnectorColumnMeta col : columns) {
            String colName = col.getName();
            long nonNullCount = rows.stream()
                    .filter(row -> row.get(colName) != null)
                    .count();
            console.info(String.format("  %s (%s) - non-null: %d/%d (%.1f%%)",
                    colName,
                    col.getType().getSimpleName(),
                    nonNullCount,
                    rows.size(),
                    rows.isEmpty() ? 0 : (nonNullCount * 100.0 / rows.size())));
        }
        console.info("Total rows: " + rows.size());
        console.info("======================");
    }
    /**
     * Prints the dataset as a table with debug level (more detailed).
     *
     * @param maxRows maximum number of rows to print
     */
    public void printTableDebug(int maxRows) {
        if (columns.isEmpty()) {
            console.debug("Empty dataset (no columns)");
            return;
        }
        console.debug("=== Dataset Debug Info ===");
        console.debug("Columns: " + columns.size());
        for (JQuickConnectorColumnMeta col : columns) {
            console.debug("  - " + col.getName() + " (" + col.getType().getSimpleName() + ")");
        }
        console.debug("Total rows: " + rows.size());
        console.debug("==========================");
        printTable(maxRows);
    }
    /**
     * Helper method to repeat a string n times (Java 8 compatible).
     *
     * @param str the string to repeat
     * @param count the number of repetitions
     * @return the repeated string
     */
    private static String repeat(String str, int count) {
        if (count <= 0) return "";
        StringBuilder sb = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    /**
     * Adds a new column with values computed from existing data.
     *
     * @param newColumnName the new column name
     * @param type the column type
     * @param source the source description
     * @param valueExtractor function to compute the value for each row
     * @return a new dataset with the additional column
     */
    public JQuickConnectorDataSet addColumn(String newColumnName, Class<?> type, String source, Function<JQuickRow, Object> valueExtractor) {
        List<JQuickRow> newRows = new ArrayList<>();
        for (JQuickRow row : rows) {
            JQuickRow newRow = new JQuickRow(row);
            newRow.put(newColumnName, valueExtractor.apply(row));
            newRows.add(newRow);
        }
        List<JQuickConnectorColumnMeta> newColumns = new ArrayList<>(columns);
        newColumns.add(new JQuickConnectorColumnMeta(newColumnName, type, source));
        return new JQuickConnectorDataSet(newColumns, newRows);
    }
    /**
     * Selects only the specified columns.
     *
     * @param columnNames the columns to keep
     * @return a new dataset with only the specified columns
     */
    public JQuickConnectorDataSet select(String... columnNames) {
        Set<String> keepSet = new HashSet<>(Arrays.asList(columnNames));
        List<JQuickConnectorColumnMeta> selectedColumns = columns.stream()
                .filter(col -> keepSet.contains(col.getName()))
                .collect(Collectors.toList());
        List<JQuickRow> selectedRows = rows.stream()
                .map(row -> {
                    JQuickRow newRow = new JQuickRow();
                    for (String colName : columnNames) {
                        if (row.containsKey(colName)) {
                            newRow.put(colName, row.get(colName));
                        }
                    }
                    return newRow;
                })
                .collect(Collectors.toList());
        return new JQuickConnectorDataSet(selectedColumns, selectedRows);
    }
    /**
     * Drops the specified columns from the dataset.
     *
     * @param columnNames the columns to remove
     * @return a new dataset without the specified columns
     */
    public JQuickConnectorDataSet drop(String... columnNames) {
        Set<String> dropSet = new HashSet<>(Arrays.asList(columnNames));
        List<JQuickConnectorColumnMeta> remainingColumns = columns.stream()
                .filter(col -> !dropSet.contains(col.getName()))
                .collect(Collectors.toList());
        List<JQuickRow> remainingRows = rows.stream()
                .map(row -> {
                    JQuickRow newRow = new JQuickRow(row);
                    for (String colName : dropSet) {
                        newRow.remove(colName);
                    }
                    return newRow;
                })
                .collect(Collectors.toList());
        return new JQuickConnectorDataSet(remainingColumns, remainingRows);
    }
    /**
     * Gets summary statistics for a numeric column.
     *
     * @param columnName the column name
     * @return a map containing count, sum, avg, min, max
     */
    public Map<String, Double> getStatistics(String columnName) {
        DoubleSummaryStatistics stats = rows.stream()
                .map(row -> row.getAs(columnName, Number.class))
                .filter(Objects::nonNull)
                .mapToDouble(Number::doubleValue)
                .summaryStatistics();

        Map<String, Double> result = new LinkedHashMap<>();
        result.put("count", (double) stats.getCount());
        result.put("sum", stats.getSum());
        result.put("avg", stats.getAverage());
        result.put("min", stats.getMin());
        result.put("max", stats.getMax());
        return result;
    }
    /**
     * Performs an action for each row.
     *
     * @param action the action to perform
     */
    public void forEach(java.util.function.Consumer<JQuickRow> action) {
        rows.forEach(action);
    }

    /**
     * Gets a stream of rows for advanced operations.
     *
     * @return a stream of rows
     */
    public Stream<JQuickRow> stream() {
        return rows.stream();
    }
    /**
     * Gets a parallel stream of rows.
     *
     * @return a parallel stream of rows
     */
    public Stream<JQuickRow> parallelStream() {
        return rows.parallelStream();
    }
    public JQuickConnectorDataSet filter(Predicate<JQuickRow> predicate) {
        List<JQuickRow> filtered = rows.stream().filter(predicate).collect(Collectors.toList());
        return new JQuickConnectorDataSet(columns, filtered);
    }

    public JQuickConnectorDataSet map(Function<JQuickRow, JQuickRow> mapper) {
        List<JQuickRow> mapped = rows.stream().map(mapper).collect(Collectors.toList());
        return new JQuickConnectorDataSet(columns, mapped);
    }

    public <T> Stream<T> mapToColumn(Function<Object, T> mapper, String columnName) {
        return rows.stream().map(row -> mapper.apply(row.get(columnName)));
    }

    public static class Builder {
        private final List<JQuickConnectorColumnMeta> columns = new ArrayList<>();
        private final List<JQuickRow> rows = new ArrayList<>();
        public Builder addColumn(String name, Class<?> type, String source) {
            columns.add(new JQuickConnectorColumnMeta(name, type, source));
            return this;
        }
        public Builder addRow(JQuickRow row) {
            rows.add(row);
            return this;
        }
        public JQuickConnectorDataSet build() {
            return new JQuickConnectorDataSet(columns, rows);
        }
    }
    /**
     * Helper method to repeat a character n times.
     *
     * @param c the character to repeat
     * @param count the number of repetitions
     * @return the repeated string
     */
    private static String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
