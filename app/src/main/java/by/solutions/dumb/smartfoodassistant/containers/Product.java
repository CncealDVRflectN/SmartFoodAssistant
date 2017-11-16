package by.solutions.dumb.smartfoodassistant.containers;

public class Product {
    private String name;
    private String currency;
    private double price;

    public Product(String name, String currency, double price) {
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
