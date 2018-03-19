package com.quizapp.ip2.Activity;

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


//todo refactor this class - should be called RecentQuizGridFragment

public class RecentQuizFragment extends Fragment {
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_quiz_fragment, container, false);
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);



        //ArrayList<Fragment> fragmentsRecent = new ArrayList<Fragment>();
        for(int x=0; x<4; x++) {
            //TODO Load 5 featured quizes from database
            RecentQuizPreviewFragment quizRecent = new RecentQuizPreviewFragment();
            Bundle recentBundle = new Bundle();
            String recentTitle = "Title"; //TODO Get quiz title from database
            String recentImg = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png"; //TODO Get quiz image from database
            int recentColor = R.color.colorIntroBlue; //TODO Get quiz color from database
            recentBundle.putString("title", recentTitle);
            recentBundle.putString("img", recentImg);
            recentBundle.putInt("color", recentColor);

            quizRecent.setArguments(recentBundle);

            RelativeLayout rel = new RelativeLayout(getContext());
            rel.setId(View.generateViewId());
            getFragmentManager().beginTransaction().add(rel.getId(), quizRecent).commit();
            gridLayout.addView(rel,x);
        }
        return view;
    }

}
