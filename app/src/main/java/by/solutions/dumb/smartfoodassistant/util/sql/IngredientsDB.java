package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;

public class IngredientsDB extends Database<IngredientsDBHelper> {
    private static final String LOG_TAG = "IngredientsDB";

    public IngredientsDB(Context context, String recipeID, String language, int version) {
        super(new IngredientsDBHelper(context, recipeID, language, version));
    }
}
