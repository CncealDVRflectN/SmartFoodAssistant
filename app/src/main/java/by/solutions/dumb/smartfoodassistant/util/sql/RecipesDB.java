package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;
import android.database.Cursor;

import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;

public class RecipesDB extends Database<RecipesDBHelper> {
    private static final String LOG_TAG = "RecipesDB";

    public RecipesDB(Context context, String language, int version) {
        super(new RecipesDBHelper(context, language, version));
    }

    public Cursor getAllDataSortedByName() {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT * ");
        request.append("FROM ").append(HELPER.TABLE_NAME).append(" ");
        request.append("ORDER BY ").append(HELPER.NAME_COLUMN).append(" ASC");

        result = HELPER.getReadableDatabase().rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Cursor getFilteredData(RecipesFilter filter) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT * ");
        request.append("FROM ").append(HELPER.TABLE_NAME).append(" ");
        request.append("WHERE ").append(HELPER.NAME_COLUMN).append(" ");
        request.append("LIKE '%").append(filter.name).append("%' ");
        request.append("ORDER BY ").append(HELPER.NAME_COLUMN).append(" ASC");

        result = HELPER.getReadableDatabase().rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }
}
