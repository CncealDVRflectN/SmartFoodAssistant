package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;
import android.database.Cursor;

import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;

public class ProductsDB extends Database<ProductsDBHelper> {
    private static final String LOG_TAG = "ProductsDB";

    public ProductsDB(Context context, String language, int version) {
        super(new ProductsDBHelper(context, language, version));
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

    public Cursor getFilteredData(ProductsFilter filter) {
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
