package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FirebaseDeleteTask extends AsyncTask<Void, Void, Void> {
    private final String LOG_TAG = "FirebaseDELETE";
    private URL url;

    FirebaseDeleteTask(String url) {
        try {
            this.url = new URL(url.trim());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    @Override
    protected Void doInBackground(Void... args) {
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder("Firebase response: ");

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");
            connection.setReadTimeout(10000);
            connection.connect();

            response.append(connection.getResponseCode());
            response.append(", ");
            response.append(connection.getResponseMessage());
            Log.d(LOG_TAG, response.toString());

            if (connection.getResponseCode() != 200) {
                Log.e(LOG_TAG, "Something went wrong with Firebase: " + url);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }
}
