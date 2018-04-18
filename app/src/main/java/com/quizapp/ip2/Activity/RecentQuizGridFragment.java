package com.quizapp.ip2.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.quizapp.ip2.Helper.RequestTask2;
import com.quizapp.ip2.R;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Allan on 14/03/2018.
 */

public class RecentQuizGridFragment extends Fragment {

    GridLayout gridLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_quiz_fragment, container, false);
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        for(int i=0; i<getArguments().getIntegerArrayList("list").size(); i++) {

            try {
                String[] quizResponse = new RequestTask2().sendGetRequest("quiz/" + getArguments().getIntegerArrayList("list").get(i), "GET");
                JSONObject jsonQuiz = new JSONObject(quizResponse[1]);

                RecentQuizPreviewFragment quizRecent = new RecentQuizPreviewFragment();
                Bundle recentBundle = new Bundle();
                int recentId = jsonQuiz.getInt("QuizID");
                String recentTitle = jsonQuiz.getString("QuizName");
                String recentDescription = jsonQuiz.getString("QuizDescription");
                String recentImg = jsonQuiz.getString("QuizImage");
                int recentColor = Color.parseColor("#" + jsonQuiz.getString("QuizColor"));
                recentBundle.putInt("id", recentId);
                recentBundle.putString("title", recentTitle);
                recentBundle.putString("desc", recentDescription);
                recentBundle.putString("img", recentImg);
                recentBundle.putInt("color", recentColor);

                quizRecent.setArguments(recentBundle);

                RelativeLayout rel = new RelativeLayout(getContext());
                rel.setId(View.generateViewId());
                getFragmentManager().beginTransaction().add(rel.getId(), quizRecent).commit();
                gridLayout.addView(rel, i);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
