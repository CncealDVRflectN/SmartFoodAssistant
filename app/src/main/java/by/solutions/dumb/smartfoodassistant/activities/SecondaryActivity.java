package by.solutions.dumb.smartfoodassistant.activities;

import android.os.Bundle;

import by.solutions.dumb.smartfoodassistant.R;

public class SecondaryActivity extends BaseActivity {

    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion
}
