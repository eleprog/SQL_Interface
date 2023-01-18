package org.example;

public class productObj {
    private String name     = null;
    private String type     = null;
    private String amount   = null;
    private String price    = null;
    private String discount = null;

    productObj(String[] data) {
        if(data != null) {
            name        = data[0];
            type        = data[1];
            amount      = data[2];
            price       = data[3];
            discount    = data[4];
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
