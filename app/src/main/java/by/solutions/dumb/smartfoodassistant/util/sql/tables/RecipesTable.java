package by.solutions.dumb.smartfoodassistant.util.sql.tables;


public class RecipesTable extends DatabaseTable {
    public static final String NAME_COLUMN = "NAME";
    public static final String TYPE_COLUMN = "TYPE";
    public static final String INSTRUCTION_COLUMN = "INSTRUCTION";
    public static final String CUISINE_COLUMN = "CUISINE";
    public static final String INGREDIENTS_COLUMN = "INGREDIENTS";

    public RecipesTable() {
        super("RECIPES");
    }
}
