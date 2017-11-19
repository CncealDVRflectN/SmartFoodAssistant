package by.solutions.dumb.smartfoodassistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.containers.Product;


public class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private List<Product> products;
    private int layout;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView nameView;
        TextView priceView;
        TextView currencyView;
        Product product;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }

        nameView = convertView.findViewById(R.id.product_name);
        priceView = convertView.findViewById(R.id.product_price);
        currencyView = convertView.findViewById(R.id.product_currency);
        product = products.get(position);

        nameView.setText(product.getName());
        priceView.setText(Double.toString(product.getPrice()));
        currencyView.setText(product.getCurrency());

        return convertView;
    }
}
