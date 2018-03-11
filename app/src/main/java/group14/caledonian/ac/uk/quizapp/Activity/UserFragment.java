package group14.caledonian.ac.uk.quizapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group14.caledonian.ac.uk.quizapp.R;

/**
 * Created by Aaron on 10/03/2018.
 */

public class UserFragment extends FragmentView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        return view;
    }
}
