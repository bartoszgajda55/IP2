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

public class AdminEditQuizActivity extends AppCompatActivity {


    /***
     * This class is used when a user clicks on a quiz from the AdminShowQuizzesActivity activity.
     * It has a button for deleting the quiz and posting the quiz
     * It has a scrolling list of question cards associated with the quiz
     *
     * In the future, allow the user to save the quiz without publishing (e.g. may not have 10+ questions, the requirement for a quiz)
     ***/

    LinearLayout layoutQuestions;
    Bundle b;

    EditText txtName;
    EditText txtDescription;

    Button btnUploadImage;
    Button btnNewQuestion;
    Button btnRemoveImage;

    Button btnRemoveQuiz;
    Button btnSave;


    Spinner spinnerColor;
    Spinner spinnerDifficulty;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_quiz);

        txtName = (EditText) findViewById(R.id.txtQuizName);
        txtDescription = (EditText) findViewById(R.id.txtQuizDescription);

        btnUploadImage = (Button) findViewById(R.id.btnUploadQuizImage);
        btnNewQuestion = (Button) findViewById(R.id.btnNewQuestion);
        btnRemoveQuiz = (Button) findViewById(R.id.btnDeleteQuiz);
        btnRemoveImage = (Button) findViewById(R.id.btnDeleteQuizImage);
        btnSave = (Button) findViewById(R.id.btnSave);

        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        spinnerDifficulty = (Spinner) findViewById(R.id.spinnerDifficulty);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        layoutQuestions = (LinearLayout) findViewById(R.id.layoutQuestions);

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //todo YES/NO DIALOG, progress lost etc
                //TODO activity leave animation
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open camera roll, select image, upload to server. server needs php script for accepting images
                //todo consider pasting new image url????
            }
        });

        btnRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostTask imagePost = new PostTask();
                JSONObject jsonNoImage = new JSONObject();
                try {
                    jsonNoImage.put("quizimage", "");
                } catch (JSONException e) {
                    Log.e("RESULT: ", "Bad JSON");
                }
                String[] response = imagePost.sendPostRequest("quiz/" + getIntent().getExtras().getInt("id") + "/edit", jsonNoImage.toString());

                if (response[0].equals("200")){
                    Toast.makeText(getApplicationContext(), "Quiz image removed...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostTask quizPost = new PostTask();
                JSONObject jsonQuiz = new JSONObject();

                try {
                    if((!(txtName.getText().toString().equals("") || txtDescription.getText().toString().equals(""))) && txtName.length() <= 32 && txtDescription.length() <= 230){
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
                            String[] response = quizPost.sendPostRequest("quiz/" + QuizHelper.getQuiz().getId() + "/edit", jsonQuiz.toString());
                            if(response[0].equals("200")){
                                Toast.makeText(getApplicationContext(), "Quiz updated...", Toast.LENGTH_SHORT).show();
                                finish();
                            } else{
                                Log.e("SUBMIT JSON: ", jsonQuiz.toString());
                                Log.e("RESPONSE: ", response[0] + ", " + response[1]);
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Please select a Quiz Color...", Toast.LENGTH_SHORT).show();
                            jsonQuiz.remove("quiztitle");
                            jsonQuiz.remove("quizdescription");
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid fields (Values may be too long)...", Toast.LENGTH_SHORT).show();
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
        b = getIntent().getExtras();

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        toolbar.setTitle("Editing quiz: \"" + b.getString("title") + "\"");
        getWindow().setStatusBarColor(new DarkenColorHelper().darkenColor(b.getInt("color")));
        toolbar.setBackgroundColor(b.getInt("color"));

        txtName.setText(b.getString("title"));

        String fullDescription = b.getString("desc");
        int difficultyIndex = b.getString("desc").lastIndexOf(", DIFFICULTY");
        String newDescription = fullDescription.substring(0, difficultyIndex);
        txtDescription.setText(newDescription);

        btnUploadImage.setTextColor(new DarkenColorHelper().darkenColor(b.getInt("color")));
        btnRemoveImage.setTextColor(new DarkenColorHelper().darkenColor(b.getInt("color")));

        btnRemoveQuiz.setBackgroundColor(new DarkenColorHelper().darkenColor(b.getInt("color")));
        btnSave.setBackgroundColor(new DarkenColorHelper().darkenColor(b.getInt("color")));
        btnNewQuestion.setBackgroundColor(new DarkenColorHelper().darkenColor(b.getInt("color")));

        loadQuestions();
    }

    public void loadQuestions(){

        ArrayList<Question> questions = QuizHelper.getQuiz().getQuestions();

        //TODO Limit to 6 or 7 questions per page, load more buttons
        for(int i = 0; i < questions.size(); i++){

            //Load each question individually
            Question q = questions.get(i);
            QuestionPreviewFragment questionPreview = new QuestionPreviewFragment();

            Bundle bundle = new Bundle();

            bundle.putInt("id", q.getQuestionId());
            bundle.putString("question", q.getQuestionString());
            bundle.putString("answer", q.getCorrectAnswer());
            bundle.putString("wrongAnswer1", q.getWrongAnswers().get(0));
            bundle.putString("wrongAnswer2", q.getWrongAnswers().get(1));
            bundle.putString("wrongAnswer3", q.getWrongAnswers().get(2));
            bundle.putString("img", q.getQuestionImage());
            bundle.putInt("color", b.getInt("color"));


            questionPreview.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(this);
            rel.setId(View.generateViewId());
            getSupportFragmentManager().beginTransaction().add(rel.getId(), questionPreview).commit();
            layoutQuestions.addView(rel);



        }

    }

}
