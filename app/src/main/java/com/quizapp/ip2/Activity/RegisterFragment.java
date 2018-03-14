package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quizapp.ip2.R;

/**
 * Created by aaron on 08/03/2018.
 */

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        return view;
    }


    public static Fragment newInstance(int page) {
        RegisterFragment regFragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        regFragment.setArguments(args);
        return regFragment;
    }

}