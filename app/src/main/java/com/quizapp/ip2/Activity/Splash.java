package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import  com.quizapp.ip2.R;

public class Splash extends AppCompatActivity {


    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //DELAYED, USE THIS TIME TO LOAD DATABASE CONTENT?
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }
}