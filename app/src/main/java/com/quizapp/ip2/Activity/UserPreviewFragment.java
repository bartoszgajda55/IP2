package com.quizapp.ip2.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quizapp.ip2.R;

public class UserPreviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_preview, container, false);

        TextView username = (TextView) view.findViewById(R.id.username);
        TextView level = (TextView) view.findViewById(R.id.level);
        TextView place = (TextView) view.findViewById(R.id.place);

        username.setText(this.getArguments().getString("username"));
        level.setText(this.getArguments().getString("level"));
        place.setText(Integer.toString(this.getArguments().getInt("place")));

        return view;
    }
}

