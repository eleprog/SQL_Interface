package org.example;

public class productObj {
    private String amount   = null;
    private String price    = null;
    private String discount = null;

    productObj(String[] data) {
        if(data != null) {
            amount      = data[0];
            price       = data[1];
            discount    = data[2];
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
