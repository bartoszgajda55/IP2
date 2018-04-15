package com.quizapp.ip2.Helper;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStreamReader;

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

    public static JSONObject readJson(Uri uri, Context context) {

        BufferedReader bufferedReader;
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
