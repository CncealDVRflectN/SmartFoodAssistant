package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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

    private String buildURL(List<String> pathArgs) {
        StringBuilder urlBuilder = new StringBuilder(DB_PATH);
        try {
            for (String pathElement : pathArgs) {
                urlBuilder.append("/");
                urlBuilder.append(URLEncoder.encode(pathElement, "UTF-8"));
            }
            if (pathArgs.size() != 0) {
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

    public String get(List<String> pathArgs) {
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

    public void put(String dataJSON, List<String> pathArgs) {
        FirebasePutTask putTask = new FirebasePutTask(buildURL(pathArgs));
        putTask.execute(dataJSON);
    }

    public void post(String dataJSON, String... pathArgs) {
        FirebasePostTask postTask = new FirebasePostTask(buildURL(pathArgs));
        postTask.execute(dataJSON);
    }

    public void post(String dataJSON, List<String> pathArgs) {
        FirebasePostTask postTask = new FirebasePostTask(buildURL(pathArgs));
        postTask.execute(dataJSON);
    }

    public void patch(String dataJSON, String... pathArgs) {
        FirebasePatchTask patchTask = new FirebasePatchTask(buildURL(pathArgs));
        patchTask.execute(dataJSON);
    }

    public void patch(String dataJSON, List<String> pathArgs) {
        FirebasePatchTask patchTask = new FirebasePatchTask(buildURL(pathArgs));
        patchTask.execute(dataJSON);
    }

    public void delete(String... pathArgs) {
        FirebaseDeleteTask deleteTask = new FirebaseDeleteTask(buildURL(pathArgs));
        deleteTask.execute();
    }

    public void delete(List<String> pathArgs) {
        FirebaseDeleteTask deleteTask = new FirebaseDeleteTask(buildURL(pathArgs));
        deleteTask.execute();
    }
}
