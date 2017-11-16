package by.solutions.dumb.smartfoodassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import by.solutions.dumb.smartfoodassistant.adapters.ProductAdapter;
import by.solutions.dumb.smartfoodassistant.containers.Product;

public class MainActivity extends AppCompatActivity {

    //region Private variables

    private int currentPageId;

    private Toast toast;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_recipes:
                            if (currentPageId != R.id.navigation_recipes) {
                                currentPageId = R.id.navigation_recipes;
                                toast.setText("recipes");
                                toast.show();
                            }
                            return true;
                        case R.id.navigation_products:
                            if (currentPageId != R.id.navigation_products) {
                                currentPageId = R.id.navigation_products;
                                toast.setText("products");
                                toast.show();
                            }
                            return true;
                    }
                    return false;
                }
            };

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        currentPageId = R.id.navigation_recipes;
    }

    //endregion
}
