package by.solutions.dumb.smartfoodassistant.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Locale;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ShopCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ShopActivity extends SecondaryActivity {

    //region Variables

    private static final String LOG_TAG = "ShopActivity";

    private Disposable disposable;
    private ProductsFilter filter;
    private ListView productsView;
    private String shopID;

    //endregion

    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        showProgressDialog();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        DatabasesManager.changeLanguageWithVersion(this, Locale.getDefault().getLanguage(), 1);
        shopID = getIntent().getStringExtra("shopID");
        filter = new ProductsFilter();
        productsView = findViewById(R.id.products_list);

        disposable = DatabasesManager.getDatabase().getShopByID(shopID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(final Cursor cursorShop) {
                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(cursorShop.getString(cursorShop.getColumnIndexOrThrow(ShopsTable.NAME_COLUMN)));
                        actionBar.setSubtitle(cursorShop.getString(cursorShop.getColumnIndexOrThrow(ShopsTable.ADDRESS_COLUMN)));
                        DatabasesManager.getDatabase().getShopPrices(shopID)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<Cursor>() {
                                    @Override
                                    public void onNext(Cursor cursorPrices) {
                                        productsView.setAdapter(new ShopCursorAdapter(ShopActivity.this,
                                                cursorShop.getString(cursorShop.getColumnIndexOrThrow(ShopsTable.CURRENCY_COLUMN)),
                                                cursorPrices, R.layout.product_in_shop));
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
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
            public boolean onQueryTextChange(final String newText) {
                if (disposable != null) {
                    disposable.dispose();
                }
                disposable = DatabasesManager.getDatabase().getShopByID(shopID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Cursor>() {
                            @Override
                            public void onNext(final Cursor cursorShop) {
                                ActionBar actionBar = getSupportActionBar();
                                filter.name = newText;
                                actionBar.setTitle(cursorShop.getString(cursorShop.getColumnIndexOrThrow(ShopsTable.NAME_COLUMN)));
                                actionBar.setSubtitle(cursorShop.getString(cursorShop.getColumnIndexOrThrow(ShopsTable.ADDRESS_COLUMN)));
                                DatabasesManager.getDatabase().getFilteredShopPrices(shopID, filter)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeWith(new DisposableObserver<Cursor>() {
                                            @Override
                                            public void onNext(Cursor cursorPrices) {
                                                productsView.setAdapter(new ShopCursorAdapter(ShopActivity.this,
                                                        cursorShop.getString(cursorShop.getColumnIndexOrThrow(ShopsTable.CURRENCY_COLUMN)),
                                                        cursorPrices, R.layout.product_in_shop));
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e(LOG_TAG, e.getMessage());
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(LOG_TAG, e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //endregion
}
