package org.example;

import java.util.ArrayList;
import java.util.List;

public class productObj {
    private String amount   = null;
    private String price    = null;
    private String discount = null;

    productObj(String tableName, String pk) {
        String[] columns = pk.split(" ", 2);

        if(columns.length != 2)
            return;

        columns[0] = "prod_name = '" + columns[0] + "'";
        columns[1] = "prod_type = '" + columns[1] + "'";

        List<String[]> data = SqlTerminal.getInstance().select(tableName, columns);

        if(data != null) {
            String[] line = data.get(0);
            amount      = line[2];
            price       = line[3];
            discount    = line[4];
        }
    }

    public String getAmount() {
        return amount;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }
}
