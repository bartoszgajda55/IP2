package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.quizapp.ip2.Helper.AnimatedColor;
import com.quizapp.ip2.Model.Tutorial;
import com.quizapp.ip2.R;

import java.util.ArrayList;

/**
 * Created by Allan on 15/03/2018.
 */

public class TutorialActivity extends FragmentedActivity {

    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        viewPager = (ViewPager) findViewById(R.id.slider);

        ArrayList<Tutorial> tutorials = new ArrayList<>();
        Tutorial tut1 = new Tutorial("Welcome to "+getResources().getString(R.string.app_name),
                getResources().getString(R.string.tutorial_1), R.drawable.logo_inverted_small);
        Tutorial tut2 = new Tutorial("Homepage",
                getResources().getString(R.string.tutorial_2),R.drawable.tabicon_home);
        Tutorial tut3 = new Tutorial("User Page",
                getResources().getString(R.string.tutorial_3),R.drawable.tabicon_user);
        Tutorial tut4 = new Tutorial("Quizzes",
                getResources().getString(R.string.tutorial_4),R.drawable.icon_level);
        Tutorial tut5 = new Tutorial("Leaderboard",
                getResources().getString(R.string.tutorial_5),R.drawable.tabicon_leaderboard);
        tutorials.add(tut1);
        tutorials.add(tut2);
        tutorials.add(tut3);
        tutorials.add(tut4);
        tutorials.add(tut5);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for(int x=0; x<5; x++){
            TutorialFragment frag = new TutorialFragment();
            Bundle bundle = new Bundle();
            String title = (tutorials.get(x).getTitle());
            String desc = (tutorials.get(x).getDesc());
            int img = (tutorials.get(x).getImg());
            bundle.putString("title", title);
            bundle.putString("desc", desc);
            bundle.putInt("img", img);
            frag.setArguments(bundle);
            fragments.add(frag);
        }

        SliderAdapter sliderAdapter = new SliderAdapter(getSupportFragmentManager(), fragments.size(), fragments);
        viewPager.setAdapter(sliderAdapter);

        final AnimatedColor oneToTwo = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroNavy), ContextCompat.getColor(this, R.color.colorIntroDarkBlue));
        final AnimatedColor twoToThree = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroDarkBlue), ContextCompat.getColor(this, R.color.colorIntroBlue));
        final AnimatedColor threeToFour = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroBlue), ContextCompat.getColor(this, R.color.colorIntroGreen));
        final AnimatedColor fourToFive = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroGreen), ContextCompat.getColor(this, R.color.colorIntroDarkGreen));

        //Set status bar to darker color variant
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorDarkGray));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0: //welcome
                        viewPager.setBackgroundColor(oneToTwo.with(positionOffset));
                        break;
                    case 1: //
                        viewPager.setBackgroundColor(twoToThree.with(positionOffset));
                        break;
                    case 2: //
                        viewPager.setBackgroundColor(threeToFour.with(positionOffset));
                        break;
                    case 3: //
                        viewPager.setBackgroundColor(fourToFive.with(positionOffset));
                        break;
                    case 4: //
                        viewPager.setBackgroundColor(ContextCompat.getColor(TutorialActivity.this, R.color.colorIntroDarkGreen));
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void onSkipClick(View v){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);


    }

}
