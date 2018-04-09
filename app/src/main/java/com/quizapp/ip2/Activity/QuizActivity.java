package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.R;

public class QuizActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ProgressBar progressBarCorrect;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Bundle b;

    @ColorInt
    int darkenColor(@ColorInt int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.75f;
        return Color.HSVToColor(hsv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        b = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView imgQuiz = (ImageView) findViewById(R.id.imgQuiz);
        TextView txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarCorrect = (ProgressBar) findViewById(R.id.progressBarCorrect);

        progressBar.setMax(10);
        progressBarCorrect.setMax(10);
        progressBar.setProgress(getIntent().getExtras().getInt("question"));
        progressBarCorrect.setProgress(getIntent().getExtras().getInt("correct"));

        //IF ALL QUESTIONS ANSWERED GO TO QUIZ END
        if (getIntent().getExtras().getInt("question")==10){
            Intent intent = new Intent(getApplicationContext(), QuizEndActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("correct", progressBarCorrect.getProgress());
            bundle.putInt("color", b.getInt("color"));
            bundle.putString("img", b.getString("img"));
            intent.putExtras(bundle);
            startActivity(intent);
        }

        //Set button background color to neutral color
        button1.setBackgroundColor(darkenColor(b.getInt("color")));
        button2.setBackgroundColor(darkenColor(b.getInt("color")));
        button3.setBackgroundColor(darkenColor(b.getInt("color")));
        button4.setBackgroundColor(darkenColor(b.getInt("color")));


        txtQuestion.setText("Example Question"); //TODO Set question text and Randomise order of buttons
        button1.setText("Answer 1");
        button2.setText("Correct Answer");
        button3.setText("Answer 3");
        button4.setText("Answer 4");

        //Set toolbar title and color
        toolbar.setTitle(b.getString("title") + " Quiz");
        toolbar.setBackgroundColor(b.getInt("color"));

        //Set status bar to darker color variant
        Window window = getWindow();
        window.setStatusBarColor(darkenColor(b.getInt("color")));

        //Set toolbar back arrow and it's listener
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

        //Set quiz image
        new DownloadImageTask(imgQuiz).execute(b.getString("img"));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickCorrect();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }

    public void onButtonClick(){
        progressBar.incrementProgressBy(1);
        button2.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("question", progressBar.getProgress());
        bundle.putInt("correct", progressBarCorrect.getProgress());
        bundle.putInt("color", b.getInt("color"));
        bundle.putString("title", b.getString("title"));
        bundle.putString("img", b.getString("img"));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onButtonClickCorrect(){
        progressBarCorrect.incrementProgressBy(1);
        onButtonClick();
    }
}
