package by.solutions.dumb.smartfoodassistant.util.sql.adapters;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.PricesTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;

public class ProductPricesCursorAdapter extends CursorAdapter {
    private int layout;

    public ProductPricesCursorAdapter(Context context, Cursor cursor, int layout) {
        super(context, cursor, layout);
        this.layout = layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(this.layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.shop_name))
                .setText(cursor.getString(cursor.getColumnIndexOrThrow(ShopsTable.NAME_COLUMN)));
        ((TextView) view.findViewById(R.id.shop_address))
                .setText(cursor.getString(cursor.getColumnIndexOrThrow(ShopsTable.ADDRESS_COLUMN)));
        ((TextView) view.findViewById(R.id.product_currency))
                .setText(cursor.getString(cursor.getColumnIndexOrThrow(ShopsTable.CURRENCY_COLUMN)));
        ((TextView) view.findViewById(R.id.product_price))
                .setText(String.format("%.2f", cursor.getFloat(cursor.getColumnIndexOrThrow(PricesTable.PRICE_COLUMN))));
    }
}
