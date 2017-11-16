package by.solutions.dumb.smartfoodassistant.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import by.solutions.dumb.smartfoodassistant.fragments.ProductsFragment;
import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.fragments.RecipesFragment;

public class MainActivity extends AppCompatActivity {

    //region Variables

    private int currentPageId;
    private FragmentManager fragmentManager;
    private ProductsFragment productsFragment;
    private RecipesFragment recipesFragment;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        productsFragment = new ProductsFragment();
        recipesFragment = new RecipesFragment();

        currentPageId = R.id.navigation_recipes;
        addFragment(R.id.main_fragment_container, recipesFragment);
        addFragment(R.id.main_fragment_container, productsFragment);
        hideFragment(productsFragment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recipes:
                        if (currentPageId != R.id.navigation_recipes) {
                            currentPageId = R.id.navigation_recipes;
                            changeFragments(recipesFragment,productsFragment);
                        }
                        return true;
                    case R.id.navigation_products:
                        if (currentPageId != R.id.navigation_products) {
                            currentPageId = R.id.navigation_products;
                            changeFragments(productsFragment, recipesFragment);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    //endregion


    //region Private methods

    private void addFragment(int containerId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerId, fragment);
        fragmentTransaction.commit();
    }

    private void hideFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    private void changeFragments(Fragment fragment1, Fragment fragment2) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.show(fragment1);
        fragmentTransaction.hide(fragment2);
        fragmentTransaction.commit();
    }

    //endregion
}
