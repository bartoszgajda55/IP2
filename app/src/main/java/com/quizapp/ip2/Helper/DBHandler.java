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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by aaron on 09/03/2018.
 */

public class DBHandler{
    static final String API_URL = "https://ip2-api.herokuapp.com/api/";

    
    HttpsURLConnection connection;
    BufferedReader reader;

    public ArrayList<JSONObject> get(final String path){
        final ArrayList<JSONObject> results = new ArrayList<>();

         AsyncTask.execute(new Runnable() {
          @Override
          public void run() {


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
                          results.add(jsonObject);

                          System.out.println("RESULTS: " + results.toString());

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


          }
      });
        return results;
    }

    public void post(){

    }



}
