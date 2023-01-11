package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Класс для обработки .csv файлов
public class fileToList {

    /* Преобразование файла в список массивов строк
     *
     * path - путь к файлу
     */
    static public List<String[]> csvReadToList(String path) {
        String[] nextRecord;
        List<String[]> data = new ArrayList<>();

        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));

            while ((nextRecord = csvReader.readNext()) != null)
                data.add(nextRecord);

            return data;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }





}
