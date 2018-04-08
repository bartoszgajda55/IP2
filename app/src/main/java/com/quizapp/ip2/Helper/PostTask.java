package com.quizapp.ip2.Helper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Aaron on 07/04/2018.
 */

public class PostTask {

    public static final String BASE_URL = "https://ip2-api.herokuapp.com/api/";

    public PostTask(){
    }

    public String[] sendPostRequest(String path, String json){
        try {
            return new JsonTask().execute(BASE_URL + path, json).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class JsonTask extends AsyncTask<String, String, String[]>{

        protected String[] doInBackground(String... params){
            HttpURLConnection connection = null;
            String response[] = {"404","Error"};

            try {
                URL url = new URL(params[0]);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Accept","application/json");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.writeBytes(params[1]);
                dos.flush();
                dos.close();

                InputStream is = null;

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    is = connection.getInputStream();
                } else {
                    is = connection.getErrorStream();
                }



                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine()) != null){
                        sb.append(line + "\n");
                    }
                    is.close();
                    response[0] = String.valueOf(connection.getResponseCode());
                    response[1] = sb.toString();

                }catch (Exception e){
                    Log.e("BUFFER ERROR", "Error converting result " + e.toString());
                }

                connection.disconnect();
                return response;

            } catch (IOException e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
        }

    }
}
