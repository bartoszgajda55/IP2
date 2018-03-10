package group14.caledonian.ac.uk.quizapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group14.caledonian.ac.uk.quizapp.R;

/**
 * Created by aaron on 08/03/2018.
 */

public class RegisterFragment extends FragmentView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        return view;
    }


    public static FragmentView newInstance(int page) {
        RegisterFragment regFragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        regFragment.setArguments(args);
        return regFragment;
    }

}
