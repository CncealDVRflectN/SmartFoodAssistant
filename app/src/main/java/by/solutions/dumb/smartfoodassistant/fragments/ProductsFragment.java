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


public class ProductsFragment extends Fragment {

    //region Variables

    private static final String LOG_TAG = "ProductsFragment";

    private ListView productsView;

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
        productsView.setAdapter(new ProductsCursorAdapter(this.getActivity(),
                DatabasesManager.getDatabase().getAllProducts(), R.layout.product));

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

    //endregion

    public void resetFilter() {
        productsView.setAdapter(new ProductsCursorAdapter(this.getActivity(),
                DatabasesManager.getDatabase().getAllProducts(), R.layout.product));
    }

    public void filter(ProductsFilter filter) {
        productsView.setAdapter(new ProductsCursorAdapter(this.getActivity(),
                DatabasesManager.getDatabase().getFilteredProducts(filter), R.layout.product));
    }

    //region Getters

    //endregion


    //region Setters

    //endregion


    //region Private methods

    //endregion
}
