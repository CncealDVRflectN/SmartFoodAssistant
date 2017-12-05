package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.MainActivity;
import by.solutions.dumb.smartfoodassistant.activities.ProductActivity;
import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.ProductsCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.ProductsDBHelper;


public class ProductsFragment extends Fragment {

    //region Variables

    private ListView productsView;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_products, container, false);

        productsView = fragmentView.findViewById(R.id.products_list);
        productsView.setAdapter(new ProductsCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getProductsDB().getAllDataSortedByName(), R.layout.product));

        productsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                Cursor product = (Cursor) adapterView.getItemAtPosition(i);

                intent.putExtra("productID", product.getString(product.getColumnIndex(ProductsDBHelper.ID_COLUMN)));

                startActivity(intent);
            }
        });

        return fragmentView;
    }

    //endregion

    public void resetFilter() {
        productsView.setAdapter(new ProductsCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getProductsDB().getAllDataSortedByName(), R.layout.product));
    }

    public void filter(ProductsFilter filter) {
        productsView.setAdapter(new ProductsCursorAdapter(this.getActivity(),
                MainActivity.getDbManager().getProductsDB().getFilteredData(filter), R.layout.product));
    }

    //region Getters

    //endregion


    //region Setters

    //endregion


    //region Private methods

    //endregion
}
