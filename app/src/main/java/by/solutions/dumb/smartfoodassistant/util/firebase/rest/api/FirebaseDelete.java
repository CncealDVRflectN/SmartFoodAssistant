package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FirebaseDelete {
    private final String LOG_TAG = "FirebaseDELETE";
    private URL url;

    FirebaseDelete(String url) {
        try {
            this.url = new URL(url.trim());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    void deleteData() throws IOException {
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder("Firebase response: ");

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");
            connection.setReadTimeout(5000);
            connection.connect();

            response.append(connection.getResponseCode());
            response.append(", ");
            response.append(connection.getResponseMessage());
            Log.d(LOG_TAG, response.toString());

            if (connection.getResponseCode() != 200) {
                Log.e(LOG_TAG, "Can't delete data on Firebase: " + url);
                throw new IOException("Can't delete data on Firebase.");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
