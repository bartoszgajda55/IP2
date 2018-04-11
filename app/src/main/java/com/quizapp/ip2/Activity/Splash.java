package com.quizapp.ip2.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.Model.User;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Splash extends AppCompatActivity {

    boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Automatically authenticate user if remember me was selected
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        if(loginPreferences != null && loginPreferences.getAll().size()>0){

            String email = loginPreferences.getString("email", "");
            String password = loginPreferences.getString("password", "");

            final PostTask pt = new PostTask();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);

                String[] response = pt.sendPostRequest("user/login", jsonObject.toString());
                if(response[0].equals("200")){
                    //Authentication successfull
                    success = true;

                    JSONArray jsonResponseArray = new JSONArray(response[1]);
                    JSONObject jsonResponse = jsonResponseArray.getJSONObject(0);

                    User user = new User();
                    user.setUserID(jsonResponse.getInt("UserID"));
                    user.setUsername(jsonResponse.getString("Username"));
                    user.setEmail(jsonResponse.getString("Email"));
                    user.setFirstName(jsonResponse.getString("Firstname"));
                    user.setSurname(jsonResponse.getString("Surname"));
                    user.setProfilePicture(jsonResponse.getString("ProfileImage"));
                    user.setAdminStatus(jsonResponse.getInt("AdminStatus"));
                    user.setXp(jsonResponse.getInt("XP"));
                    user.setQuizzessCompleted(jsonResponse.getInt("QuizzessCompleted"));
                    user.setCorrectAnswers(jsonResponse.getInt("CorrectAnswers"));

                    UserHelper.setUser(user);

                }else{
                    Toast.makeText(getApplicationContext(), "Error signing in...", Toast.LENGTH_SHORT);
                }
            }catch (JSONException e){
                Log.e("JSON ERROR", "Invalid JSON");
            }

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!success){
                    Intent intent = new Intent(Splash.this, AuthenticationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else{
                    Intent intent = new Intent(Splash.this, HomepageActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        }, 1000); //Delayed splash screen - 1 second
    }

}