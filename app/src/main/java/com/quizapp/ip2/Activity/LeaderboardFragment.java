package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

/**
 * Created by Aaron on 10/03/2018.
 */

public class LeaderboardFragment extends Fragment {

    //TODO Add a spinner before loading the leaderboards, only start loading leaderboards when the page is swiped onto. Do it on a different thread

    LinearLayout linearLayout;
    LinearLayout ownLayout;
    ProgressBar progressBar;
    boolean pageLoaded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard_fragment, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        ownLayout = (LinearLayout) view.findViewById(R.id.layoutOwn);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        //Load top 100 users from the database
        //TODO Query database for users, ranked by descending order for XP


        return view;
    }

    public void populatePage(){
        if(!pageLoaded) {
            Thread loadLeaderboard = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int x = 0; x < 101; x++) {
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
                        linearLayout.addView(rel);
                    }

                    //Add current user's ranking/score to bottom of page
                    UserPreviewFragment frag = new UserPreviewFragment();
                    Bundle bundle = new Bundle();
                    int place = 201; //TODO Calculate user's place in the world

                    String username = "You";  //Username is hardcoded "You"
                    String level = "1"; //TODO Calculate user's level from XP
                    bundle.putInt("place", place);
                    bundle.putString("username", username);
                    bundle.putString("level", level);

                    bundle.putInt("color", R.color.colorPrimary);
                    bundle.putInt("textColor", R.color.colorLight);
                    bundle.putFloat("alpha", 1F);

                    frag.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(ownLayout.getId(), frag).commit();


                    progressBar.setVisibility(View.INVISIBLE);
                    pageLoaded = true;

                }
            });
            loadLeaderboard.start();



        }
    }

}
