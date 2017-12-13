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

    public Observable<String> get(final String... pathArgs) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(new FirebaseGet(buildURL(pathArgs)).getData());
                e.onComplete();
            }
        });
    }

    public Observable<String> get(final List<String> pathArgs) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(new FirebaseGet(buildURL(pathArgs)).getData());
                e.onComplete();
            }
        });
    }

    public Completable put(final String dataJSON, final String... pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebasePut(buildURL(pathArgs)).putData(dataJSON);
                e.onComplete();
            }
        });
    }

    public Completable put(final String dataJSON, final List<String> pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebasePut(buildURL(pathArgs)).putData(dataJSON);
                e.onComplete();
            }
        });
    }

    public Completable post(final String dataJSON, final String... pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebasePost(buildURL(pathArgs)).postData(dataJSON);
                e.onComplete();
            }
        });
    }

    public Completable post(final String dataJSON, final List<String> pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebasePost(buildURL(pathArgs)).postData(dataJSON);
                e.onComplete();
            }
        });
    }

    public Completable patch(final String dataJSON, final String... pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebasePatch(buildURL(pathArgs)).patchData(dataJSON);
                e.onComplete();
            }
        });
    }

    public Completable patch(final String dataJSON, final List<String> pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebasePatch(buildURL(pathArgs)).patchData(dataJSON);
                e.onComplete();
            }
        });
    }

    public Completable delete(final String... pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebaseDelete(buildURL(pathArgs)).deleteData();
                e.onComplete();
            }
        });
    }

    public Completable delete(final List<String> pathArgs) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                new FirebaseDelete(buildURL(pathArgs)).deleteData();
                e.onComplete();
            }
        });
    }
}
