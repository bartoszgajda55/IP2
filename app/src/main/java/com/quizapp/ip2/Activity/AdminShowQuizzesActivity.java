package com.quizapp.ip2.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminShowQuizzesActivity extends AppCompatActivity {


    /***
     * This class shows a list of all quizzes in the application in display cards, like the Search activity however has different functionality and no search function.
     * Perhaps add the search function to this activity in the future?
     ***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_quizzes);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutLinear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //Load the quizzes
        final RequestTask rt = new RequestTask();
        try {
            JSONArray resultset = new JSONArray(rt.sendGetRequest("quiz"));
            ArrayList<RelativeLayout> foundQuizzes = new ArrayList<>();

            for (int i = 0; i < resultset.length(); i++) {

                JSONObject result = resultset.getJSONObject(i);

                QuizPreviewFragment quizPreview = new QuizPreviewFragment();
                Bundle quizBundle = new Bundle();
                String quizTitle = result.getString("QuizName"); //Load name to search by name
                String quizDesc = result.getString("QuizDescription"); //Load description to search by description
                String quizImg = result.getString("QuizImage");
                int quizColor = Color.parseColor("#" + result.getString("QuizColor"));

                quizBundle.putString("title", quizTitle);
                quizBundle.putString("desc", quizDesc);
                quizBundle.putString("img", quizImg);
                quizBundle.putInt("color", quizColor);

                quizPreview.setArguments(quizBundle);
                RelativeLayout rel = new RelativeLayout(this);
                rel.setId(View.generateViewId());
                getSupportFragmentManager().beginTransaction().add(rel.getId(),quizPreview).commit();
                linearLayout.addView(rel);

            }
        } catch (JSONException e){
            Log.e("ERROR", "Invalid JSON");
        }


        //Handle the toolbar
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
        toolbar.setTitle("Showing all quizzes...");
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
}
