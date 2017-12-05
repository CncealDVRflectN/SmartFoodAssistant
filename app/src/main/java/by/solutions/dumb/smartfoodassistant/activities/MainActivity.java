package by.solutions.dumb.smartfoodassistant.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.fragments.ProductsFragment;
import by.solutions.dumb.smartfoodassistant.fragments.RecipesFragment;
import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;
import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;

public class MainActivity extends AppCompatActivity {

    //region Variables

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 1324;

    private FragmentManager fragmentManager;
    private ProductsFragment productsFragment;
    private RecipesFragment recipesFragment;
    private int currentPageId;

    private MenuItem searchItem;
    private MenuItem authItem;
    private ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;
//    private FloatingActionButton addFab;

    private ProductsFilter productsFilter;
    private RecipesFilter recipesFilter;

    private static DatabasesManager dbManager;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
//        addFab = findViewById(R.id.addFAB);

        dbManager = new DatabasesManager(this, "ru");
        fragmentManager = getFragmentManager();
        productsFragment = new ProductsFragment();
        recipesFragment = new RecipesFragment();
        productsFilter = new ProductsFilter();
        recipesFilter = new RecipesFilter();

        currentPageId = R.id.navigation_recipes;
        addFragment(R.id.main_fragment_container, recipesFragment);
        addFragment(R.id.main_fragment_container, productsFragment);
        hideFragment(productsFragment);
        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_recipes);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_recipes:
                        if (currentPageId != R.id.navigation_recipes) {
                            Log.d(TAG, "Recipes bottom button clicked");
                            currentPageId = R.id.navigation_recipes;
                            changeFragments(recipesFragment, productsFragment);
                            Log.d(TAG, "Recipes fragment showed");
                            productsFragment.resetFilter();
                            searchItem.collapseActionView();
                            actionBar.setTitle(R.string.title_recipes);
                        }
                        return true;
                    case R.id.navigation_products:
                        if (currentPageId != R.id.navigation_products) {
                            Log.d(TAG, "Products bottom button clicked");
                            currentPageId = R.id.navigation_products;
                            changeFragments(productsFragment, recipesFragment);
                            Log.d(TAG, "Products fragment showed");
                            recipesFragment.resetFilter();
                            searchItem.collapseActionView();
                            actionBar.setTitle(R.string.title_products);
                        }
                        return true;
                }
                return false;
            }
        });

//        addFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (currentPageId) {
//                    case R.id.navigation_recipes:
//                        startActivity(addRecipeIntent);
//                        return;
//                    case R.id.navigation_products:
//                        startActivity(addProductIntent);
//                }
//            }
//        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        System.out.println("+++++++++++++++++++++++++++++++++++++");
//        System.out.println("*******************************" + (bottomNavigationView.getTop() - bottomNavigationView.getBottom()));
//        bottomNavigationView.measure(0,0);
//        findViewById(R.id.main_fragment_container).setPadding(0,0, 0, bottomNavigationView.getTop() - bottomNavigationView.getBottom());
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SearchView searchView;

        getMenuInflater().inflate(R.menu.main_activity_toolbar_actions, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (currentPageId == R.id.navigation_recipes) {
                    recipesFilter.name = newText;
                    recipesFragment.filter(recipesFilter);
                } else if (currentPageId == R.id.navigation_products) {
                    productsFilter.name = newText;
                    productsFragment.filter(productsFilter);
                }
                return false;
            }
        });

        authItem = menu.findItem(R.id.action_user);
        updateUser();
        authItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivityForResult(new Intent(MainActivity.this, SignInActivity.class),
                        RC_SIGN_IN);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                updateUser();
            } else {
                Log.d(TAG, "SignInActivity incorrect result");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    private void updateUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            authItem.setIcon(R.drawable.ic_user_sign_in_white_24dp);
//            addFab.setVisibility(View.VISIBLE);
        } else {
            authItem.setIcon(R.drawable.ic_user_sign_out_white_24dp);
//            addFab.setVisibility(View.GONE);
        }
    }

    //endregion


    public static DatabasesManager getDbManager() {
        return dbManager;
    }
}
