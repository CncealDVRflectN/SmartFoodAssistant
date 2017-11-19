package by.solutions.dumb.smartfoodassistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.containers.Shop;


public class ShopAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private List<Shop> shops;
    private int layout;

    public ShopAdapter(Context context, int resource, List<Shop> shops) {
        super(context, resource, shops);
        this.shops = shops;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView nameView;
        TextView addressView;
        TextView priceView;
        TextView currencyView;
        Shop shop;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }

        nameView = convertView.findViewById(R.id.shop_name);
        addressView = convertView.findViewById(R.id.shop_address);
        priceView = convertView.findViewById(R.id.product_price);
        currencyView = convertView.findViewById(R.id.product_currency);
        shop = shops.get(position);

        nameView.setText(shop.getName());
        addressView.setText(shop.getAddress());
        priceView.setText(Double.toString(shop.getPrice()));
        currencyView.setText(shop.getCurrency());

        return convertView;
    }
}
