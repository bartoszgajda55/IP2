package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.quizapp.ip2.Helper.DBHandler;
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
        final UserFragment userFragment = new UserFragment();
        final LeaderboardFragment leaderboardFragment = new LeaderboardFragment();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(userFragment);
        fragments.add(new HomeFragment());
        fragments.add(leaderboardFragment);

        SliderAdapter sliderAdapter = new SliderAdapter(getSupportFragmentManager(), 3, fragments);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(2);


        //
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0: //User
                        userFragment.populateFriends();
                        break;
                    case 2: //Leaderboard
                        leaderboardFragment.populatePage();
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //Add tab icons to the navigation bar
        addTabIcons();

        DBHandler dbHandler = new DBHandler();
        System.out.println(dbHandler.get("user/4").toString());

    }

    //Adds the white navigation icons to the tab bar
    private void addTabIcons(){
        for(int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
}