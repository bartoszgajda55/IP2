package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quizapp.ip2.Helper.DarkenColorHelper;
import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.Helper.LevelParser;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.QuizHelper;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.R;
import com.quizapp.ip2.Views.GifImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        ProgressBar rating = (ProgressBar) findViewById(R.id.ratingBar);
        ImageView imgQuiz = (ImageView) findViewById(R.id.imgQuiz);
        TextView message = (TextView) findViewById(R.id.message);
        TextView messageXP = (TextView) findViewById(R.id.messageXP);
        Button endButton = (Button) findViewById(R.id.btnEnd);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutRelative);

        new DownloadImageTask(imgQuiz).execute(getIntent().getExtras().getString("img"));

        int correct = getIntent().getExtras().getInt("correct");
        rating.setMax(10);
        rating.setProgress(correct);

        relativeLayout.setBackgroundColor(getIntent().getExtras().getInt("color"));

        //Set status bar to darker color variant
        Window window = getWindow();
        window.setStatusBarColor(new DarkenColorHelper().darkenColor(getIntent().getExtras().getInt("color")));

        int xpMultiplier;
        if(QuizHelper.getQuiz().getDescription().endsWith("Easy")){
          xpMultiplier = 4;
        } else if (QuizHelper.getQuiz().getDescription().endsWith("Medium")) {
            xpMultiplier = 8;
        } else { //hard
            xpMultiplier = 12;
        }

        int xp=(correct*xpMultiplier);

        int prevLevel = new LevelParser(UserHelper.getUser().getXp()).getLevel();
        UserHelper.getUser().addXp(xp);
        UserHelper.getUser().addCorrectAnswers(correct);
        UserHelper.getUser().addQuizzessCompleted(1);

        //ADD TO USERS RECENT QUIZZES
        PostTask pt = new PostTask();

        JSONObject json = new JSONObject();
        try {
            json.put("quizid", QuizHelper.getQuiz().getId());
            json.put("userid", UserHelper.getUser().getUserID());
            json.put("score", correct);
            String[] response = pt.sendPostRequest("recentQuiz", json.toString(), "POST");
            if(response[0].equals("500")){
                Toast.makeText(getApplicationContext(), "Error updating recent quiz...", Toast.LENGTH_SHORT).show();
            }
        }catch(JSONException e){
            Log.e("JSON ERROR", "BAD JSON");
            e.printStackTrace();
        }

        int newLevel = new LevelParser(UserHelper.getUser().getXp()).getLevel();
        if(newLevel > prevLevel) {
            //User has levelled up...

            final AlertDialog.Builder builder = new AlertDialog.Builder(QuizEndActivity.this);
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View view = inflater.inflate(R.layout.levelup_dialog, null);

            GifImageView gif = (GifImageView) view.findViewById(R.id.gifLevelUp);
            TextView levelText = (TextView) view.findViewById(R.id.textView2);
            levelText.setText("You are now level " + newLevel + "...");
            gif.setBackground(getResources().getDrawable(R.drawable.rounded));
            gif.setGifImageResource(R.drawable.level_up);
            gif.setClipToOutline(true);


            builder.setView(view);

            AlertDialog ad = builder.create();


            ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ad.show();


        }

        //Upload local user data to database
        UserHelper.uploadUser();

        //Give different messages depending on score
        if (correct>=7){
            message.setText("Well Done!" +"\n"+" You got "+correct+" out of 10");
            messageXP.setText(xp+"XP Earned!");}
        else if(correct>4 && correct<7){
            message.setText("Not bad."+"\n"+" You got "+correct+" out of 10");
            messageXP.setText(xp+"XP Earned!");
        }else if(correct>0 && correct<=4){
            message.setText("Oh Dear."+"\n"+" You got "+correct+" out of 10");
            messageXP.setText(xp+"XP Earned!");
        }else{
            message.setText("Oh no!"+"\n"+" You got no answers correct");
            messageXP.setText("No XP Earned.");
        }


        endButton.setBackgroundColor(new DarkenColorHelper().darkenColor(getIntent().getExtras().getInt("color")));
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }

}
