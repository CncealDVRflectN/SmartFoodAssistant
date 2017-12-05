package by.solutions.dumb.smartfoodassistant.containers;

public class Product implements Container {

    //region Variables

    private String name;
    private String currency;
    private double price;

    //endregion


    //region Constructors

    public Product(String name, String currency, double price) {
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

    //endregion


    //region Getters

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    //endregion


    //region Setters

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
