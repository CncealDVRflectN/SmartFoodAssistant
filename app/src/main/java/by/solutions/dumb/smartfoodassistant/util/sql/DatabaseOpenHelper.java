package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String SQL_ID = "_id";
    public static final String ID_COLUMN = "ID";
    public final String TABLE_NAME;
    public final String LANGUAGE;

    public DatabaseOpenHelper(Context context, String tableName, String language, int version) {
        super(context, tableName.trim() + "_" + language.trim().toUpperCase(), null, version);
        LANGUAGE = language.trim().toUpperCase();
        TABLE_NAME = tableName.trim().toUpperCase() + "_" + LANGUAGE;
    }
}
