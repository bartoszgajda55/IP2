package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.R;

public class QuizActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imgQuiz = (ImageView) findViewById(R.id.imgQuiz);
        TextView txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        final Button button4 = (Button) findViewById(R.id.button4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final ProgressBar progressBarCorrect = (ProgressBar) findViewById(R.id.progressBarCorrect);

        progressBar.setMax(10);
        progressBarCorrect.setMax(10);
        progressBar.setProgress(getIntent().getExtras().getInt("question"));
        progressBarCorrect.setProgress(getIntent().getExtras().getInt("correct"));
        if (getIntent().getExtras().getInt("question")==10){
            Intent intent = new Intent(getApplicationContext(), QuizEndActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("correct", progressBarCorrect.getProgress());
            intent.putExtras(bundle);
            startActivity(intent);
        }

        //TODO Set toolbar colour to Quiz Colour, Title to quiz name
        txtQuestion.setText("Example Question"); //TODO Set question text
        button1.setText("Answer 1");
        button2.setText("Correct Answer");
        button3.setText("Answer 3");
        button4.setText("Answer 4");

        toolbar.setTitle("Quiz Title");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });

        new DownloadImageTask(imgQuiz).execute("https://d30y9cdsu7xlg0.cloudfront.net/png/36442-200.png");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.incrementProgressBy(1);
                button2.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("question", progressBar.getProgress());
                bundle.putInt("correct", progressBarCorrect.getProgress());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.incrementProgressBy(1);
                progressBarCorrect.incrementProgressBy(1);
                button2.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("question", progressBar.getProgress());
                bundle.putInt("correct", progressBarCorrect.getProgress());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.incrementProgressBy(1);
                button2.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("question", progressBar.getProgress());
                bundle.putInt("correct", progressBarCorrect.getProgress());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.incrementProgressBy(1);
                button2.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("question", progressBar.getProgress());
                bundle.putInt("correct", progressBarCorrect.getProgress());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
