package by.solutions.dumb.smartfoodassistant.util.sql;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Database<T extends DatabaseOpenHelper> {
    protected SQLiteDatabase db;
    public final T HELPER;

    public Database(T helper) {
        HELPER = helper;
    }

    public int getItemCount() {
        Cursor cursor;
        int result;

        db = HELPER.getReadableDatabase();
        cursor = db.query(HELPER.TABLE_NAME, null, null, null, null, null, null);
        result = cursor.getCount();
        cursor.close();

        return result;
    }

    public Cursor getAllData() {
        Cursor result = db.query(HELPER.TABLE_NAME, null, null, null, null, null, null);
        result.moveToFirst();
        return result;
    }

    public Cursor getByID(String ID) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT * ");
        request.append("FROM ").append(HELPER.TABLE_NAME).append(" ");
        request.append("WHERE ").append(HELPER.ID_COLUMN).append(" = '").append(ID).append("'");

        result = HELPER.getReadableDatabase().rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public void close() {
        HELPER.close();
        db.close();
    }
}
