package com.quizapp.ip2.Helper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class RequestTask extends AsyncTask<String, String, String[]>{

    public static final String BASE_URL = "https://ip2-api.herokuapp.com/api/";

    public interface AsyncResponse {
        void processFinish(String[] output);
    }

    public AsyncResponse delegate = null;

    public RequestTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    public String[] sendGetRequest(String path, String method) {
        try {
            return execute(BASE_URL + path, method).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String[] doInBackground(String... params) {
        HttpsURLConnection connection = null;
        String response[] = {"404","Error"};
        try {
            URL url = new URL(params[0]);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(params[1]);
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

    @Override
    protected void onPostExecute(String[] result){

        super.onPostExecute(result);
        delegate.processFinish(result);
    }



}
