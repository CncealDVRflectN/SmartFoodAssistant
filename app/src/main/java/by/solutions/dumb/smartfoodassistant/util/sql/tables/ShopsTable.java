package by.solutions.dumb.smartfoodassistant.util.sql.tables;


public class ShopsTable extends DatabaseTable {
    public static final String NAME_COLUMN = "NAME";
    public static final String ADDRESS_COLUMN = "ADDRESS";
    public static final String CURRENCY_COLUMN = "CURRENCY";

    public ShopsTable() {
        super("SHOPS");
    }
}
