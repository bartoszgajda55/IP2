package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quizapp.ip2.R;

public class AdminEditQuestionActivity extends AppCompatActivity {

    /***
     * This class is used when a user clicks on a Question from the AdminEditQuizActivity activity
     * OR
     * used when the user clicks on Create New Question from AdminEditQuizActivity
     ***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_question);
    }
}
