package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private TabLayout featuredNavigationDots;
    private TabLayout recentNavigationDots;
    private Button btnBrowseAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        featuredPager = (ViewPager) view.findViewById(R.id.featuredSlider);
        recentPager = (ViewPager) view.findViewById(R.id.recentSlider);
        featuredNavigationDots = (TabLayout) view.findViewById(R.id.tabFeaturedNavigationDots);
        recentNavigationDots = (TabLayout) view.findViewById(R.id.tabRecentNavigationDots);
        final EditText searchText = (EditText) view.findViewById(R.id.txtSearch);
        btnBrowseAll = (Button) view.findViewById(R.id.btnBrowseAll);

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
                            //"all" key is a boolean value, if true, will show every quiz in the database. Else the query will be used.
                            b.putBoolean("all", false);
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
            String featuredImg = "https://d30y9cdsu7xlg0.cloudfront.net/png/36442-200.png"; //TODO Get quiz image from database
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
        featuredNavigationDots.setupWithViewPager(featuredPager, true);


        //Display recent quizzes in a slider
        ArrayList<Fragment> fragmentsRecentGrid = new ArrayList<>();
        for(int x=0; x<2; x++){
            RecentQuizGridFragment recentGrid = new RecentQuizGridFragment();
            fragmentsRecentGrid.add(recentGrid);

        }
        recentAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragmentsRecentGrid.size(), fragmentsRecentGrid);
        recentPager.setAdapter(recentAdapter);
        recentNavigationDots.setupWithViewPager(recentPager, true);


        //Listen for button click to show all quizzes
        btnBrowseAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), QuizSearchActivity.class);
                Bundle b = new Bundle();
                b.putString("search", "all");
                //"all" key is a boolean value, if true, will show every quiz in the database. Else the query will be used.
                b.putBoolean("all", true);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        return view;
    }


}
