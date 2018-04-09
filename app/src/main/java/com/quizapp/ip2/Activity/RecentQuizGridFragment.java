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

import com.quizapp.ip2.R;


/**
 * Created by Allan on 14/03/2018.
 */

public class RecentQuizGridFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_quiz_fragment, container, false);
        GridLayout gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        for(int x=0; x<4; x++) {
            //TODO Load recent quizzes from database
            RecentQuizPreviewFragment quizRecent = new RecentQuizPreviewFragment();
            Bundle recentBundle = new Bundle();
            String recentTitle = "Title"; //TODO Get quiz title from database
            String recentImg = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4QvV3dBSyaOLlo6SzULcSh-TflvDUjua3lvZ69UcFMc5BiZBIJg"; //TODO Get quiz image from database
            int recentColor = Color.parseColor("#3d3d3d"); //TODO Get quiz color from database
            recentBundle.putString("title", recentTitle);
            recentBundle.putString("img", recentImg);
            recentBundle.putInt("color", recentColor);

            quizRecent.setArguments(recentBundle);

            RelativeLayout rel = new RelativeLayout(getContext());
            rel.setId(View.generateViewId());
            getFragmentManager().beginTransaction().add(rel.getId(), quizRecent).commit();
            gridLayout.addView(rel,x);
        }
        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }

}
