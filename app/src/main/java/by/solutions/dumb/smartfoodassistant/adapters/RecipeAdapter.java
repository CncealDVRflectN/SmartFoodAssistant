package by.solutions.dumb.smartfoodassistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.containers.Recipe;


public class RecipeAdapter extends ArrayAdapter<Recipe> {

    //region Variables

    private LayoutInflater inflater;
    private int layout;
    private List<Recipe> recipes;

    //endregion


    //region Constructors

    public RecipeAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.recipes = recipes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    //endregion


    //region ArrayAdapter methods

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        TextView nameView = view.findViewById(R.id.recipe_name);
        Recipe recipe = recipes.get(position);

        nameView.setText(recipe.getName());

        return view;
    }

    //endregion
}
