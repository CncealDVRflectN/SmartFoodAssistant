package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.adapters.ProductAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Product;


public class ProductsFragment extends Fragment {

    //region Variables

    private List<Product> products = new ArrayList<>();
    private ListView productsList;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testInitial();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_fragment, container, false);

        productsList = view.findViewById(R.id.products_list);
        ProductAdapter productAdapter = new ProductAdapter(getActivity().getApplicationContext(), R.layout.product, products);
        productsList.setAdapter(productAdapter);

        return view;
    }

    //endregion


    //region Private methods

    private void testInitial() {
        for (int i = 0; i < 5; i++) {
            products.add(new Product("Картоха", "USD", 2304));
            products.add(new Product("Картофель", "USD", 2364));
            products.add(new Product("Картошка", "USD", 3452));
        }
    }

    //endregion
}
