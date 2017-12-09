package by.solutions.dumb.smartfoodassistant.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.Database;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ShopCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;


public class ShopActivity extends AppCompatActivity {
    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database db = DatabasesManager.getDatabase();
        ActionBar actionBar;
        String shopID;
        Cursor shop;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        shopID = getIntent().getStringExtra("shopID");
        shop = db.getShopByID(shopID);

        ((ListView) findViewById(R.id.products_list))
                .setAdapter(new ShopCursorAdapter(this,
                        shop.getString(shop.getColumnIndexOrThrow(ShopsTable.CURRENCY_COLUMN)),
                        db.getShopPrices(shopID), R.layout.product));

        actionBar = getSupportActionBar();
        actionBar.setTitle(shop.getString(shop.getColumnIndexOrThrow(ShopsTable.NAME_COLUMN)));
        actionBar.setSubtitle(shop.getString(shop.getColumnIndexOrThrow(ShopsTable.ADDRESS_COLUMN)));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion
}
