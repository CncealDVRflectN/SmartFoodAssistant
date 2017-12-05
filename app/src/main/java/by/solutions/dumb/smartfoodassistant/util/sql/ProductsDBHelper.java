package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Set;

import by.solutions.dumb.smartfoodassistant.util.firebase.rest.api.FirebaseREST;

public class ProductsDBHelper extends DatabaseOpenHelper {
    public static final String NAME_COLUMN = "NAME";
    public static final String MEASURE_COLUMN = "MEASURE";

    public ProductsDBHelper(Context context, String language, int version) {
        super(context, "PRODUCTS", language, version);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FirebaseREST firebaseDB = new FirebaseREST("https://smartfoodassistant.firebaseio.com");
        StringBuilder requestBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONObject products;
        JSONObject product;
        ContentValues contentValues = new ContentValues();
        Set productsID;

        Log.d(TABLE_NAME, "Create");

        requestBuilder.append("CREATE TABLE ").append(TABLE_NAME).append(" (");
        requestBuilder.append(SQL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        requestBuilder.append(ID_COLUMN).append(" TEXT, ");
        requestBuilder.append(NAME_COLUMN).append(" TEXT, ");
        requestBuilder.append(MEASURE_COLUMN).append(" TEXT);");

        db.execSQL(requestBuilder.toString());

        try {
            products = (JSONObject) parser.parse(firebaseDB.get(LANGUAGE.toLowerCase(), "products"));
            productsID = products.keySet();
            for (Object productID : productsID) {
                product = (JSONObject) products.get(productID);
                contentValues.clear();
                contentValues.put(ID_COLUMN, (String) productID);
                contentValues.put(NAME_COLUMN, ((String) product.get("name")).replaceAll("\"", ""));
                contentValues.put(MEASURE_COLUMN, ((String) product.get("measure")).replaceAll("\"", ""));
                db.insert(TABLE_NAME, null, contentValues);
            }
        } catch (ParseException e) {
            Log.e(TABLE_NAME, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TABLE_NAME, "Update");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
