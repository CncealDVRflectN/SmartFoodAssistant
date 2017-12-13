package by.solutions.dumb.smartfoodassistant.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Locale;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ProductPricesCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ProductActivity extends SecondaryActivity {
    private static final String LOG_TAG = "ProductActivity";

    private CompositeDisposable disposables;

    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ListView shopsView;
        String productID;

        showProgressDialog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        DatabasesManager.changeLanguageWithVersion(this, Locale.getDefault().getLanguage(), 1);

        disposables = new CompositeDisposable();
        productID = getIntent().getStringExtra("productID");
        shopsView = findViewById(R.id.shops_list);

        disposables.add(DatabasesManager.getDatabase().getProductByID(productID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex(ProductsTable.NAME_COLUMN)));
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
        disposables.add(DatabasesManager.getDatabase().getProductPrices(productID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        shopsView.setAdapter(new ProductPricesCursorAdapter(ProductActivity.this, cursor, R.layout.shop));
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        hideProgressDialog();
                    }
                }));

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
    protected void onDestroy() {
        if (disposables != null) {
            disposables.dispose();
        }
        super.onDestroy();
    }

    //endregion


    //region Public methods


    //endregion
}
