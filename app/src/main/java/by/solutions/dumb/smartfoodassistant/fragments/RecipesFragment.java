package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.BaseActivity;
import by.solutions.dumb.smartfoodassistant.activities.RecipeActivity;
import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.RecipesCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.RecipesTable;


public class RecipesFragment extends Fragment {

    //region Variables

    private static final String LOG_TAG = "RecipesFragment";

    private ListView recipesView;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity) getActivity()).showProgressDialog();
        View fragmentView = inflater.inflate(R.layout.fragment_recipes, container, false);

        recipesView = fragmentView.findViewById(R.id.recipes_list);
        recipesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                DatabasesManager.getDatabase().getAllRecipes(), R.layout.recipe));

        recipesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                Cursor recipe = (Cursor) adapterView.getItemAtPosition(i);
                String id = recipe.getString(recipe.getColumnIndex(RecipesTable.ID_COLUMN));

                intent.putExtra("recipeID", id);
                Log.d(LOG_TAG, id);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    //endregion

    public void resetFilter() {
        recipesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                DatabasesManager.getDatabase().getAllRecipes(), R.layout.recipe));
    }

    public void filter(RecipesFilter filter) {
        recipesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                DatabasesManager.getDatabase().getFilteredRecipes(filter), R.layout.recipe));
    }

    //region Getters


    //endregion


    //region Setters


    //endregion


    //region Private methods


    //endregion
}
