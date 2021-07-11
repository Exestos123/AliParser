package com.exestos.aliparser.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvGenerator<T> {

    private final List<T> dataList;
    private Class type;

    public CsvGenerator(List<T> dataList) {
        this.dataList = dataList;
    }

    private boolean validateDataList() {
        if (dataList.size() > 0) {
            type = dataList.get(0).getClass();
            return true;
        }
        return false;
    }

    public Path generateCsv(String fileName) throws IOException, IllegalAccessException {
        Path tempPath = generateTempPath();
        Path filePath = tempPath.resolve(fileName);
        Files.deleteIfExists(filePath);
        Files.createFile(filePath);

        if (validateDataList()) {
            loadData(filePath);
        }

        return filePath;
    }

    private void loadData(Path filePath) throws IOException, IllegalAccessException {
        BufferedWriter out = Files.newBufferedWriter(filePath);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                .withHeader(getFieldsName()))) {

            Field[] fields = type.getFields();
            List<Object> fieldValues;
            for (T entity : dataList) {
                fieldValues = new ArrayList<>();
                for (Field field : fields) {
                    field.setAccessible(true);
                    fieldValues.add(field.get(entity));
                }
                printer.printRecord(fieldValues);
            }
        }
    }

    private Path generateTempPath() throws IOException {
        Path tempPath = Paths.get("temp");
        return Files.exists(tempPath) ? tempPath : Files.createDirectory(tempPath);
    }

    private String[] getFieldsName() {
        Field[] fields = type.getFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }
}
