package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import by.solutions.dumb.smartfoodassistant.util.firebase.rest.api.FirebaseREST;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.DatabaseTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.IngredientsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.PricesTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.RecipesTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private final String DATABASE_NAME;

    public final ProductsTable PRODUCTS_TABLE;
    public final RecipesTable RECIPES_TABLE;
    public final PricesTable PRICES_TABLE;
    public final ShopsTable SHOPS_TABLE;
    public final Map<String, IngredientsTable> INGREDIENTS_TABLES;
    public final String LANGUAGE;

    public DatabaseOpenHelper(Context context, String databaseName, String language, int version) {
        super(context, databaseName + "_" + language.toUpperCase(), null, version);

        String recipeID;
        Cursor recipesID;

        DATABASE_NAME = databaseName + "_" + language.toUpperCase();
        PRODUCTS_TABLE = new ProductsTable();
        RECIPES_TABLE = new RecipesTable();
        PRICES_TABLE = new PricesTable();
        SHOPS_TABLE = new ShopsTable();
        INGREDIENTS_TABLES = new HashMap<>();
        LANGUAGE = language;

        recipesID = getReadableDatabase().rawQuery("SELECT " + RecipesTable.ID_COLUMN + " FROM " +
                RECIPES_TABLE.TABLE_NAME, null);
        recipesID.moveToFirst();
        while (!recipesID.isAfterLast()) {
            recipeID = recipesID.getString(recipesID.getColumnIndexOrThrow(RecipesTable.ID_COLUMN));
            INGREDIENTS_TABLES.put(recipeID, new IngredientsTable(recipeID));
            recipesID.moveToNext();
        }

        recipesID.close();
    }

    private void createProductsTable(SQLiteDatabase db, JSONObject products) {
        StringBuilder requestBuilder = new StringBuilder();
        JSONObject product;
        ContentValues contentValues = new ContentValues();
        Set productsID;

        Log.d(DATABASE_NAME, "Creating " + PRODUCTS_TABLE.TABLE_NAME);

        requestBuilder.append("CREATE TABLE ").append(PRODUCTS_TABLE.TABLE_NAME).append(" (")
                .append(DatabaseTable.SQL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DatabaseTable.ID_COLUMN).append(" TEXT, ")
                .append(ProductsTable.NAME_COLUMN).append(" TEXT, ")
                .append(ProductsTable.MEASURE_COLUMN).append(" TEXT);");

        db.execSQL(requestBuilder.toString());

        productsID = products.keySet();
        for (Object productID : productsID) {
            product = (JSONObject) products.get(productID);

            contentValues.clear();
            contentValues.put(DatabaseTable.ID_COLUMN, ((String) productID).replaceAll("\"", ""));
            contentValues.put(ProductsTable.NAME_COLUMN, ((String) product.get("name")).replaceAll("\"", ""));
            contentValues.put(ProductsTable.MEASURE_COLUMN, ((String) product.get("measure")).replaceAll("\"", ""));
            db.insert(PRODUCTS_TABLE.TABLE_NAME, null, contentValues);
        }
    }

    private void createRecipesTable(SQLiteDatabase db, JSONObject recipes) {
        StringBuilder requestBuilder = new StringBuilder();
        JSONObject recipe;
        ContentValues contentValues = new ContentValues();
        Set recipesID;

        Log.d(DATABASE_NAME, "Creating " + RECIPES_TABLE.TABLE_NAME);

        requestBuilder.append("CREATE TABLE ").append(RECIPES_TABLE.TABLE_NAME).append(" (")
                .append(DatabaseTable.SQL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DatabaseTable.ID_COLUMN).append(" TEXT, ")
                .append(RecipesTable.NAME_COLUMN).append(" TEXT, ")
                .append(RecipesTable.TYPE_COLUMN).append(" TEXT, ")
                .append(RecipesTable.INSTRUCTION_COLUMN).append(" TEXT, ")
                .append(RecipesTable.CUISINE_COLUMN).append(" TEXT, ")
                .append(RecipesTable.INGREDIENTS_COLUMN).append(" TEXT);");

        db.execSQL(requestBuilder.toString());

        recipesID = recipes.keySet();
        for (Object recipeID : recipesID) {
            recipe = (JSONObject) recipes.get(recipeID);

            contentValues.clear();
            contentValues.put(DatabaseTable.ID_COLUMN, (String) recipeID);
            contentValues.put(RecipesTable.NAME_COLUMN, ((String) recipe.get("name")).replaceAll("\"", ""));
            contentValues.put(RecipesTable.TYPE_COLUMN, ((String) recipe.get("type")).replaceAll("\"", ""));
            contentValues.put(RecipesTable.INSTRUCTION_COLUMN, ((String) recipe.get("instruction")).replaceAll("\"", ""));
            contentValues.put(RecipesTable.CUISINE_COLUMN, ((String) recipe.get("cuisine")).replaceAll("\"", ""));
            contentValues.put(RecipesTable.INGREDIENTS_COLUMN, "INGREDIENTS_" + recipeID + "_" + LANGUAGE);
            db.insert(RECIPES_TABLE.TABLE_NAME, null, contentValues);

            createIngredientsTable(db, (String) recipeID, (JSONObject) recipe.get("ingredients"));
        }
    }

    private void createIngredientsTable(SQLiteDatabase db, String recipeID, JSONObject ingredients) {
        StringBuilder requestBuilder = new StringBuilder();
        ContentValues contentValues = new ContentValues();
        IngredientsTable ingredientsTable = new IngredientsTable(recipeID);
        Set ingredientsID;

        Log.d(DATABASE_NAME, "Creating " + ingredientsTable.TABLE_NAME);

        requestBuilder.append("CREATE TABLE ").append(ingredientsTable.TABLE_NAME).append(" (")
                .append(DatabaseTable.SQL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DatabaseTable.ID_COLUMN).append(" TEXT, ")
                .append(IngredientsTable.AMOUNT_COLUMN).append(" REAL);");

        db.execSQL(requestBuilder.toString());

        ingredientsID = ingredients.keySet();
        for (Object ingredientID : ingredientsID) {
            ingredientID = ((String) ingredientID).replaceAll("\"", "");

            contentValues.clear();
            contentValues.put(DatabaseTable.ID_COLUMN, (String) ingredientID);
            contentValues.put(IngredientsTable.AMOUNT_COLUMN, (Double) ingredients.get(ingredientID));
            db.insert(ingredientsTable.TABLE_NAME, null, contentValues);
        }
    }

    private void createShopsTable(SQLiteDatabase db, JSONObject shops) {
        StringBuilder requestBuilder = new StringBuilder();
        JSONObject shop;
        JSONObject addressLocal;
        ContentValues contentValues = new ContentValues();
        Set shopsID;

        Log.d(DATABASE_NAME, "Creating " + SHOPS_TABLE.TABLE_NAME);

        requestBuilder.append("CREATE TABLE ").append(SHOPS_TABLE.TABLE_NAME).append(" (")
                .append(DatabaseTable.SQL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DatabaseTable.ID_COLUMN).append(" TEXT, ")
                .append(ShopsTable.NAME_COLUMN).append(" TEXT, ")
                .append(ShopsTable.ADDRESS_COLUMN).append(" TEXT, ")
                .append(ShopsTable.CURRENCY_COLUMN).append(" TEXT);");

        db.execSQL(requestBuilder.toString());

        shopsID = shops.keySet();
        for (Object shopID : shopsID) {
            shop = (JSONObject) shops.get(shopID);
            addressLocal = (JSONObject) shop.get(LANGUAGE.toLowerCase());
            shopID = ((String) shopID).replaceAll("\"", "");

            contentValues.clear();
            contentValues.put(DatabaseTable.ID_COLUMN, (String) shopID);
            contentValues.put(ShopsTable.NAME_COLUMN, ((String) shop.get("name")).replaceAll("\"", ""));
            contentValues.put(ShopsTable.CURRENCY_COLUMN, ((String) shop.get("currency")).replaceAll("\"", ""));
            contentValues.put(ShopsTable.ADDRESS_COLUMN, ((String) addressLocal.get("address")).replaceAll("\"", ""));
            db.insert(SHOPS_TABLE.TABLE_NAME, null, contentValues);
        }
    }

    private void createPricesTable(SQLiteDatabase db, JSONObject shops) {
        StringBuilder requestBuilder = new StringBuilder();
        JSONObject prices;
        ContentValues contentValues = new ContentValues();
        Set shopsID;
        Set productsID;

        Log.d(DATABASE_NAME, "Creating " + PRICES_TABLE.TABLE_NAME);

        requestBuilder.append("CREATE TABLE ").append(PRICES_TABLE.TABLE_NAME).append(" (")
                .append(DatabaseTable.SQL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DatabaseTable.ID_COLUMN).append(" TEXT, ")
                .append(PricesTable.SHOP_ID_COLUMN).append(" TEXT, ")
                .append(PricesTable.PRICE_COLUMN).append(" REAL);");

        db.execSQL(requestBuilder.toString());

        shopsID = shops.keySet();
        for (Object shopID : shopsID) {
            shopID = ((String) shopID).replaceAll("\"", "");
            prices = (JSONObject) ((JSONObject) shops.get(shopID)).get("prices");
            productsID = prices.keySet();
            for (Object productID : productsID) {
                productID = ((String) productID).replaceAll("\"", "");

                contentValues.clear();
                contentValues.put(DatabaseTable.ID_COLUMN, (String) productID);
                contentValues.put(PricesTable.SHOP_ID_COLUMN, (String) shopID);
                contentValues.put(PricesTable.PRICE_COLUMN, (Double) prices.get(productID));
                db.insert(PRICES_TABLE.TABLE_NAME, null, contentValues);
            }
        }

    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        FirebaseREST firebaseDB = new FirebaseREST("https://smartfoodassistant.firebaseio.com");
        JSONParser parser = new JSONParser();
        JSONObject shops;

        try {
            shops = (JSONObject) parser.parse(firebaseDB.get("shops"));

            createProductsTable(db, (JSONObject) parser.parse(firebaseDB.get(LANGUAGE.toLowerCase(), "products")));
            createRecipesTable(db, (JSONObject) parser.parse(firebaseDB.get(LANGUAGE.toLowerCase(), "recipes")));
            createShopsTable(db, shops);
            createPricesTable(db, shops);
        } catch (ParseException e) {
            Log.e(DATABASE_NAME, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRICES_TABLE.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SHOPS_TABLE.TABLE_NAME);
        for (String id : INGREDIENTS_TABLES.keySet()) {
            db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_TABLES.get(id).TABLE_NAME);
        }
        onCreate(db);
    }
}
