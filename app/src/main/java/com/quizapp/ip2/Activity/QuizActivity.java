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
import com.quizapp.ip2.Model.Question;
import com.quizapp.ip2.R;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ProgressBar progressBarCorrect;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Bundle b;
    public static ArrayList<Question> questions;
    private ArrayList answers;
    private String correctAnswer;
    ImageView image;

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
        TextView txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarCorrect = (ProgressBar) findViewById(R.id.progressBarCorrect);
        image = (ImageView) findViewById(R.id.imgQuiz);

        //Set progress bar values
        progressBar.setMax(10);
        progressBarCorrect.setMax(10);
        progressBar.setProgress(getIntent().getExtras().getInt("question"));
        progressBarCorrect.setProgress(getIntent().getExtras().getInt("correct"));

        //IF ALL QUESTIONS ANSWERED GO TO QUIZ END
        if (getIntent().getExtras().getInt("question") == 10) {
            Intent intent = new Intent(getApplicationContext(), QuizEndActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("correct", progressBarCorrect.getProgress());
            bundle.putInt("color", b.getInt("color"));
            bundle.putString("img", b.getString("img"));
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        //Set button background color to neutral color
        button1.setBackgroundColor(darkenColor(b.getInt("color")));
        button2.setBackgroundColor(darkenColor(b.getInt("color")));
        button3.setBackgroundColor(darkenColor(b.getInt("color")));
        button4.setBackgroundColor(darkenColor(b.getInt("color")));

        txtQuestion.setText("" + questions.get(progressBar.getProgress()).getQuestionString());

        answers = new ArrayList<>();
        if (questions.get(progressBar.getProgress()).getQuestionImage().equals("")){
            new DownloadImageTask(image).execute(b.getString("img"));
        } else {
            new DownloadImageTask(image).execute(questions.get(progressBar.getProgress()).getQuestionImage());
        }
        correctAnswer = questions.get(progressBar.getProgress()).getCorrectAnswer();
        answers.add(correctAnswer);

        for (int i=0; i<3; i++) {
            answers.add(questions.get(progressBar.getProgress()).getWrongAnswers().get(i));
        }
        Collections.shuffle(answers);
        button1.setText(""+answers.get(0));
        button2.setText(""+answers.get(1));
        button3.setText(""+answers.get(2));
        button4.setText(""+answers.get(3));

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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(button4);
            }
        });
    }

    public void onButtonClick(Button button){
        progressBar.incrementProgressBy(1);
        //Validate
        if(button.getText().toString().equals(correctAnswer)){
            progressBarCorrect.incrementProgressBy(1);
            button.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
        }else if(button1.getText().toString().equals(correctAnswer)){
            button1.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
        }else if(button2.getText().toString().equals(correctAnswer)){
            button2.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
        }else if(button3.getText().toString().equals(correctAnswer)){
            button3.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
        }else if(button4.getText().toString().equals(correctAnswer)){
            button4.setBackgroundColor(getResources().getColor(R.color.colorIntroGreen));
        }

        //IF ALL QUESTIONS ANSWERED GO TO QUIZ END
        if (progressBar.getProgress()==10){
            Intent intent = new Intent(getApplicationContext(), QuizEndActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("correct", progressBarCorrect.getProgress());
            bundle.putInt("color", b.getInt("color"));
            bundle.putString("img", b.getString("img"));
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else {
            Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("question", progressBar.getProgress());
            bundle.putInt("correct", progressBarCorrect.getProgress());
            bundle.putInt("color", b.getInt("color"));
            bundle.putString("title", b.getString("title"));
            bundle.putString("img", b.getString("img"));
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
