package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.R;

public class QuizEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        ProgressBar rating = (ProgressBar) findViewById(R.id.ratingBar);
        ImageView imgQuiz = (ImageView) findViewById(R.id.imgQuiz);
        TextView message = (TextView) findViewById(R.id.message);
        TextView messageXP = (TextView) findViewById(R.id.messageXP);
        Button endButton = (Button) findViewById(R.id.btnEnd);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutRelative);

        new DownloadImageTask(imgQuiz).execute(getIntent().getExtras().getString("img"));

        int correct = getIntent().getExtras().getInt("correct"); //TODO ADD to user statistics
        rating.setMax(10);
        rating.setProgress(correct);

        relativeLayout.setBackgroundColor(getIntent().getExtras().getInt("color"));

        //Set status bar to darker color variant
        Window window = getWindow();
        window.setStatusBarColor(darkenColor(getIntent().getExtras().getInt("color")));

        //TODO Calculate XP
        int xp=(correct*10);

        //Give different messages depending on score
        if (correct>7){
            message.setText("Well Done!" +"\n"+" You got "+correct+" out of 10");
            messageXP.setText(xp+"XP Earned!");}
        else if(correct>4 && correct<7){
            message.setText("Not bad."+"\n"+" You got "+correct+" out of 10");
            messageXP.setText(xp+"XP Earned!");
        }else if(correct>0 && correct<=4){
            message.setText("Oh Dear."+"\n"+" You got "+correct+" out of 10");
            messageXP.setText(xp+"XP Earned!");
        }else{
            message.setText("Oh no!"+"\n"+" You got no answers correct");
            messageXP.setText("No XP Earned.");
        } //TODO add xp to user


        endButton.setBackgroundColor(darkenColor(getIntent().getExtras().getInt("color")));
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });
    }

    @ColorInt
    int darkenColor(@ColorInt int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.75f;
        return Color.HSVToColor(hsv);
    }
}
