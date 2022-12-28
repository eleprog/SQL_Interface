package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        SqlTerminal dbTable = SqlTerminal.getInstance();
        final String name = "shop";

        try {
            dbTable.connect("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");

            dbTable.delete(name);
            dbTable.create(name, fileToList.csvReadToList("opisanie_poley.csv"));
            dbTable.insert(name, fileToList.csvReadToList("soderjimoe_poley.csv"));

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