package org.example;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс для обработки .csv файлов
 */
public class FileToList {

    /** Преобразование файла в список массивов строк
     *
     * @param path путь к файлу
     * @return возвращает список массивов строк
     * (если произошла ошибка то возращается пустой список)
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