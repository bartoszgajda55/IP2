package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quizapp.ip2.Helper.DarkenColorHelper;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.QuizHelper;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Model.Question;
import com.quizapp.ip2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminEditQuestionActivity extends AppCompatActivity {

    /***
     * This class is used when a user clicks on a Question from the AdminEditQuizActivity activity
     * OR
     * used when the user clicks on Create New Question from AdminEditQuizActivity
     ***/

    Button btnUploadImage;
    Button btnDeleteImage;
    Button btnSave;
    Button btnDelete;

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

    String questionImage = "";

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_question);

        btnUploadImage = (Button) findViewById(R.id.btnUploadImage);
        btnDeleteImage = (Button) findViewById(R.id.btnDeleteImage);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btnDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionImage = "";
                Toast.makeText(getApplicationContext(), "Image removed...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        radioA1 = (AppCompatRadioButton) findViewById(R.id.switchAnswer1);
        radioA2 = (AppCompatRadioButton) findViewById(R.id.switchAnswer2);
        radioA3 = (AppCompatRadioButton) findViewById(R.id.switchAnswer3);
        radioA4 = (AppCompatRadioButton) findViewById(R.id.switchAnswer4);

        txtQuestion = (EditText) findViewById(R.id.txtQuestion);
        txtAnswer1 = (EditText) findViewById(R.id.txtAnswer1);
        txtAnswer2 = (EditText) findViewById(R.id.txtAnswer2);
        txtAnswer3 = (EditText) findViewById(R.id.txtAnswer3);
        txtAnswer4 = (EditText) findViewById(R.id.txtAnswer4);

        bundle = getIntent().getExtras().getBundle("bundle");

        if(bundle.getBoolean("editingquestion")==false){
            btnDelete.setVisibility(View.INVISIBLE);
        }

        final JSONObject jsonQuestionBuilder = new JSONObject();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle.getBoolean("editingquestion")==true){
                    PostTask postQuestion = new PostTask();
                    try {
                        jsonQuestionBuilder.put("questionstring", txtQuestion.getText());
                        jsonQuestionBuilder.put("questionimage", questionImage);
                        if (radioA1.isChecked()) {
                            jsonQuestionBuilder.put("correctanswerstring", txtAnswer1.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring", txtAnswer2.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring2", txtAnswer3.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring3", txtAnswer4.getText().toString());
                        } else if (radioA2.isChecked()) {
                            jsonQuestionBuilder.put("wronganswerstring", txtAnswer1.getText().toString());
                            jsonQuestionBuilder.put("correctanswerstring", txtAnswer2.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring2", txtAnswer3.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring3", txtAnswer4.getText().toString());
                        } else if (radioA3.isChecked()) {
                            jsonQuestionBuilder.put("wronganswerstring", txtAnswer1.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring2", txtAnswer2.getText().toString());
                            jsonQuestionBuilder.put("correctanswerstring", txtAnswer3.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring3", txtAnswer4.getText().toString());
                        } else if(radioA4.isChecked()){
                            jsonQuestionBuilder.put("wronganswerstring", txtAnswer1.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring2", txtAnswer2.getText().toString());
                            jsonQuestionBuilder.put("wronganswerstring3", txtAnswer3.getText().toString());
                            jsonQuestionBuilder.put("correctanswerstring", txtAnswer4.getText().toString());

                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String[] questionResponse = postQuestion.sendPostRequest("question/" + bundle.getInt("id"), jsonQuestionBuilder.toString(), "PUT");
                        Toast.makeText(getApplicationContext(), "Question updated...", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    } catch (JSONException e){
                        Log.e("JSON ERROR", "Bad json");
                    }
                } else if(bundle.getBoolean("editingquestion")==false){

                    //correct ans, array wrong, image, string - need new constructor for this
                    String answer;
                    ArrayList<String> wrongAnswers = new ArrayList<>();
                    String questionString = txtQuestion.getText().toString();

                    if (radioA1.isChecked()) {
                        wrongAnswers.clear();
                        answer = txtAnswer1.getText().toString();
                        wrongAnswers.add(txtAnswer2.getText().toString());
                        wrongAnswers.add(txtAnswer3.getText().toString());
                        wrongAnswers.add(txtAnswer4.getText().toString());
                    } else if (radioA2.isChecked()) {
                        wrongAnswers.clear();
                        wrongAnswers.add(txtAnswer1.getText().toString());
                        answer = txtAnswer2.getText().toString();
                        wrongAnswers.add(txtAnswer3.getText().toString());
                        wrongAnswers.add(txtAnswer4.getText().toString());
                    } else if (radioA3.isChecked()) {
                        wrongAnswers.clear();
                        wrongAnswers.add(txtAnswer1.getText().toString());
                        wrongAnswers.add(txtAnswer2.getText().toString());
                        answer = txtAnswer3.getText().toString();
                        wrongAnswers.add(txtAnswer4.getText().toString());
                    } else if(radioA4.isChecked()){
                        wrongAnswers.clear();
                        wrongAnswers.add(txtAnswer1.getText().toString());
                        wrongAnswers.add(txtAnswer2.getText().toString());
                        wrongAnswers.add(txtAnswer3.getText().toString());
                        answer = txtAnswer4.getText().toString();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(bundle.getBoolean("editingquiz")==false){
                        Question questionObj = new Question(answer,wrongAnswers,questionImage,questionString);

                        AdminCreateQuizActivity.questionsList.add(questionObj);
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else if(bundle.getBoolean("editingquiz")==true){
                        PostTask pt = new PostTask();
                        JSONObject jsonQuestion = new JSONObject();
                        try {
                            jsonQuestion.put("quizid", bundle.getInt("id"));
                            jsonQuestion.put("questionstring", questionString);
                            jsonQuestion.put("questionimage", questionImage);
                            jsonQuestion.put("correctanswerstring", answer);
                            jsonQuestion.put("wronganswerstring", wrongAnswers.get(0));
                            jsonQuestion.put("wronganswerstring2", wrongAnswers.get(1));
                            jsonQuestion.put("wronganswerstring3", wrongAnswers.get(2));

                            String response[] = pt.sendPostRequest("question", jsonQuestion.toString(), "POST");
                            if(response[0].equals("201")){
                                Question questionObj = new Question(answer,wrongAnswers,questionImage,questionString);
                                QuizHelper.getQuiz().getQuestions().add(questionObj);
                                finish();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e){
                            Log.e("JSON ERROR", "Bad json");
                        }

                    } else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminEditQuestionActivity.this);
                builder.setTitle("Set Question Image");
                builder.setMessage("Enter the image URL of the image you wish to use\n");

                final EditText txtImageUrl = new EditText(AdminEditQuestionActivity.this);

                txtImageUrl.setHint("Image URL...");
                txtImageUrl.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                LinearLayout linearLayout = new LinearLayout(AdminEditQuestionActivity.this);
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
                        questionImage = txtImageUrl.getText().toString();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Image set...", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });


        if(bundle.getInt("color") == 0 ){
            bundle.putInt("color", Color.parseColor("#f44336")); //default color for creating
        }

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        if(bundle.getBoolean("editingquestion") == true) {
            toolbar.setTitle("Editing question...");
            getWindow().setStatusBarColor(new DarkenColorHelper().darkenColor(bundle.getInt("color")));
            toolbar.setBackgroundColor(bundle.getInt("color"));

            btnUploadImage.setTextColor(bundle.getInt("color"));
            btnDeleteImage.setTextColor(bundle.getInt("color"));
            btnSave.setBackgroundColor(bundle.getInt("color"));
            btnDelete.setBackgroundColor(bundle.getInt("color"));

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
                    new int[]{bundle.getInt("color")}
            );

            radioA1.setButtonTintList(colorStateList);
            radioA2.setButtonTintList(colorStateList);
            radioA3.setButtonTintList(colorStateList);
            radioA4.setButtonTintList(colorStateList);
        } else if(bundle.getBoolean("editingquestion") == false){
            toolbar.setTitle("New question...");
            getWindow().setStatusBarColor(new DarkenColorHelper().darkenColor(bundle.getInt("color")));
            toolbar.setBackgroundColor(bundle.getInt("color"));

            btnUploadImage.setTextColor(bundle.getInt("color"));
            btnDeleteImage.setTextColor(bundle.getInt("color"));
            btnSave.setBackgroundColor(bundle.getInt("color"));
            btnDelete.setBackgroundColor(bundle.getInt("color"));

            radioA1.toggle();

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[]{bundle.getInt("color")}
            );

            radioA1.setButtonTintList(colorStateList);
            radioA2.setButtonTintList(colorStateList);
            radioA3.setButtonTintList(colorStateList);
            radioA4.setButtonTintList(colorStateList);

        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.getBoolean("editingquiz")==false){
                    AdminCreateQuizActivity.questionsList.remove(bundle.getInt("viewId"));
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                else{
                    RequestTask pt = new RequestTask();
                    String[] response = pt.sendGetRequest("question/"+bundle.getInt("id"), "DELETE");
                    if(response[0].equals("200")){
                        Toast.makeText(getApplicationContext(), "Question removed", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }else{
                        Toast.makeText(getApplicationContext(), "Error...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}
