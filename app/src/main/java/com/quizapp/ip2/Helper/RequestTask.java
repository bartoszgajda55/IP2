package com.quizapp.ip2.Helper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RequestTask {

    public static final String BASE_URL = "https://ip2-api.herokuapp.com/api/";

    public RequestTask() {
    }

    public String[] sendGetRequest(String path) {
        try {
            return new JsonTask().execute(BASE_URL + path).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class JsonTask extends AsyncTask<String, String, String[]> {

        protected String[] doInBackground(String... params) {
            HttpsURLConnection connection = null;
            String response[] = {"404","Error"};
            try {
                URL url = new URL(params[0]);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                stream.close();
                response[0] = String.valueOf(connection.getResponseCode());
                response[1] = sb.toString();

            }catch (Exception e) {
                Log.e("BUFFER ERROR", "Error converting result " + e.toString());

            }

            connection.disconnect();
            return response;
        }
    }
}
