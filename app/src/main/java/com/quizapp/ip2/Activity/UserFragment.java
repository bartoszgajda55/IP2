package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

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

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void populateFriends(){
        if(!friendsLoaded) {
            //TODO Add an async task for loading friends
            for (int x = 0; x < 30; x++) {
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
