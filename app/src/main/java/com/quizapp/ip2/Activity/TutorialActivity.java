package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.quizapp.ip2.Helper.AnimatedColor;
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

        ArrayList<Fragment> fragments = new ArrayList<>();
        for(int x=0; x<5; x++){
            TutorialFragment frag = new TutorialFragment();
            Bundle bundle = new Bundle();
            String title = ("Tutorial "+(x+1));
            String desc = "Description";
            String img = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png";
            bundle.putString("title", title);
            bundle.putString("desc", desc);
            bundle.putString("img", img);
            frag.setArguments(bundle);
            fragments.add(frag);
        }

        SliderAdapter sliderAdapter = new SliderAdapter(getSupportFragmentManager(), fragments.size(), fragments);
        viewPager.setAdapter(sliderAdapter);


        final AnimatedColor oneToTwo = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroNavy), ContextCompat.getColor(this, R.color.colorIntroDarkBlue));
        final AnimatedColor twoToThree = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroDarkBlue), ContextCompat.getColor(this, R.color.colorIntroBlue));
        final AnimatedColor threeToFour = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroBlue), ContextCompat.getColor(this, R.color.colorIntroGreen));
        final AnimatedColor fourToFive = new AnimatedColor(ContextCompat.getColor(this, R.color.colorIntroGreen), ContextCompat.getColor(this, R.color.colorIntroDarkGreen));


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

}
