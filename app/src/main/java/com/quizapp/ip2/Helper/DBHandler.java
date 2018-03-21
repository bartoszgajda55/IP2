package com.quizapp.ip2.Helper;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by aaron on 09/03/2018.
 */

public class DBHandler{
    //TODO ALL OF IT
    /**
    static final String API_URL = "https://ip2-api.herokuapp.com/api/";
    HttpsURLConnection connection;
    BufferedReader reader;
    ArrayList<JSONObject> results = new ArrayList<>();

    private ArrayList<JSONObject> getResults() {
        return results;
    }

    private void setResults(ArrayList<JSONObject> results){
        this.results = results;
    }


    public ArrayList<JSONObject> get(final String path){
        AsyncGetTask asyncGetTask = new AsyncGetTask(path);
        asyncGetTask.execute();}
        return getResults();
    }

    public void post(){
    }



    //INNER CLASS FOR GET
    public class AsyncGetTask extends AsyncTask<Void, Void, Void> {
        ArrayList<JSONObject> getResults = new ArrayList<>();
        String path;
        public AsyncGetTask(String path) {
            this.path = path;
        }

        @Override
        public Void doInBackground(Void... records) {
            try {
                URL url = new URL(API_URL + path);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                while((line = reader.readLine()) != null){
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(line);
                    try {
                        JSONObject jsonObject = new JSONObject(stringBuffer.toString());
                        getResults.add(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null){
                        reader.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println("RESULTS BEFORE: "+getResults);
            setResults(getResults);
        }
    }
        **/
}