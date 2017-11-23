package by.solutions.dumb.smartfoodassistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.adapters.ShopAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Shop;


public class ProductActivity extends AppCompatActivity {

    //region Variables

    private List<Shop> shops = new ArrayList<>();
    private ListView shopsView;
    private ActionBar actionBar;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ShopAdapter shopAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        testInitial();
        shopsView = findViewById(R.id.shops_list);
        shopAdapter = new ShopAdapter(this, R.layout.shop, shops);
        shopsView.setAdapter(shopAdapter);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent().getStringExtra("productName"));
        actionBar.setDisplayHomeAsUpEnabled(true);

        shopsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ProductActivity.this, ShopActivity.class);
                Shop shop = (Shop) adapterView.getItemAtPosition(i);

                intent.putExtra("shopName", shop.getName());
                intent.putExtra("shopAddress", shop.getAddress());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion


    //region Public methods

    private void testInitial() {
        shops.add(new Shop("Гипермаркет", "ул. Нигде 43", "USD", 2342));
        shops.add(new Shop("Супермаркет", "ул. Здесь 23", "USD", 4563));
        shops.add(new Shop("Магазин", "ул. Там 45", "USD", 4353));
    }

    //endregion
}
