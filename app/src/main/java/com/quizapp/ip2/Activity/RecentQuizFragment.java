package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;


/**
 * Created by Allan on 14/03/2018.
 */

public class RecentQuizFragment extends Fragment {
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_quiz_fragment, container, false);
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);

        for(int x=0; x<4; x++) {
            //TODO Load 5 featured quizes from database
            RecentQuizPreviewFragment quizPreview = new RecentQuizPreviewFragment();
            Bundle bundle = new Bundle();
            String title = "Title"; //TODO get from database
            String img = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png"; //TODO get from database
            String color = ""; //TODO get from database
            bundle.putString("title", title);
            bundle.putString("img", img);
            bundle.putString("color", color);
            quizPreview.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(getContext());
            rel.setId(View.generateViewId());
            getFragmentManager().beginTransaction().add(rel.getId(), quizPreview).commit();
            gridLayout.addView(rel,x);
        }
        return view;
    }
}
