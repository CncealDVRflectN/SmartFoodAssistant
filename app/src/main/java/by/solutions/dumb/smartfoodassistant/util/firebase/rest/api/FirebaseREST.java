package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class FirebaseREST {
    private final String LOG_TAG = "FirebaseREST";
    private final String DB_PATH;
    private String secureToken;

    public FirebaseREST(String dbPath) {
        this.DB_PATH = dbPath.trim();
    }

    public void updateSecureToken(String secureToken) {
        this.secureToken = secureToken.trim();
    }

    private String buildURL(String... pathArgs) {
        StringBuilder urlBuilder = new StringBuilder(DB_PATH);
        try {
            for (String pathElement : pathArgs) {
                urlBuilder.append("/");
                urlBuilder.append(URLEncoder.encode(pathElement, "UTF-8"));
            }
            if (pathArgs.length != 0) {
                urlBuilder.append(".json");
            }
            if (secureToken != null) {
                urlBuilder.append("?auth=");
                urlBuilder.append(secureToken);
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return urlBuilder.toString();
    }

    public String get(String... pathArgs) {
        String result = null;
        FirebaseGetTask getTask = new FirebaseGetTask(buildURL(pathArgs));
        getTask.execute();
        try {
            result = getTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(LOG_TAG, e.toString());
        }
        return result;
    }

    public void put(String dataJSON, String... pathArgs) {
        FirebasePutTask putTask = new FirebasePutTask(buildURL(pathArgs));
        putTask.execute(dataJSON);
    }

    public void post(String dataJSON, String... pathArgs) {
        FirebasePostTask postTask = new FirebasePostTask(buildURL(pathArgs));
        postTask.execute(dataJSON);
    }

    public void patch(String dataJSON, String... pathArgs) {
        FirebasePatchTask patchTask = new FirebasePatchTask(buildURL(pathArgs));
        patchTask.execute(dataJSON);
    }

    public void delete(String... pathArgs) {
        FirebaseDeleteTask deleteTask = new FirebaseDeleteTask(buildURL(pathArgs));
        deleteTask.execute();
    }
}
