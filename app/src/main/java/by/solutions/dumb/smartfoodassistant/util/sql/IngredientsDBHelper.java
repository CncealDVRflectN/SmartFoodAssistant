package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Set;

import by.solutions.dumb.smartfoodassistant.util.firebase.rest.api.FirebaseREST;

public class IngredientsDBHelper extends DatabaseOpenHelper {
    public static final String AMOUNT_COLUMN = "AMOUNT";
    public final String RECIPE_ID;

    public IngredientsDBHelper(Context context, String recipeID, String language, int version) {
        super(context, "INGREDIENTS_" + recipeID, language, version);
        RECIPE_ID = recipeID.trim();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FirebaseREST firebaseDB = new FirebaseREST("https://smartfoodassistant.firebaseio.com");
        StringBuilder requestBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONObject ingredients;
        ContentValues contentValues = new ContentValues();
        Set ingredientsID;
        Log.e(TABLE_NAME, "Create");

        requestBuilder.append("CREATE TABLE ").append(TABLE_NAME).append(" (");
        requestBuilder.append(SQL_ID).append(" integer primary key autoincrement, ");
        requestBuilder.append(ID_COLUMN).append(" text, ");
        requestBuilder.append(AMOUNT_COLUMN).append(" text);");

        db.execSQL(requestBuilder.toString());

        try {
            ingredients = (JSONObject) parser.parse(firebaseDB.get(LANGUAGE.toLowerCase(), "recipes", RECIPE_ID, "ingredients"));
            ingredientsID = ingredients.keySet();
            for (Object ingredientID : ingredientsID) {
                contentValues.clear();
                ingredientID = ((String) ingredientID).replaceAll("\"", "");
                contentValues.put(ID_COLUMN, (String) ingredientID);
                contentValues.put(AMOUNT_COLUMN, ((String) ingredients.get(ingredientID)).replaceAll("\"", ""));
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
