package by.solutions.dumb.smartfoodassistant.util.sql.tables;


public class IngredientsTable extends DatabaseTable {
    public static final String AMOUNT_COLUMN = "AMOUNT";
    public final String RECIPE_ID;

    public IngredientsTable(String recipeID) {
        super("INGREDIENTS_" + recipeID);
        RECIPE_ID = recipeID;
    }
}
