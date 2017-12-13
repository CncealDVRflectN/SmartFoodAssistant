package by.solutions.dumb.smartfoodassistant.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.IngredientsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.RecipesTable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecipeActivity extends SecondaryActivity {

    //region Variables

    private static final String LOG_TAG = "RecipeActivity";
    private final static int INGREDIENT_FONT_SIZE = 18;

    private CompositeDisposable disposables;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LinearLayout recipeIngredientsContainer;
        final TextView recipeDescriptionView;
        String recipeID;

        showProgressDialog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        DatabasesManager.changeLanguageWithVersion(this, Locale.getDefault().getLanguage(), 1);

        disposables = new CompositeDisposable();
        recipeID = getIntent().getStringExtra("recipeID");

        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        recipeDescriptionView = findViewById(R.id.recipe_description);

        disposables.add(DatabasesManager.getDatabase().getRecipeByID(recipeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex(RecipesTable.NAME_COLUMN)));
                        recipeDescriptionView.setText(cursor.getString(cursor.getColumnIndex(RecipesTable.INSTRUCTION_COLUMN)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        disposables.add(DatabasesManager.getDatabase().getRecipeIngredientsByID(recipeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        View tmpIngredientView;
                        int indexNameColumn = cursor.getColumnIndexOrThrow(ProductsTable.NAME_COLUMN);
                        int indexAmountColumn = cursor.getColumnIndexOrThrow(IngredientsTable.AMOUNT_COLUMN);
                        int indexMeasureColumn = cursor.getColumnIndexOrThrow(ProductsTable.MEASURE_COLUMN);

                        while (!cursor.isAfterLast()) {
                            tmpIngredientView = getLayoutInflater().inflate(R.layout.ingredient, null);
                            fillIngredientInfo((TextView) tmpIngredientView.findViewById(R.id.ingredient_name),
                                    cursor.getString(indexNameColumn));
                            fillIngredientInfo((TextView) tmpIngredientView.findViewById(R.id.ingredient_amount),
                                    cursor.getString(indexAmountColumn));
                            fillIngredientInfo((TextView) tmpIngredientView.findViewById(R.id.ingredient_measure),
                                    cursor.getString(indexMeasureColumn));
                            recipeIngredientsContainer.addView(tmpIngredientView);
                            cursor.moveToNext();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        hideProgressDialog();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        if (disposables != null) {
            disposables.dispose();
        }
        super.onDestroy();
    }

    //endregion

    private void fillIngredientInfo(TextView textView, String text) {
        textView.setText(text);
        textView.setTextSize(INGREDIENT_FONT_SIZE);
    }

}
