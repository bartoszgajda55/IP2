package com.quizapp.ip2.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.quizapp.ip2.Helper.DarkenColorHelper;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.QuizColor;
import com.quizapp.ip2.Helper.QuizHelper;
import com.quizapp.ip2.Model.Question;
import com.quizapp.ip2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminCreateQuizActivity extends AppCompatActivity {


    /***
     * This class is used when a user clicks on Create quiz from the AdminShowQuizzesActivity activity.
     * It has a button for posting the quiz
     * It has a scrolling list of question cards associated with the quiz
     *
     * In the future, allow the user to save the quiz without publishing (e.g. may not have 10+ questions, the requirement for a quiz)
     ***/

    LinearLayout layoutQuestions;

    EditText txtName;
    EditText txtDescription;

    Button btnUploadImage;
    Button btnNewQuestion;

    Button btnCreate;

    Spinner spinnerColor;
    Spinner spinnerDifficulty;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_quiz);

        txtName = (EditText) findViewById(R.id.txtQuizName);
        txtDescription = (EditText) findViewById(R.id.txtQuizDescription);

        btnUploadImage = (Button) findViewById(R.id.btnUploadQuizImage);
        btnNewQuestion = (Button) findViewById(R.id.btnNewQuestion);
        btnCreate = (Button) findViewById(R.id.btnCreate);

        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        spinnerDifficulty = (Spinner) findViewById(R.id.spinnerDifficulty);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        layoutQuestions = (LinearLayout) findViewById(R.id.layoutQuestions);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        toolbar.setTitle("Create Quiz");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        btnUploadImage.setTextColor(new DarkenColorHelper().darkenColor(getResources().getColor(R.color.colorPrimary)));

        btnCreate.setBackgroundColor(new DarkenColorHelper().darkenColor(getResources().getColor(R.color.colorPrimary)));
        btnNewQuestion.setBackgroundColor(new DarkenColorHelper().darkenColor(getResources().getColor(R.color.colorPrimary)));


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

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open camera roll, select image, upload to server. server needs php script for accepting images
                //todo consider pasting new image url????
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostTask quizPost = new PostTask();
                JSONObject jsonQuiz = new JSONObject();

                try {
                    if(!(txtName.getText().toString().equals("") || txtDescription.getText().toString().equals(""))){
                        jsonQuiz.put("quizname", txtName.getText().toString());
                        if(!(spinnerDifficulty.getSelectedItem().equals("Quiz Difficulty"))){
                            String difficulty, description;
                            difficulty = spinnerDifficulty.getSelectedItem().toString();
                            description = txtDescription.getText().toString() + ", DIFFICULTY: " + difficulty;

                            jsonQuiz.put("quizdescription", description);

                        } else {
                            Toast.makeText(getApplicationContext(), "Please select a difficulty...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //move
                        if(!(spinnerColor.getSelectedItem().equals("Quiz Color"))){
                            jsonQuiz.put("quizcolor", QuizColor.valueOf(spinnerColor.getSelectedItem().toString().toUpperCase()));
                            String[] response = quizPost.sendPostRequest("quiz/" + getIntent().getExtras().getInt("id") + "/edit", jsonQuiz.toString());
                            if(response[0].equals("200")){
                                Toast.makeText(getApplicationContext(), "Quiz updated...", Toast.LENGTH_SHORT).show();
                                finish();
                            } else{
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Please select a Quiz Color...", Toast.LENGTH_SHORT).show();
                            jsonQuiz.remove("quiztitle");
                            jsonQuiz.remove("quizdescription");
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Please fill out all fields...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("RESULT: ", "Bad JSON");
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();


        //loadQuestions();
    }

    public void loadQuestions(){

        //ArrayList<Question> questions = b.getParcelableArrayList("questions");
        ArrayList<Question> questions = QuizHelper.getQuiz().getQuestions();
        System.out.println(questions.get(0).getQuestionString());

        //TODO Limit to 6 or 7 questions per page, load more buttons
        for(int i = 0; i < questions.size(); i++){

            //Load each question individually
            Question q = questions.get(i);
            QuestionPreviewFragment questionPreview = new QuestionPreviewFragment();

            Bundle bundle = new Bundle();
            bundle.putString("question", q.getQuestionString());
            bundle.putString("img", q.getQuestionImage());
            questionPreview.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(this);
            rel.setId(View.generateViewId());
            getSupportFragmentManager().beginTransaction().add(rel.getId(), questionPreview).commit();
            layoutQuestions.addView(rel);



        }

    }

}
