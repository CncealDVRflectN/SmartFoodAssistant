package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;

public class DatabasesManager {
    private ProductsDB productsDB;
    private RecipesDB recipesDB;
    private Context context;
    private String language;

    public DatabasesManager(Context context, String language) {
        productsDB = new ProductsDB(context, language, 1);
        recipesDB = new RecipesDB(context, language, 1);
        this.context = context;
    }

    public void changeLanguage(String language) {
        productsDB = new ProductsDB(context, language, 1);
        recipesDB = new RecipesDB(context, language, 1);
        this.language = language;
    }

    public ProductsDB getProductsDB() {
        return productsDB;
    }

    public RecipesDB getRecipesDB() {
        return recipesDB;
    }

    public String getLanguage() {
        return language;
    }
}
