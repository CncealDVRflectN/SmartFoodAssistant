package by.solutions.dumb.smartfoodassistant.util;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import by.solutions.dumb.smartfoodassistant.util.firebase.rest.api.FirebaseREST;

public class UpdatesChecker extends Service {

    private static final String LOG_TAG = "UpdatesCheckerService";

    private FirebaseREST firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        firebase = new FirebaseREST("https://smartfoodassistant.firebaseio.com");
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        JSONParser parser = new JSONParser();
        long currentVersion;
        try {
            currentVersion = (Long) parser.parse(firebase.get("version"));
            PreferenceManager.getDefaultSharedPreferences(this).edit().putLong("current_version", currentVersion).apply();
            Log.d(LOG_TAG, "Databases version: " + currentVersion);
        } catch (ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
