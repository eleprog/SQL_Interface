package org.example;

public class ProductObj {
    private String name     = null;
    private String type     = null;
    private String amount   = null;
    private Integer price    = null;
    private Integer discount = null;

    /*ProductObj(String[] data) {
        if(data != null) {
            name        = data[0];
            type        = data[1];
            amount      = data[2];
            price       = data[3];
            discount    = data[4];
        }
    }*/

    public ProductObj(String name, String type, String amount, Integer price, Integer discount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
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

    public Integer getPrice() {
        return price;
    }

    public Integer getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "productObj{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                ", price='" + price + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }
}
