package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        //SpringApplication.run(Main.class, args);



        ArrayList<String[]> dataCSV = new ArrayList<>();
        String[] nextRecord;

        try {
            FileReader filereader = new FileReader("opisanie_poley.csv");
            CSVReader csvReader = new CSVReader(filereader);

            while ((nextRecord = csvReader.readNext()) != null) {
                dataCSV.add(nextRecord);
            }
        }
        catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }



        final String name = "shop";
        SqlTerminal dbTable = new SqlTerminal();

        try {
            dbTable.connect("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
            dbTable.create(name, dataCSV);
            dbTable.insert(name, dataCSV);
            dbTable.close();
        }

        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}