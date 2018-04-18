package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.quizapp.ip2.Helper.LevelParser;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Helper.RequestTask2;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron on 10/03/2018.
 */

public class LeaderboardFragment extends Fragment {
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

        return view;
    }

    public void populatePage(){
        if(!pageLoaded) {
            final RequestTask rt = new RequestTask(new RequestTask.AsyncResponse() {
                @Override
                public void processFinish(String[] output) {
                    try {
                        JSONArray resultset = new JSONArray(output[1]);

                        for(int i = 0; i < resultset.length(); i++){
                            if(i < 101){
                                JSONObject result = resultset.getJSONObject(i);
                                UserPreviewFragment frag = new UserPreviewFragment();
                                Bundle bundle = new Bundle();
                                int place = i + 1;
                                String username = result.getString("Username");
                                String level = String.valueOf(new LevelParser(result.getInt("XP")).getLevel());

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
                        }

                    } catch (JSONException e){
                        Log.e("ERROR", "Invalid JSON");
                    }

                }
            });

            rt.sendGetRequest("user?term=XP&order=desc&limit=101", "GET");

            //Add current user's ranking/score to bottom of page
                UserPreviewFragment frag = new UserPreviewFragment();
                Bundle bundle = new Bundle();
                RequestTask2 rankingRequest = new RequestTask2();
                int place = 0;
                try {
                    String[] rankResponse = rankingRequest.sendGetRequest("user/" + UserHelper.getUser().getUserID() + "/ranking", "GET");
                    JSONObject jsonRanking = new JSONObject(rankResponse[1]);
                    place = jsonRanking.getInt("position")+1;
                } catch (JSONException e){
                    Log.e("JSON ERROR", "Error parsing json");
                }
                String username = "You";  //Username is hardcoded "You"
                String level = String.valueOf(new LevelParser(UserHelper.getUser().getXp()).getLevel());
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
    }

}
