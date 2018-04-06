package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.quizapp.ip2.R;

import org.w3c.dom.Text;

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

        //TODO load quiz image and set

        int correct = getIntent().getExtras().getInt("correct");
        rating.setMax(10);
        rating.setProgress(correct);

        //TODO Calcualte XP
        int xp=(correct*10);


        message.setText("You got "+correct+" out of 10");
        messageXP.setText(xp+"XP Earned!");

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });
    }
}
