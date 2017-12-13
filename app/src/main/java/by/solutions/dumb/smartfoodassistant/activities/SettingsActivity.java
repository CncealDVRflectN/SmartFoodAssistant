package by.solutions.dumb.smartfoodassistant.activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import by.solutions.dumb.smartfoodassistant.R;

public class SettingsActivity extends SecondaryActivity {

    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.title_settings);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.settings_container, new SettingsFragment())
                .commit();
    }

    //endregion


    //region Nested classes

    public static class SettingsFragment extends PreferenceFragment {

        //region Fragment lifecycle

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        //endregion

    }

    //endregion
}
