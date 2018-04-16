package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.quizapp.ip2.Helper.DarkenColorHelper;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.QuizColor;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Model.Question;
import com.quizapp.ip2.R;

import org.json.JSONArray;
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

    String quizImage;

    public static ArrayList<Question> questionsList = new ArrayList<>(); //todo remember to clear this when you leave the page

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
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCreateQuizActivity.this);
                builder.setTitle("Set Quiz Image");
                builder.setMessage("Enter the image URL of the image you wish to use\n");

                final EditText txtImageUrl = new EditText(AdminCreateQuizActivity.this);

                txtImageUrl.setHint("Image URL...");
                txtImageUrl.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                LinearLayout linearLayout = new LinearLayout(AdminCreateQuizActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                int paddingDp = 20;
                int paddingPx = (int)(paddingDp * getResources().getDisplayMetrics().density);
                linearLayout.setPadding(paddingPx, 0, paddingPx, 0);

                linearLayout.addView(txtImageUrl);
                builder.setView(linearLayout);

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quizImage = txtImageUrl.getText().toString();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Image set...", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upload quizhelper to database
                RequestTask post = new RequestTask();
                JSONObject jsonQuiz = new JSONObject();
                try {
                    jsonQuiz.put("quizimage", quizImage);
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
                        if(!(spinnerColor.getSelectedItem().equals("Quiz Color"))){


                            jsonQuiz.put("quizcolor", QuizColor.valueOf(spinnerColor.getSelectedItem().toString().toUpperCase()));
                            if(questionsList.size() >= 10){
                                Log.e("VALUES", ": "+questionsList.toString());

                                /**Post quiz to database**/
                                String[] quizResponse = post.sendGetRequest("quiz/","GET");

                                /**Post questions to database **/
                                Log.e("RESPONSE",": "+quizResponse[1]);
                                JSONArray jsonArray = new JSONArray(quizResponse[1]);
                                int newQuizId =jsonArray.getJSONObject(0).getInt("id");

                                JSONObject jsonAllQuizQuestions = new JSONObject();
                                jsonAllQuizQuestions.put("quizid", newQuizId);
                                JSONArray jsonQuestionArray = new JSONArray();
                                for(int i = 0; i < questionsList.size(); i++){
                                    JSONObject jsonQuestion = new JSONObject();
                                    jsonQuestion.put("questionstring", questionsList.get(i).getQuestionString());
                                    jsonQuestion.put("questionimage", questionsList.get(i).getQuestionImage());
                                    jsonQuestion.put("correctanswerstring", questionsList.get(i).getCorrectAnswer());
                                    jsonQuestion.put("wronganswerstring", questionsList.get(i).getWrongAnswers().get(0));
                                    jsonQuestion.put("wronganswerstring2", questionsList.get(i).getWrongAnswers().get(1));
                                    jsonQuestion.put("wronganswerstring3", questionsList.get(i).getWrongAnswers().get(2));
                                    jsonQuestionArray.put(jsonQuestion);
                                }
                                jsonAllQuizQuestions.put("questions", jsonQuestionArray);
                                Log.e("JSON ALL QUESTIONS","VALUE: "+jsonAllQuizQuestions.toString());
                                String[] allQuestionsResponse = post.sendPostRequest("question/many", jsonAllQuizQuestions.toString(),"POST");

                                Log.e("POST","POSTING: "+allQuestionsResponse[0]+" VALUES: "+allQuestionsResponse[1]);

                                if(quizResponse[0].equals("201") && allQuestionsResponse.equals("201")){
                                    Toast.makeText(getApplicationContext(), "Quiz Created...", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else{
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "A quiz must have at least 10 questions", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Please select a Quiz Color...", Toast.LENGTH_SHORT).show();
                            jsonQuiz.remove("quizname");
                            jsonQuiz.remove("quizdescription");
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Please fill out all fields...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("RESULT: ", "Bad JSON");
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminEditQuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("editingquestion", false);
                bundle.putBoolean("editingquiz", false);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();


        loadQuestions();
    }

    public void loadQuestions(){
        layoutQuestions.removeAllViews();

        for(int i = 0; i < questionsList.size(); i++){
            Question q = questionsList.get(i);
            QuestionPreviewFragment questionPreview = new QuestionPreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("viewId", i);
            bundle.putString("question", q.getQuestionString());
            bundle.putString("img", q.getQuestionImage());
            bundle.putString("answer", q.getCorrectAnswer());
            bundle.putString("wrongAnswer1", q.getWrongAnswers().get(0));
            bundle.putString("wrongAnswer2", q.getWrongAnswers().get(1));
            bundle.putString("wrongAnswer3", q.getWrongAnswers().get(2));
            questionPreview.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(this);
            rel.setId(View.generateViewId());
            getSupportFragmentManager().beginTransaction().add(rel.getId(), questionPreview).commit();
            layoutQuestions.addView(rel);
        }
    }

}
