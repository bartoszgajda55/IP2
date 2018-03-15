package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;


import com.quizapp.ip2.R;

import java.util.ArrayList;

/**
 * Created by Aaron on 10/03/2018.
 */

public class HomeFragment extends Fragment {


    private FragmentPagerAdapter featuredAdapter;
    private FragmentPagerAdapter recentAdapter;
    private ViewPager featuredPager;
    private ViewPager recentPager;
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        featuredPager = (ViewPager) view.findViewById(R.id.featuredSlider);
        recentPager = (ViewPager) view.findViewById(R.id.recentSlider);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for(int x=0; x<5; x++){
            //TODO Load 5 featured quizes from database
            QuizPreviewFragment quizPreview = new QuizPreviewFragment();
            Bundle bundle = new Bundle();
            String title = "Title"; //TODO get from database
            String desc = "Description";//TODO get from database
            String img = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png"; //TODO get from database
            String color = ""; //TODO get from database
            bundle.putString("title", title);
            bundle.putString("desc", desc);
            bundle.putString("img", img);
            bundle.putString("color", color);
            quizPreview.setArguments(bundle);
            fragments.add(quizPreview);
        }
        featuredAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragments.size(), fragments);
        featuredPager.setAdapter(featuredAdapter);

        /**
        //TODO for each recent quiz from database do the following
        for (int x=0; x<3; x++){
            QuizPreviewFragment frag = new QuizPreviewFragment();
            Bundle bundle = new Bundle();
            String title = "Title 123"; //TODO get from database
            String desc = "Description"; //TODO get from database
            String img = "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_80%2Cw_300/MTE4MDAzNDEwODgzMDIwMzAy/bobby-sands-20941955-1-402.jpg"; //TODO get from database
            String color = ""; //TODO get from database
            bundle.putString("title", title);
            bundle.putString("desc", desc);
            bundle.putString("img", img);
            bundle.putString("color", color);
            frag.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(getContext());
            rel.setId(View.generateViewId());
            getFragmentManager().beginTransaction().add(rel.getId(), frag).commit();
            linearLayout.addView(rel);
        }**/

            ArrayList<Fragment> fragmentsRecent = new ArrayList<Fragment>();
            //TODO Load 5 featured quizes from database
            for(int x=0; x<3; x++){
                RecentQuizFragment quizRecent = new RecentQuizFragment();
                Bundle bundle = new Bundle();
                String title = "Title"; //TODO get from database
                String desc = "Description";//TODO get from database
                String img = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png"; //TODO get from database
                String color = ""; //TODO get from database
                bundle.putString("title", title);
                bundle.putString("desc", desc);
                bundle.putString("img", img);
                bundle.putString("color", color);
                quizRecent.setArguments(bundle);
                fragmentsRecent.add(quizRecent);
            }
        recentAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragmentsRecent.size(), fragmentsRecent);
        recentPager.setAdapter(recentAdapter);
        return view;
    }



    //TODO INSERT VIEWPAGER INSIDE THIS FRAGMENT
    //TODO INSERT QUIZ FRAGMENTS INSIDE THIS VIEWPAGER



}
