package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.MainActivity;
import by.solutions.dumb.smartfoodassistant.activities.RecipeActivity;
import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesDBHelper;


public class FavoritesFragment extends Fragment {

    //region Variables

    private ListView favoritesView;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesView = fragmentView.findViewById(R.id.favorites_list);
        favoritesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getRecipesDB().getAllDataSortedByName(), R.layout.recipe));

        favoritesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                Cursor recipe = (Cursor) adapterView.getItemAtPosition(i);

                intent.putExtra("recipeID", recipe.getString(recipe.getColumnIndex(RecipesDBHelper.ID_COLUMN)));

                startActivity(intent);
            }
        });

        return fragmentView;
    }

    //endregion

    public void resetFilter() {
        favoritesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getRecipesDB().getAllDataSortedByName(), R.layout.recipe));
    }

    public void filter(RecipesFilter filter) {
        favoritesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getRecipesDB().getFilteredData(filter), R.layout.recipe));
    }

    //region Getters


    //endregion


    //region Setters


    //endregion


    //region Private methods


    //endregion
}
