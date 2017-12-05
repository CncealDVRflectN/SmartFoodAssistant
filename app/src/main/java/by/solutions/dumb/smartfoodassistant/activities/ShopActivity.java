package by.solutions.dumb.smartfoodassistant.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.adapters.ProductAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Container;
import by.solutions.dumb.smartfoodassistant.containers.Product;


public class ShopActivity extends AppCompatActivity {

    //region Variables

    private List<Container> products = new ArrayList<>();
    private ListView productsView;
    private ActionBar actionBar;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProductAdapter productAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        testInitial();
        productAdapter = new ProductAdapter(this, R.layout.product, products);

        productsView = findViewById(R.id.products_list);
        productsView.setAdapter(productAdapter);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent().getStringExtra("shopName"));
        actionBar.setSubtitle(getIntent().getStringExtra("shopAddress"));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion


    //region Public methods

    public void testInitial() {
        products.add(new Product("Картоха", "USD", 2304));
        products.add(new Product("Картофель", "USD", 2364));
        products.add(new Product("Картошка", "USD", 3452));
    }

    //endregion
}
