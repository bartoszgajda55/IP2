package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

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
    }

}
