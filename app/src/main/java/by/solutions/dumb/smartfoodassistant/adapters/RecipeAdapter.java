package by.solutions.dumb.smartfoodassistant.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.containers.Container;
import by.solutions.dumb.smartfoodassistant.util.ContainerListFilter;


public class RecipeAdapter extends ArrayAdapter<Container> implements RefreshableAdapter {

    //region Variables

    private LayoutInflater inflater;
    private int layout;
    private List<Container> recipes;
    private ContainerListFilter filter;

    //endregion


    //region Constructors

    public RecipeAdapter(Context context, int resource, List<Container> recipes) {
        super(context, resource, recipes);
        this.recipes = recipes;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        filter = new ContainerListFilter(this.recipes, this.recipes, this);
    }

    //endregion


    //region ArrayAdapter methods

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameView = convertView.findViewById(R.id.recipe_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameView.setText(recipes.get(position).getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Nullable
    @Override
    public Container getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    //endregion


    //region RefreshableAdapter

    @Override
    public void refreshData(List<Container> filteredData) {
        this.recipes = filteredData;
        notifyDataSetChanged();
    }

    //endregion


    //region Nested classes

    private static class ViewHolder {
        TextView nameView;
    }

    //endregion

}
