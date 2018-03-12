package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import  com.quizapp.ip2.R;

/**
 * Created by Aaron on 11/03/2018.
 */

public class Quiz2Fragment extends FragmentedActivity {

    @Nullable
   // @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        return view;
    }
}
