package by.solutions.dumb.smartfoodassistant;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.activities.ProductActivity;
import by.solutions.dumb.smartfoodassistant.adapters.ProductAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Product;


public class ProductsFragment extends Fragment {
    private List<Product> products = new ArrayList<>();
    private ListView productsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_fragment, container, false);
        AdapterView.OnItemClickListener itemListener;

        testInitial();
        productsList = view.findViewById(R.id.products_list);
        ProductAdapter productAdapter = new ProductAdapter(getActivity().getApplicationContext(), R.layout.product, products);
        productsList.setAdapter(productAdapter);
        itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                Product product = (Product) adapterView.getItemAtPosition(i);

                intent.putExtra("productName", product.getName());

                startActivity(intent);
            }
        };
        productsList.setOnItemClickListener(itemListener);

        return view;
    }

    private void testInitial() {
        products.add(new Product("Картоха", "USD", 2304));
        products.add(new Product("Картофель", "USD", 2364));
        products.add(new Product("Картошка", "USD", 3452));
    }
}
