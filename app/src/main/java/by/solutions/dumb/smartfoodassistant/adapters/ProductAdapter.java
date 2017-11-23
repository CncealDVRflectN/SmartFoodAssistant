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
import by.solutions.dumb.smartfoodassistant.containers.Product;
import by.solutions.dumb.smartfoodassistant.util.ContainerListFilter;


public class ProductAdapter extends ArrayAdapter<Container> implements RefreshableAdapter {


    //region Variables

    private LayoutInflater inflater;

    private List<Container> products;
    private ContainerListFilter filter;

    private int layout;

    //endregion


    //region Constructors

    public ProductAdapter(Context context, int resource, List<Container> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        filter = new ContainerListFilter(this.products, this.products, this);
    }

    //endregion


    //region ArrayAdapter methods

    @Override
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
        product = (Product) products.get(position);

        nameView.setText(product.getName());
        priceView.setText(Double.toString(product.getPrice()));
        currencyView.setText(product.getCurrency());

        return convertView;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Nullable
    @Override
    public Container getItem(int position) {
        return products.get(position);
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
        this.products = filteredData;
        notifyDataSetChanged();
    }

    //endregion


    //region Nested classes


    private static class ViewHolder {
        TextView nameView;
        TextView priceView;
        TextView currencyView;
    }

    //endregion
}
