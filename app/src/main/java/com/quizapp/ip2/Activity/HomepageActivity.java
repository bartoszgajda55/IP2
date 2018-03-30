package com.quizapp.ip2.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

import java.util.ArrayList;

public class HomepageActivity extends FragmentedActivity {

    private RelativeLayout parent;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] icons = {
            R.drawable.tabicon_user ,R.drawable.tabicon_home, R.drawable.tabicon_leaderboard
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        parent = (RelativeLayout) findViewById(R.id.parentLayout);
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                parent.requestFocus();
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
            public void onPageSelected(int position) {

            }


            //Prevent virtual keyboard persisting when page using keyboard is swiped away from
            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){
                    final InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                }


            }
        });

        //Add tab icons to the navigation bar
        addTabIcons();
    }

    //Adds the white navigation icons to the tab bar
    private void addTabIcons(){
        for(int i = 0; i < tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }

}