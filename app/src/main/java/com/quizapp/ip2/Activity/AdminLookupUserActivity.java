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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLookupUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lookup);

        Button btnSave = (Button) findViewById(R.id.btnSave);

        final Bundle args = getIntent().getExtras();

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
        final Switch switchBanned = (Switch) findViewById(R.id.switchBanned);

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
        txtRanking.setText(String.valueOf(args.getInt("ranking") + 1));
        txtQuizzesComplete.setText(String.valueOf(args.getInt("quizzescomplete")));
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
            public void onClick(View view) {
                final PostTask pt = new PostTask();

                final JSONObject jsonUser = new JSONObject();
                final JSONObject jsonBan = new JSONObject();
                try {
                    int boolAdmin = (switchAdmin.isChecked()) ? 1 : 0;
                    jsonUser.put("adminstatus", boolAdmin);

                    String[] adminResponse = pt.sendPostRequest("user/" + txtUserId.getText().toString() + "/edit", jsonUser.toString(), "POST");
                    if(!adminResponse[0].equals("200")){
                        Toast.makeText(getApplicationContext(), "Error finding user...", Toast.LENGTH_SHORT).show();
                    }

                    int updatedBanned = (switchBanned.isChecked()) ? 1 : 0;
                    int bundleBanned = (args.getBoolean("banned")) ? 1 : 0;

                    if(bundleBanned == 1 && updatedBanned == 0){
                        //Unban
                        String[] unbanResponse = new RequestTask().sendGetRequest("blacklist/" + txtUserId.getText().toString(),"DELETE");
                        if(unbanResponse[0].equals("500")){
                            Toast.makeText(getApplicationContext(), "Error unbanning user...", Toast.LENGTH_SHORT).show();
                        }


                    }else if(bundleBanned == 0 && updatedBanned == 1){
                        //Ban
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminLookupUserActivity.this);
                        builder.setTitle("Ban Reason");

                        final EditText txtBanReason = new EditText(AdminLookupUserActivity.this);
                        txtBanReason.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                        LinearLayout linearLayout = new LinearLayout(AdminLookupUserActivity.this);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        int paddingDp = 20;
                        int paddingPx = (int)(paddingDp * getResources().getDisplayMetrics().density);
                        linearLayout.setPadding(paddingPx, 0, paddingPx, 0);

                        linearLayout.addView(txtBanReason);
                        builder.setView(linearLayout);

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                try {
                                    jsonBan.put("userid", txtUserId.getText().toString());
                                    jsonBan.put("reason", txtBanReason.getText().toString());
                                } catch (JSONException e){
                                    Log.e("JSON ERROR", "Error parsing json");
                                }

                                String banResponse[] = pt.sendPostRequest("blacklist", jsonBan.toString(), "POST");
                                if(!banResponse[0].equals("201")){
                                    Toast.makeText(getApplicationContext(), "Error banning user...", Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(getApplicationContext(), "Changes saved...", Toast.LENGTH_SHORT).show();
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                            }
                        });

                        AlertDialog ad = builder.create();
                        ad.show();
                    }else{

                    Toast.makeText(getApplicationContext(), "Changes saved...", Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);}
                } catch (JSONException e){
                    Log.e("JSON ERROR", "Bad JSON");
                }

            }
        });

    }
}
