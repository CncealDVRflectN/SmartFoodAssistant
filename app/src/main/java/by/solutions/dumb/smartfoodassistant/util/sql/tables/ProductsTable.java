package by.solutions.dumb.smartfoodassistant.util.sql.tables;


public class ProductsTable extends DatabaseTable {
    public static final String NAME_COLUMN = "NAME";
    public static final String MEASURE_COLUMN = "MEASURE";

    public ProductsTable() {
        super("PRODUCTS");
    }
}
