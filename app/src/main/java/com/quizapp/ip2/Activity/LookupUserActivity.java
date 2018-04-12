package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LookupUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lookup);

        Button btnSave = (Button) findViewById(R.id.btnSave);

        Bundle args = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txtUsername = (TextView) findViewById(R.id.txtUsername);
        final TextView txtUserId = (TextView) findViewById(R.id.txtUserId);
        TextView txtEmail= (TextView) findViewById(R.id.txtEmail);
        TextView txtName = (TextView) findViewById(R.id.txtName);

        TextView txtTotalXP = (TextView) findViewById(R.id.txtXP);
        TextView txtLevel = (TextView) findViewById(R.id.txtLevel);
        TextView txtRanking = (TextView) findViewById(R.id.txtRank);
        TextView txtQuizzesComplete = (TextView) findViewById(R.id.txtComplete);
        TextView txtCorrectAnswers = (TextView) findViewById(R.id.txtCorrect);

        final Switch switchAdmin = (Switch) findViewById(R.id.switchAdmin);
        Switch switchBanned = (Switch) findViewById(R.id.switchBanned);

        ImageView imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
        imgProfilePic.setBackground(getResources().getDrawable(R.drawable.rounded));
        imgProfilePic.setClipToOutline(true);

        txtUsername.setText(args.getString("username"));
        txtUserId.setText(String.valueOf(args.getInt("userid")));

        txtEmail.setText(args.getString("email"));
        txtName.setText(args.getString("firstname") + " " + args.getString("surname"));

        new DownloadImageTask(imgProfilePic).execute(args.getString("profilepic"));


        txtTotalXP.setText(String.valueOf(args.getInt("xp")));
        txtLevel.setText(String.valueOf(args.getInt("level")));
        txtRanking.setText(String.valueOf(args.getInt("ranking")));
        txtQuizzesComplete.setText(String.valueOf(args.getInt("quizzesscomplete")));
        txtCorrectAnswers.setText(String.valueOf(args.getInt("correctanswers")));

        switchAdmin.setChecked(args.getBoolean("adminstatus"));
        switchBanned.setChecked(args.getBoolean("banned"));

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), AdminActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //todo tidy up ban, response 500 when user already banned
                PostTask pt = new PostTask();

                JSONObject jsonUser = new JSONObject();
                JSONObject jsonBan = new JSONObject();
                try {
                    int boolAdmin = (switchAdmin.isChecked()) ? 1 : 0;
                    jsonUser.put("adminstatus", boolAdmin);

                    jsonBan.put("userid", txtUserId.getText().toString());



                } catch (JSONException e){
                    Log.e("JSON ERROR", "Bad JSON");
                }
                String[] adminResponse = pt.sendPostRequest("user/" + txtUserId.getText().toString() + "/edit", jsonUser.toString());

            }
        });

    }
}
