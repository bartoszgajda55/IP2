package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quizapp.ip2.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 10/03/2018.
 */

public class HomeFragment extends Fragment {

    private FragmentPagerAdapter featuredAdapter;
    private ViewPager featuredPager;

    @Nullable
   // @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        featuredPager = (ViewPager) view.findViewById(R.id.featuredSlider);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new QuizPreviewFragment());
        fragments.add(new QuizPreviewFragment());

        //FragmentedActivity fragmentedActivity = new FragmentedActivity();
        featuredAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragments.size(), fragments);

        featuredPager.setAdapter(featuredAdapter);


        return view;
    }



    //INSERT VIEWPAGER INSIDE THIS FRAGMENT
    //INSERT QUIZ FRAGMENTS INSIDE THIS VIEWPAGER



}
