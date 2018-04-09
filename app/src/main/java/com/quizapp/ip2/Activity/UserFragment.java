package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quizapp.ip2.Helper.LevelParser;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.R;

import java.util.logging.Level;

/**
 * Created by Aaron on 10/03/2018.
 */

public class UserFragment extends Fragment {


    ProgressBar progressBar;
    LinearLayout friendsLayout;
    boolean friendsLoaded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        friendsLayout = (LinearLayout) view.findViewById(R.id.friendLinearLayout);
        Button btnSettings = (Button) view.findViewById(R.id.btnSettings);
        Button btnAdmin = (Button) view.findViewById(R.id.btnAdmin);

        TextView txtUsername = (TextView) view.findViewById(R.id.txtUsername);
        ImageView imgUser = (ImageView) view.findViewById(R.id.imgUserIcon);
        TextView txtEmail =(TextView) view.findViewById(R.id.txtEmail);
        TextView txtLevel =(TextView) view.findViewById(R.id.txtLevel);
        TextView txtFirstname =(TextView) view.findViewById(R.id.txtFirstname);
        TextView txtSurname =(TextView) view.findViewById(R.id.txtSurname);

        TextView txtQuizzesComplete =(TextView) view.findViewById(R.id.txtQuizzesComplete);
        TextView txtCorrectAnswers =(TextView) view.findViewById(R.id.txtCorrectAnswers);

        //SET VALUES
        txtUsername.setText(UserHelper.getUser().getUsername());
        txtEmail.setText(UserHelper.getUser().getEmail());

        //Calculate level and then set
        LevelParser lvl = new LevelParser(UserHelper.getUser().getXp());
        int level = lvl.getLevel();
        txtLevel.setText(Integer.toString(level));

        txtFirstname.setText("Firstname: "+UserHelper.getUser().getFirstName());
        txtSurname.setText("Surname: "+UserHelper.getUser().getSurname());
        txtCorrectAnswers.setText("Correct Answers: "+Integer.toString(UserHelper.getUser().getCorrectAnswers()));
        txtQuizzesComplete.setText("Quizzess Completed: "+Integer.toString(UserHelper.getUser().getQuizzessCompleted()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        progressBar.setProgress(lvl.nextLevel());

        //IF USEER IS NOT ADMIN MAKE btnAdmin invisible
        if (UserHelper.getUser().getAdminStatus()==0){
            btnAdmin.setVisibility(View.INVISIBLE);
        }

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void populateFriends(){
        if(!friendsLoaded) {
            //TODO Add an async task for loading friends
            for (int x = 0; x < 12; x++) {
                UserPreviewFragment frag = new UserPreviewFragment();
                Bundle bundle = new Bundle();
                int place = x + 1;
                String username = "User " + 1; //TODO get username from user query result
                String level = "10"; //TODO calculate level from user query result (XP)
                bundle.putInt("place", place);
                bundle.putString("username", username);
                bundle.putString("level", level);
                bundle.putInt("color", R.color.colorLightGray);
                bundle.putInt("textColor", R.color.colorDarkGray);
                bundle.putFloat("alpha", 0.25F);

                frag.setArguments(bundle);
                RelativeLayout rel = new RelativeLayout(getContext());
                rel.setId(View.generateViewId());
                getFragmentManager().beginTransaction().add(rel.getId(), frag).commit();
                friendsLayout.addView(rel);
            }

            progressBar.setVisibility(View.INVISIBLE);
            friendsLoaded = true;

        }
    }
}
