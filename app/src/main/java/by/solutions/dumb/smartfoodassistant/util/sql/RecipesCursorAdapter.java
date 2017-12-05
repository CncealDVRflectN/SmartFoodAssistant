package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import by.solutions.dumb.smartfoodassistant.R;

public class RecipesCursorAdapter extends CursorAdapter {
    private int layout;

    public RecipesCursorAdapter(Context context, Cursor cursor, int layout) {
        super(context, cursor, 0);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(this.layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.recipe_name))
                .setText(cursor.getString(cursor.getColumnIndexOrThrow(RecipesDBHelper.NAME_COLUMN)));
    }
}
