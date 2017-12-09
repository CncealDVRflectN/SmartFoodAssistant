package by.solutions.dumb.smartfoodassistant.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.Database;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ProductPricesCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;


public class ProductActivity extends AppCompatActivity {
    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Database db = DatabasesManager.getDatabase();
        ActionBar actionBar;
        ListView shopsView;
        String productID;
        Cursor product;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        productID = getIntent().getStringExtra("productID");
        product = db.getProductByID(productID);
        shopsView = findViewById(R.id.shops_list);

        shopsView.setAdapter(new ProductPricesCursorAdapter(this, db.getProductPrices(productID), R.layout.shop));

        actionBar = getSupportActionBar();
        actionBar.setTitle(product.getString(product.getColumnIndex(ProductsTable.NAME_COLUMN)));
        actionBar.setDisplayHomeAsUpEnabled(true);

        shopsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ProductActivity.this, ShopActivity.class);
                Cursor shop = (Cursor) adapterView.getItemAtPosition(i);

                intent.putExtra("shopID", shop.getString(shop.getColumnIndexOrThrow(ShopsTable.ID_COLUMN)));

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



    //endregion
}
