package by.solutions.dumb.smartfoodassistant.util;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import by.solutions.dumb.smartfoodassistant.util.firebase.rest.api.FirebaseREST;

public class UpdatesChecker extends Service {
    private FirebaseREST firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        firebase = new FirebaseREST("https://smartfoodassistant.firebaseio.com");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JSONParser parser = new JSONParser();
        int currentVersion;
        try {
            currentVersion = (Integer) parser.parse(firebase.get("version"));
        } catch (ParseException e) {
            Log.e("UpdatesCheker", e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
