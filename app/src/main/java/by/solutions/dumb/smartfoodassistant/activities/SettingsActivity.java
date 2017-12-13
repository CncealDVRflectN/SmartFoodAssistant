package by.solutions.dumb.smartfoodassistant.activities;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import by.solutions.dumb.smartfoodassistant.R;

public class SettingsActivity extends SecondaryActivity {

    private final static String LOG_TAG = "SettingsActivity";

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
            Preference button = findPreference(getString(R.string.preference_version_key));
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.d(LOG_TAG, "Update version clicked");
                    Toast.makeText(getActivity(), getString(R.string.version_toast_text) + " " +
                                    PreferenceManager.getDefaultSharedPreferences(
                                            getActivity()).getLong(getString(R.string.preference_version_key), 1),
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        //endregion
    }

    //endregion
}
