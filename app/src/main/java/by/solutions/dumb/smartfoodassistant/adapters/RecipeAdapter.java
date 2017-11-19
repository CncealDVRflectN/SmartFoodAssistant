package by.solutions.dumb.smartfoodassistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.containers.Recipe;


public class RecipeAdapter extends ArrayAdapter<Recipe> implements Filterable {

    //region Variables

    private LayoutInflater inflater;
    private int layout;
    private List<Recipe> allRecipes;
    private List<Recipe> filteredRecipes;
    private Filter recipeFilter;

    //endregion


    //region Constructors

    public RecipeAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.allRecipes = recipes;
        this.filteredRecipes = recipes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        recipeFilter = new RecipeFilter();
    }

    //endregion


    //region ArrayAdapter methods

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.recipe_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(filteredRecipes.get(position).getName());

        return convertView;
    }

    static class  ViewHolder {
        TextView textView;
    }

    @Override
    public int getCount() {
        return filteredRecipes.size();
    }

    @Nullable
    @Override
    public Recipe getItem(int position) {
        return filteredRecipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return recipeFilter;
    }

    //endregion

    private class RecipeFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Recipe> list = allRecipes;

            int count = list.size();
            final List<Recipe> resultList = new ArrayList<>(count);

            Recipe filterableRecipe;

            for (int i = 0; i < count; i++) {
                filterableRecipe = list.get(i);
                if (filterableRecipe.getName().toLowerCase().contains(filterString)) {
                    resultList.add(filterableRecipe);
                }
            }

            results.values = resultList;
            results.count = resultList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredRecipes = (ArrayList<Recipe>) results.values;
            notifyDataSetChanged();
        }

    }

}
