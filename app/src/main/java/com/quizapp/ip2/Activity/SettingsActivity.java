package com.quizapp.ip2.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.quizapp.ip2.R;

/**
 * Created by Allan on 26/03/2018.
 */

public class SettingsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final Switch switchLight = (Switch) findViewById(R.id.switchLight);
        final Switch switchDark = (Switch) findViewById(R.id.switchDark);
        final Switch switchMono = (Switch) findViewById(R.id.switchMono);

        final Switch switchSound = (Switch) findViewById(R.id.switchSound); //TODO Implement sound toggle

        final TextView txtLight = (TextView) findViewById(R.id.txtLight);
        final TextView txtDark = (TextView) findViewById(R.id.txtDark);
        final TextView txtMono = (TextView) findViewById(R.id.txtMono);

        Button btnLogOut = (Button) findViewById(R.id.btnLogOut);


        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), HomepageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        //Listen for LIGHT SWITCH toggle
        switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchDark.setChecked(false);
                    switchDark.setClickable(true);
                    txtDark.setTypeface(null, Typeface.NORMAL);

                    switchMono.setChecked(false);
                    switchMono.setClickable(true);
                    txtMono.setTypeface(null, Typeface.NORMAL);

                    switchLight.setClickable(false);
                    txtLight.setTypeface(txtLight.getTypeface(), Typeface.BOLD);
                    //TODO Change theme to light
                }
            }
        });

        //Listen for DARK SWITCH toggle
        switchDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchLight.setChecked(false);
                    switchLight.setClickable(true);
                    txtLight.setTypeface(null, Typeface.NORMAL);

                    switchMono.setChecked(false);
                    switchMono.setClickable(true);
                    txtMono.setTypeface(null, Typeface.NORMAL);

                    switchDark.setClickable(false);
                    txtDark.setTypeface(txtDark.getTypeface(), Typeface.BOLD);
                    //TODO Change theme to dark
                }
            }
        });

        //Listen for MONO SWITCH toggle
        switchMono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchLight.setChecked(false);
                    switchLight.setClickable(true);
                    txtLight.setTypeface(null, Typeface.NORMAL);

                    switchDark.setChecked(false);
                    switchDark.setClickable(true);
                    txtDark.setTypeface(null, Typeface.NORMAL);

                    switchMono.setClickable(false);
                    txtMono.setTypeface(txtMono.getTypeface(), Typeface.BOLD);
                    //TODO Change theme to mono
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Log out");
                builder.setMessage("Are you sure you want to log out?");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

                        loginPrefsEditor.clear();
                        loginPrefsEditor.apply();

                        Intent intent = new Intent(SettingsActivity.this, AuthenticationActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();


            }
        });


    }
}
