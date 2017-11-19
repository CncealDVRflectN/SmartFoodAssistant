package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.adapters.RecipeAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Container;
import by.solutions.dumb.smartfoodassistant.containers.Recipe;


public class RecipesFragment extends Fragment {

    //region Variables

    private List<Container> recipes = new ArrayList<>();
    private ListView recipesList;
    private RecipeAdapter adapter;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testInitial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipes_fragment, container, false);

        recipesList = view.findViewById(R.id.recipes_list);
        adapter = new RecipeAdapter(getActivity().getApplicationContext(), R.layout.recipe, recipes);
        recipesList.setAdapter(adapter);

        return view;
    }

    //endregion


    //region Getters

    public RecipeAdapter getAdapter() {
        return adapter;
    }

    //endregion


    //region Setters

    public void setAdapter(RecipeAdapter adapter) {
        this.adapter = adapter;
    }

    //endregion


    //region Private methods

    private void testInitial() {
        for (int i = 0; i < 5; i++) {
            recipes.add(new Recipe("Жареная картоха"));
            recipes.add(new Recipe("Вареный картофель"));
            recipes.add(new Recipe("Тушеная картошка"));
        }
    }

    //endregion
}
