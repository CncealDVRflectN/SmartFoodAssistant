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

public class RecipesDBHelper extends DatabaseOpenHelper {
    private Context context;

    public static final String NAME_COLUMN = "NAME";
    public static final String TYPE_COLUMN = "TYPE";
    public static final String INSTRUCTION_COLUMN = "INSTRUCTION";
    public static final String CUISINE_COLUMN = "CUISINE";
    public static final String INGREDIENTS_COLUMN = "INGREDIENTS";

    public RecipesDBHelper(Context context, String language, int version) {
        super(context, "RECIPES", language, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FirebaseREST firebaseDB = new FirebaseREST("https://smartfoodassistant.firebaseio.com");
        StringBuilder requestBuilder = new StringBuilder();
        JSONParser parser = new JSONParser();
        JSONObject recipes;
        JSONObject recipe;
        ContentValues contentValues = new ContentValues();
        Set recipesID;
        Log.d(TABLE_NAME, "Create");

        requestBuilder.append("CREATE TABLE ").append(TABLE_NAME).append(" (");
        requestBuilder.append(SQL_ID).append(" integer primary key autoincrement, ");
        requestBuilder.append(ID_COLUMN).append(" text, ");
        requestBuilder.append(NAME_COLUMN).append(" text, ");
        requestBuilder.append(TYPE_COLUMN).append(" text, ");
        requestBuilder.append(INSTRUCTION_COLUMN).append(" text, ");
        requestBuilder.append(CUISINE_COLUMN).append(" text, ");
        requestBuilder.append(INGREDIENTS_COLUMN).append(" text);");

        db.execSQL(requestBuilder.toString());

        try {
            recipes = (JSONObject) parser.parse(firebaseDB.get(LANGUAGE.toLowerCase(), "recipes"));
            recipesID = recipes.keySet();
            for (Object recipeID : recipesID) {
                recipe = (JSONObject) recipes.get(recipeID);
                contentValues.clear();
                contentValues.put(ID_COLUMN, (String) recipeID);
                contentValues.put(NAME_COLUMN, ((String) recipe.get("name")).replaceAll("\"", ""));
                contentValues.put(TYPE_COLUMN, ((String) recipe.get("type")).replaceAll("\"", ""));
                contentValues.put(INSTRUCTION_COLUMN, ((String) recipe.get("instruction")).replaceAll("\"", ""));
                contentValues.put(CUISINE_COLUMN, ((String) recipe.get("cuisine")).replaceAll("\"", ""));
                contentValues.put(INGREDIENTS_COLUMN, "INGREDIENTS_" + recipeID + "_" + LANGUAGE);
                db.insert(TABLE_NAME, null, contentValues);

                new IngredientsDBHelper(context, (String) recipeID, LANGUAGE, 1);
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
