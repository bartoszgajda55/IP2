package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        featuredPager = (ViewPager) view.findViewById(R.id.featuredSlider);
        recentPager = (ViewPager) view.findViewById(R.id.recentSlider);
        final EditText searchText = (EditText) view.findViewById(R.id.txtSearch);

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            Intent intent = new Intent(getContext(), QuizSearchActivity.class);
                            Bundle b = new Bundle();
                            b.putString("search", searchText.getText().toString());
                            intent.putExtras(b);
                            startActivity(intent);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        //To display the featured quizzes in a slider
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        for(int x=0; x<5; x++){
            //TODO Load 5 featured quizes from database
            QuizPreviewFragment quizPreview = new QuizPreviewFragment();
            Bundle bundle = new Bundle();
            String title = ("Featured Quiz "+(x+1)); //TODO get from database
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

         //To display the recent quizzes in a slider with a grid layout
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


}
