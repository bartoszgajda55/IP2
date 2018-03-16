package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

/**
 * Created by Aaron on 10/03/2018.
 */

public class LeaderboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard_fragment, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        for (int x=0; x<100; x++){
            UserPreviewFragment frag = new UserPreviewFragment();
            Bundle bundle = new Bundle();
            int place = x;
            String username = "User "+1; //TODO get from database
            String level = ""; //TODO get from database
            bundle.putInt("place", place);
            bundle.putString("username", username);
            bundle.putString("level", level);
            frag.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(getContext());
            rel.setId(View.generateViewId());
            getFragmentManager().beginTransaction().add(rel.getId(),frag).commit();
            linearLayout.addView(rel);
        }

        return view;
    }

}
