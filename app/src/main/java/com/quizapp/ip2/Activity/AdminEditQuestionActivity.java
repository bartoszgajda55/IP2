package com.quizapp.ip2.Activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quizapp.ip2.Helper.DarkenColorHelper;
import com.quizapp.ip2.R;

public class AdminEditQuestionActivity extends AppCompatActivity {

    /***
     * This class is used when a user clicks on a Question from the AdminEditQuizActivity activity
     * OR
     * used when the user clicks on Create New Question from AdminEditQuizActivity
     ***/

    Button btnUploadImage;
    Button btnDeleteImage;
    Button btnSave;

    AppCompatRadioButton radioA1;
    AppCompatRadioButton radioA2;
    AppCompatRadioButton radioA3;
    AppCompatRadioButton radioA4;

    Toolbar toolbar;

    EditText txtQuestion;
    EditText txtAnswer1;
    EditText txtAnswer2;
    EditText txtAnswer3;
    EditText txtAnswer4;

   // Quiz quiz;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_question);

        bundle = getIntent().getExtras().getBundle("bundle");

        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
        btnDeleteImage = (Button) findViewById(R.id.btnDeleteImage);
        btnSave = (Button) findViewById(R.id.btnSave);

        radioA1 = (AppCompatRadioButton) findViewById(R.id.switchAnswer1);
        radioA2 = (AppCompatRadioButton) findViewById(R.id.switchAnswer2);
        radioA3 = (AppCompatRadioButton) findViewById(R.id.switchAnswer3);
        radioA4 = (AppCompatRadioButton) findViewById(R.id.switchAnswer4);

        txtQuestion = (EditText) findViewById(R.id.txtQuestion);
        txtAnswer1 = (EditText) findViewById(R.id.txtAnswer1);
        txtAnswer2 = (EditText) findViewById(R.id.txtAnswer2);
        txtAnswer3 = (EditText) findViewById(R.id.txtAnswer3);
        txtAnswer4 = (EditText) findViewById(R.id.txtAnswer4);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //todo YES/NO DIALOG, progress lost etc
                //TODO activity leave animation
                finish();
            }
        });

        //quiz = QuizHelper.getQuiz();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioA1.isSelected()){
                    //todo post task edit question
                } else if(radioA2.isSelected()){

                } else if(radioA3.isSelected()){

                }else {
                    //button 4

                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bundle.getInt("color") == 0 ){
            bundle.putInt("color", Color.parseColor("f44336"));
        }

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        toolbar.setTitle("Editing question...");
        getWindow().setStatusBarColor(new DarkenColorHelper().darkenColor(bundle.getInt("color")));
        toolbar.setBackgroundColor(bundle.getInt("color"));

        btnUploadImage.setTextColor(bundle.getInt("color"));
        btnDeleteImage.setTextColor(bundle.getInt("color"));
        btnSave.setBackgroundColor(bundle.getInt("color"));

        txtQuestion.setText(bundle.getString("question"));
        txtAnswer1.setText(bundle.getString("answer"));
        txtAnswer2.setText(bundle.getString("wrongAnswer1"));
        txtAnswer3.setText(bundle.getString("wrongAnswer2"));
        txtAnswer4.setText(bundle.getString("wrongAnswer3"));

        radioA1.toggle();

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_enabled}
                },
                new int[] {bundle.getInt("color")}
        );

        radioA1.setButtonTintList(colorStateList);
        radioA2.setButtonTintList(colorStateList);
        radioA3.setButtonTintList(colorStateList);
        radioA4.setButtonTintList(colorStateList);


    }
}
