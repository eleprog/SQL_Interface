package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        // запуск "spring web"
        SpringApplication.run(Main.class, args);

        SqlTerminal dbTable = SqlTerminal.getInstance();
        final String name = "shop";
        final String[] columnsToAdd = {"prod_name", "prod_type", "prod_amount", "prod_price", "prod_discount"};

        dbTable.setPrintConsoleFlag(true);

        dbTable.connect("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");

        dbTable.delete(name);

        dbTable.create(name, FileToList.csvReadToList("opisanie_poley.csv"));
        dbTable.insert(name, columnsToAdd, FileToList.csvReadToList("soderjimoe_poley.csv"));

        String[] columnAdd1 = {"prod_type","INT"};
        System.out.println(dbTable.addColumn(name, columnAdd1));

        String[] columnAdd2 = {"id","INT"};
        System.out.println(dbTable.addColumn(name, columnAdd2));

        //dbTable.deleteRows(name, new String[]{"prod_name = 'potato'", "prod_type = 'RUS'"});

        dbTable.updateColumn(name, "prod_amount = 'update'", new String[]{"prod_name = 'potato'","prod_type = 'RUS'"});
        dbTable.updateColumn(name, "prod_amount = 'update'", new String[]{"prod_name = 'potato'","prod_type = 'TUR'"});


    }
}