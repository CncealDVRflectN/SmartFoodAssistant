package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FirebasePostTask extends AsyncTask<String, Void, Void> {
    private final String LOG_TAG = "FirebasePOST";
    private URL url;

    public FirebasePostTask(String url) {
        try {
            this.url = new URL(url.trim());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    @Override
    protected Void doInBackground(String... args) {
        HttpURLConnection connection = null;
        PrintWriter writer;
        StringBuilder response = new StringBuilder("Firebase response: ");

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
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
