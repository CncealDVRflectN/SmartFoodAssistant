package by.solutions.dumb.smartfoodassistant.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.adapters.ProductAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Container;
import by.solutions.dumb.smartfoodassistant.containers.Product;


public class ShopActivity extends AppCompatActivity {
    private List<Container> products = new ArrayList<>();
    private ListView productsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView shopName;
        TextView shopAddress;
        ProductAdapter productAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        testInitial();
        productsView = findViewById(R.id.products_list);
        shopName = findViewById(R.id.shop_name);
        shopAddress = findViewById(R.id.shop_address);
        productAdapter = new ProductAdapter(this, R.layout.product, products);
        productsView.setAdapter(productAdapter);
        shopName.setText(getIntent().getStringExtra("shopName"));
        shopAddress.setText(getIntent().getStringExtra("shopAddress"));
    }

    public void testInitial() {
        products.add(new Product("Картоха", "USD", 2304));
        products.add(new Product("Картофель", "USD", 2364));
        products.add(new Product("Картошка", "USD", 3452));
    }
}
