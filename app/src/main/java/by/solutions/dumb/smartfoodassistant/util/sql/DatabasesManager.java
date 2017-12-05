package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;

public class DatabasesManager {
    private static ProductsDB productsDB;
    private static RecipesDB recipesDB;
    private static String currentLanguage;
    private static int currentVersion;

    private DatabasesManager() {
    }

    public static void changeLanguage(Context context, String language) {
        if (currentLanguage == null || !currentLanguage.equalsIgnoreCase(language.trim())) {
            if (productsDB != null && recipesDB != null) {
                productsDB.close();
                recipesDB.close();
            }
            productsDB = new ProductsDB(context, language, 1);
            recipesDB = new RecipesDB(context, language, 1);
            currentLanguage = language.trim();
        }
    }

    public static void changeVersion(Context context, int version) {
        if (currentVersion != version || currentVersion == 0) {
            if (productsDB != null && recipesDB != null) {
                productsDB.close();
                recipesDB.close();
            }
            productsDB = new ProductsDB(context, currentLanguage, version);
            recipesDB = new RecipesDB(context, currentLanguage, version);
            currentVersion = version;
        }
    }

    public static void changeLanguageWithVersion(Context context, String language, int version) {
        if (currentLanguage == null || !currentLanguage.equalsIgnoreCase(language.trim()) ||
                currentVersion != version || currentVersion == 0) {
            if (productsDB != null && recipesDB != null) {
                productsDB.close();
                recipesDB.close();
            }
            productsDB = new ProductsDB(context, language, version);
            recipesDB = new RecipesDB(context, language, version);
            currentLanguage = language.trim();
            currentVersion = version;
        }
    }

    public static ProductsDB getProductsDB() {
        return productsDB;
    }

    public static RecipesDB getRecipesDB() {
        return recipesDB;
    }

    public static String getLanguage() {
        return currentLanguage;
    }

    public static int getVersion() {
        return currentVersion;
    }
}
