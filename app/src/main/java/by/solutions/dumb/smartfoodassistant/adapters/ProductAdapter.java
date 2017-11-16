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
import by.solutions.dumb.smartfoodassistant.containers.Product;


public class ProductAdapter extends ArrayAdapter<Product> {


    //region Variables

    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    //endregion


    //region Constructors

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    //endregion


    //region ArrayAdapter methods

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);
        TextView nameView = view.findViewById(R.id.product_name);
        TextView priceView = view.findViewById(R.id.product_price);
        TextView currencyView = view.findViewById(R.id.product_currency);
        Product product = products.get(position);

        nameView.setText(product.getName());
        priceView.setText(Double.toString(product.getPrice()));

        currencyView.setText(product.getCurrency());

        return view;
    }

    //endregion
}
