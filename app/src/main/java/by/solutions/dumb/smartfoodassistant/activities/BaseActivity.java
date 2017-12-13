package by.solutions.dumb.smartfoodassistant.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import by.solutions.dumb.smartfoodassistant.R;

public class BaseActivity extends AppCompatActivity {

    //region Variables

    private static final String PREFERENCE_KEY_THEME_COLOR = "color_list";
    private static final String DEFAULT_THEME_COLOR = "0";

    private ProgressDialog progressDialog;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getStyleId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    //endregion


    //region Private methods

    private int getStyleId() {
        String color = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(PREFERENCE_KEY_THEME_COLOR, DEFAULT_THEME_COLOR);
        switch (color) {
            case "1":
                return R.style.FirstCustomAppTheme;
        }
        return R.style.DefaultAppTheme;
    }


    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //endregion
}
