package by.solutions.dumb.smartfoodassistant.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.IngredientsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.RecipesTable;

public class RecipeActivity extends AppCompatActivity {

    //region Variables

    private final static int INGREDIENT_FONT_SIZE = 18;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinearLayout recipeIngredientsContainer;
        ActionBar actionBar;
        TextView recipeDescriptionView;
        TextView tmpTextView;
        String recipeID;
        Cursor recipe;
        Cursor ingredients;
        int indexNameColumn;
        int indexMeasureColumn;
        int indexAmountColumn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        DatabasesManager.changeLanguageWithVersion(this, Locale.getDefault().getLanguage(), 1);
        recipeID = getIntent().getStringExtra("recipeID");
        recipe = DatabasesManager.getDatabase().getRecipeByID(recipeID);

        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        recipeDescriptionView = findViewById(R.id.recipe_description);

        actionBar = getSupportActionBar();
        actionBar.setTitle(recipe.getString(recipe.getColumnIndex(RecipesTable.NAME_COLUMN)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        recipeDescriptionView.setText(recipe.getString(recipe.getColumnIndex(RecipesTable.INSTRUCTION_COLUMN)));

        ingredients = DatabasesManager.getDatabase().getRecipeIngredientsByID(recipeID);
        indexNameColumn = ingredients.getColumnIndexOrThrow(ProductsTable.NAME_COLUMN);
        indexMeasureColumn = ingredients.getColumnIndexOrThrow(ProductsTable.MEASURE_COLUMN);
        indexAmountColumn = ingredients.getColumnIndexOrThrow(IngredientsTable.AMOUNT_COLUMN);


        while (!ingredients.isAfterLast()) {
            tmpTextView = new TextView(this);
            tmpTextView.setText(ingredients.getString(indexNameColumn));
            tmpTextView.setTextSize(INGREDIENT_FONT_SIZE);
            recipeIngredientsContainer.addView(tmpTextView);
            ingredients.moveToNext();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion
}
