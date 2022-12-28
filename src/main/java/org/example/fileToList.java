package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fileToList {

    static public List<String[]> csvReadToList(String path) throws IOException, CsvValidationException {
        String[] nextRecord;
        List<String[]> data = new ArrayList<>();

        CSVReader csvReader = new CSVReader(new FileReader(path));

        while ((nextRecord = csvReader.readNext()) != null)
            data.add(nextRecord);

        return data;
    }





}
