package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aaron on 09/03/2018.
 */

public class FragmentView extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getView();
        return view;
    }

    public static FragmentView newInstance(int page) {
        FragmentView logFragment = new FragmentView();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        logFragment.setArguments(args);
        return logFragment;

    }


}