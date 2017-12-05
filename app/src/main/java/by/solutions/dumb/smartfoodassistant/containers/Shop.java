package by.solutions.dumb.smartfoodassistant.containers;


public class Shop {

    //region Variables

    private String name;
    private String address;
    private String currency;
    private double price;

    //endregion


    //region Constructors

    public Shop(String name, String address, String currency, double price) {
        this.name = name;
        this.address = address;
        this.currency = currency;
        this.price = price;
    }

    //endregion


    //region Getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //endregion


    //region Setters

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    //endregion
}
