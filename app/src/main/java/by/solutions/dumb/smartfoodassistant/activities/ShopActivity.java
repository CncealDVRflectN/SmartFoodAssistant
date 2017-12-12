package by.solutions.dumb.smartfoodassistant.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Locale;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.Database;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ShopCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;


public class ShopActivity extends AppCompatActivity {

    //region Variables

    private ProductsFilter filter;
    private ListView productsView;
    private Cursor shop;
    private Database db;
    private String shopID;

    //endregion

    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        DatabasesManager.changeLanguageWithVersion(this, Locale.getDefault().getLanguage(), 1);
        db = DatabasesManager.getDatabase();
        shopID = getIntent().getStringExtra("shopID");
        shop = db.getShopByID(shopID);
        filter = new ProductsFilter();
        productsView = findViewById(R.id.products_list);

        productsView.setAdapter(new ShopCursorAdapter(this,
                shop.getString(shop.getColumnIndexOrThrow(ShopsTable.CURRENCY_COLUMN)),
                db.getShopPrices(shopID), R.layout.product_in_shop));

        actionBar = getSupportActionBar();
        actionBar.setTitle(shop.getString(shop.getColumnIndexOrThrow(ShopsTable.NAME_COLUMN)));
        actionBar.setSubtitle(shop.getString(shop.getColumnIndexOrThrow(ShopsTable.ADDRESS_COLUMN)));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;
        MenuItem searchItem;

        getMenuInflater().inflate(R.menu.shop_activity_toolbar_actions, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productsView.setAdapter(new ShopCursorAdapter(ShopActivity.this,
                        shop.getString(shop.getColumnIndexOrThrow(ShopsTable.CURRENCY_COLUMN)),
                        db.getShopPrices(shopID), R.layout.product_in_shop));
                //TODO: change getShopPrices to getFilteredShopPrices after merging
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion
}
