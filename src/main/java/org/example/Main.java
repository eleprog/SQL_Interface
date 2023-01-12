package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        // запуск "spring web"
        SpringApplication.run(Main.class, args);

        SqlTerminal dbTable = SqlTerminal.getInstance();
        final String name = "shop";
        final String[] columnsToAdd = {"prod_name", "prod_type", "prod_amount", "prod_price", "prod_discount"};

        try {
            dbTable.connect("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");

            dbTable.delete(name);

            dbTable.create(name, fileToList.csvReadToList("opisanie_poley.csv"));
            dbTable.insert(name, columnsToAdd, fileToList.csvReadToList("soderjimoe_poley.csv"));

            String[] columnAdd1 = {"prod_type","INT"};
            System.out.println(dbTable.addColumn(name, columnAdd1));

            String[] columnAdd2 = {"id","INT"};
            System.out.println(dbTable.addColumn(name, columnAdd2));

            String[] columnAdd3 = {"prod_type = 'RUS'"};
            System.out.println(dbTable.deleteRows(name, columnAdd3));


            //dbTable.close();
        }

        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}