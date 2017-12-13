package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.BaseActivity;
import by.solutions.dumb.smartfoodassistant.activities.ProductActivity;
import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ProductsCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ProductsFragment extends Fragment {

    //region Variables

    private static final String LOG_TAG = "ProductsFragment";

    private ListView productsView;
    private Disposable disposable;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity) getActivity()).showProgressDialog();
        View fragmentView = inflater.inflate(R.layout.fragment_products, container, false);

        productsView = fragmentView.findViewById(R.id.products_list);
        disposable = DatabasesManager.getDatabase().getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        productsView.setAdapter(new ProductsCursorAdapter(ProductsFragment.this.getActivity(),
                                cursor, R.layout.product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                    }
                });

        productsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                Cursor product = (Cursor) adapterView.getItemAtPosition(i);
                String id = product.getString(product.getColumnIndex(ProductsTable.ID_COLUMN));

                intent.putExtra("productID", id);
                Log.d(LOG_TAG, id);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    //endregion

    public void resetFilter() {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = DatabasesManager.getDatabase().getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        productsView.setAdapter(new ProductsCursorAdapter(ProductsFragment.this.getActivity(),
                                cursor, R.layout.product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                    }
                });
    }

    public void filter(final ProductsFilter filter) {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = DatabasesManager.getDatabase().getFilteredProducts(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        productsView.setAdapter(new ProductsCursorAdapter(ProductsFragment.this.getActivity(),
                                cursor, R.layout.product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ((BaseActivity) getActivity()).hideProgressDialog();
                    }
                });
    }

    //region Getters

    //endregion


    //region Setters

    //endregion


    //region Private methods

    //endregion
}
