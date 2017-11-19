package by.solutions.dumb.smartfoodassistant.containers;


public class Shop {
    private String name;
    private String address;
    private String currency;
    private double price;

    public Shop(String name, String address, String currency, double price) {
        this.name = name;
        this.address = address;
        this.currency = currency;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
