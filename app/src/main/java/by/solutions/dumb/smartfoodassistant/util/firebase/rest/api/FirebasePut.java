package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FirebasePut {
    private final String LOG_TAG = "FirebasePUT";
    private URL url;

    FirebasePut(String url) {
        try {
            this.url = new URL(url.trim());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    void putData(String... args) throws IOException {
        HttpURLConnection connection = null;
        PrintWriter writer;
        StringBuilder response = new StringBuilder("Firebase response: ");

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setReadTimeout(5000);
            connection.connect();

            writer = new PrintWriter(connection.getOutputStream());
            for (String arg : args) {
                writer.write(arg);
            }
            writer.close();

            response.append(connection.getResponseCode());
            response.append(", ");
            response.append(connection.getResponseMessage());
            Log.d(LOG_TAG, response.toString());

            if (connection.getResponseCode() != 200) {
                Log.e(LOG_TAG, "Can't put data on Firebase: " + url);
                throw new IOException("Can't put data on Firebase.");
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
