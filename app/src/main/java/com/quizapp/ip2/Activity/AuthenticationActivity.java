package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.quizapp.ip2.R;

import java.util.ArrayList;

public class AuthenticationActivity extends FragmentedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        ViewPager pager = (ViewPager) findViewById(R.id.slider);
        TabLayout navigationDots = (TabLayout) findViewById(R.id.tabNavigationDots);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        FragmentPagerAdapter loginAdapter = new SliderAdapter(getSupportFragmentManager(), 2, fragments);
        pager.setAdapter(loginAdapter);
        navigationDots.setupWithViewPager(pager, true);

    }

}