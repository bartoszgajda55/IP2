package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.quizapp.ip2.R;

import java.util.ArrayList;

public class HomepageActivity extends FragmentedActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] icons = {
            R.drawable.tabicon_user ,R.drawable.tabicon_home, R.drawable.tabicon_leaderboard
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        viewPager = (ViewPager) findViewById(R.id.slider);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Add the 3 fragments for the homepage (User section, home section, leaderboard section)
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new UserFragment());
        fragments.add(new HomeFragment());
        fragments.add(new LeaderboardFragment());

        SliderAdapter sliderAdapter = new SliderAdapter(getSupportFragmentManager(), 3, fragments);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setCurrentItem(1);
        addTabIcons();
    }

    //Adds the white navigation icons to the tab bar
    private void addTabIcons(){
        for(int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
}