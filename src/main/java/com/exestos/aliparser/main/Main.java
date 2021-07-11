package com.exestos.aliparser.main;

import com.exestos.aliparser.csv.CsvGenerator;
import com.exestos.aliparser.parser.ListParser;
import com.exestos.aliparser.struct.Item;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long started = Instant.now().toEpochMilli();
        //===================================================
        try {
            int limit = 100; //could be changed

            ListParser listParser = new ListParser(limit);
            List<Item> dataList = listParser.getDataList();

            CsvGenerator<Item> csvGenerator = new CsvGenerator<>(dataList);
            Path csvPath = csvGenerator.generateCsv("file.csv");
            System.out.println(csvPath.toAbsolutePath()); //there could be another logic with this file

        } catch (Exception e) {
            e.printStackTrace();
        }
        //===================================================
        Long end = Instant.now().toEpochMilli() - started;
        System.out.println(end); //working time

        //На парсинг 100 уходит немного больше полторы секунды
        //На парсинг 1000 записей на моем компе уходит не больше 4 секунд
    }
}
