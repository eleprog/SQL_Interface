package org.example;

public class productObj {

    private final String tableName = "shop";
    private String amount;
    private String price;
    private String discount;


    productObj(String tableName, String pk) {
        String[] columns = null;
        String[][] data = SqlTerminal.getInstance().select(tableName, columns);

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
