package by.solutions.dumb.smartfoodassistant.util.sql.tables;


public abstract class DatabaseTable {
    public static final String SQL_ID = "_id";
    public static final String ID_COLUMN = "ID";
    public final String TABLE_NAME;

    public DatabaseTable(String tableName) {
        TABLE_NAME = "[" + tableName.trim() + "]";
    }
}
