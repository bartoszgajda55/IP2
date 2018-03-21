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


    //todo move
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

        //Listen for search submit (click ENTER/RETURN)
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
            //TODO Load 5 featured quizzes from database -- FeaturedQuiz table, move to a new thread - make use of spinners
            QuizPreviewFragment quizPreview = new QuizPreviewFragment();
            Bundle featuredBundle = new Bundle();
            String featuredTitle = ("Featured Quiz "+(x+1)); //TODO Get quiz title from the database
            String featuredDesc = "Description";//TODO Get quiz description from database
            String featuredImg = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png"; //TODO Get quiz image from database
            int featuredColor = R.color.colorPrimary; //TODO Get quiz color from database
            featuredBundle.putString("title", featuredTitle);
            featuredBundle.putString("desc", featuredDesc);
            featuredBundle.putString("img", featuredImg);
            featuredBundle.putInt("color", featuredColor);
            quizPreview.setArguments(featuredBundle);
            fragments.add(quizPreview);
        }
        featuredAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragments.size(), fragments);
        featuredPager.setAdapter(featuredAdapter);

        ArrayList<Fragment> fragmentsRecentGrid = new ArrayList<>();
        for(int x=0; x<2; x++){
            RecentQuizFragment recentGrid = new RecentQuizFragment();
            fragmentsRecentGrid.add(recentGrid);

        }
        recentAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragmentsRecentGrid.size(), fragmentsRecentGrid);
        recentPager.setAdapter(recentAdapter);

        return view;
    }


}
