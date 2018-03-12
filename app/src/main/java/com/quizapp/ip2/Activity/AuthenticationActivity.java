package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import  com.quizapp.ip2.R;
import java.util.ArrayList;

public class AuthenticationActivity extends FragmentedActivity {

    private FragmentPagerAdapter loginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);



        ViewPager pager = (ViewPager) findViewById(R.id.slider);
        ArrayList<FragmentView> fragments = new ArrayList<FragmentView>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        loginAdapter = new SliderAdapter(getSupportFragmentManager(), 2, fragments);
        pager.setAdapter(loginAdapter);

    }

}