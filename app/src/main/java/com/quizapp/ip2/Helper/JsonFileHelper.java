package com.quizapp.ip2.Helper;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Allan on 15/04/2018.
 */

public class JsonFileHelper {

    public JsonFileHelper() {

    }

    public static void writeToFile(String data, String filename, Context context) {

        String externalStorage = Environment.getExternalStorageDirectory().getAbsolutePath();

        File outputDirectory = new File(externalStorage + File.separator + "Quizzes" );

        /*if directory doesn't exist, create it*/
        if(!outputDirectory.exists()) {

            outputDirectory.mkdirs();
        }


        File myFile = new File(outputDirectory, filename);

        /*Write to file*/
        try {
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.append(data);
            fileWriter.flush();
            fileWriter.close();

            myFile.createNewFile();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String readFromFile(String file, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static JSONObject readJson(Uri uri, Context context) {

        BufferedReader bufferedReader;
        FileOutputStream os;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(context.getContentResolver().openInputStream(uri)));
            JSONObject jsonObj = new JSONObject(bufferedReader.readLine());
            bufferedReader.close();
            return  jsonObj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return new JSONObject();
    }


}
