package by.solutions.dumb.smartfoodassistant.util.firebase.rest.api;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FirebaseGetTask extends AsyncTask<Void, Void, String> {
    private final String LOG_TAG = "FirebaseGET";
    private URL url;

    public FirebaseGetTask(String url) {
        try {
            this.url = new URL(url.trim());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

    @Override
    protected String doInBackground(Void... args) {
        HttpURLConnection connection = null;
        BufferedReader reader;
        String line;
        StringBuilder result = new StringBuilder();
        StringBuilder response = new StringBuilder("Firebase response: ");

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();

            response.append(connection.getResponseCode());
            response.append(", ");
            response.append(connection.getResponseMessage());
            Log.d(LOG_TAG, response.toString());

            if (connection.getResponseCode() == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                Log.e(LOG_TAG, "Something went wrong with Firebase: " + url);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result.toString();
    }
}
