package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import  com.quizapp.ip2.R;

/**
 * Created by Aaron on 10/03/2018.
 */

public class HomeFragment extends FragmentView {

    private FragmentPagerAdapter sliderAdapter;

    @Nullable
   // @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ViewPager featuredPager = (ViewPager) view.findViewById(R.id.featuredSlider);

        ArrayList<FragmentedActivity> fragments = new ArrayList<FragmentedActivity>();
        fragments.add(new Quiz1Fragment());
        fragments.add(new Quiz2Fragment());

        //FragmentedActivity fragmentedActivity = new FragmentedActivity();
        //sliderAdapter = new FragmentedActivity.SliderAdapter(fragmentedActivity.getSupportFragmentManager(), 2, fragments);

        featuredPager.setAdapter(sliderAdapter);


        return view;
    }

    //INSERT VIEWPAGER INSIDE THIS FRAGMENT
    //INSERT QUIZ FRAGMENTS INSIDE THIS VIEWPAGER



}
