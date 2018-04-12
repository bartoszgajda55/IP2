package com.quizapp.ip2.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.quizapp.ip2.Model.Question;
import com.quizapp.ip2.R;

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
    Button btnUpload;
    Spinner spinnerColor;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_quiz);

        txtName = (EditText) findViewById(R.id.txtQuizName);
        txtDescription = (EditText) findViewById(R.id.txtQuizDescription);
        btnUpload = (Button) findViewById(R.id.btnUploadQuizImage);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        layoutQuestions = (LinearLayout) findViewById(R.id.layoutQuestions);

        //todo case for colors, set spinner value

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        b = getIntent().getExtras();

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        toolbar.setTitle("Editing quiz: \"" + b.getString("title") + "\"");

        txtName.setText(b.getString("title"));
        txtDescription.setText(b.getString("desc"));
        loadQuestions();
    }

    public void loadQuestions(){

        ArrayList<Question> questions = b.getParcelableArrayList("questions");
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
