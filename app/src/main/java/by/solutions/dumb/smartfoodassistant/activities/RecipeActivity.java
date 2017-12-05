package by.solutions.dumb.smartfoodassistant.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesDB;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesDBHelper;

public class RecipeActivity extends AppCompatActivity {

    //region Variables

    private final static int INGREDIENT_FONT_SIZE = 18;

    private ActionBar actionBar;
    private List<String> recipeIngredients = new ArrayList<>();

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LinearLayout recipeIngredientsContainer;
        TextView recipeDescriptionView;
        TextView tmpTextView;
        String recipeID;
        Cursor recipe;
        RecipesDB db = MainActivity.getDbManager().getRecipesDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeID = getIntent().getStringExtra("recipeID");
        recipe = db.getByID(recipeID);

        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        recipeDescriptionView = findViewById(R.id.recipe_description);
        testInitial();
        actionBar = getSupportActionBar();
        actionBar.setTitle(recipe.getString(recipe.getColumnIndex(RecipesDBHelper.NAME_COLUMN)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        recipeDescriptionView.setText(recipe.getString(recipe.getColumnIndex(RecipesDBHelper.INSTRUCTION_COLUMN)));

        for (String ingredient : recipeIngredients) {
            tmpTextView = new TextView(this);
            tmpTextView.setText(ingredient);
            tmpTextView.setTextSize(INGREDIENT_FONT_SIZE);
            recipeIngredientsContainer.addView(tmpTextView);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion


    //region Private methods

    private void testInitial() {
        recipeIngredients.add("Картоха");
        recipeIngredients.add("Картошка");
        recipeIngredients.add("Картофель");
    }

    //endregion
}
