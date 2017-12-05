package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.MainActivity;
import by.solutions.dumb.smartfoodassistant.adapters.RecipeAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Container;
import by.solutions.dumb.smartfoodassistant.containers.Recipe;
import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.ProductsDBHelper;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesDB;
import by.solutions.dumb.smartfoodassistant.util.sql.RecipesDBHelper;


public class RecipesFragment extends Fragment {

    //region Variables

    private ListView recipesView;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.recipes_fragment, container, false);

        recipesView = fragmentView.findViewById(R.id.recipes_list);
        recipesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getRecipesDB().getAllDataSortedByName(), R.layout.recipe));

        return fragmentView;
    }

    //endregion

    public void resetFilter() {
        recipesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getRecipesDB().getAllDataSortedByName(), R.layout.recipe));
    }

    public void filter(RecipesFilter filter) {
        recipesView.setAdapter(new RecipesCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getRecipesDB().getFilteredData(filter), R.layout.recipe));
    }

    //region Getters



    //endregion


    //region Setters



    //endregion


    //region Private methods



    //endregion
}
