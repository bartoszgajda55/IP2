package group14.caledonian.ac.uk.quizapp.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by aaron on 09/03/2018.
 */

public abstract class FragmentedActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Log in Adapter inner class
    public static class LoginAdapter extends FragmentPagerAdapter {
        protected int NUM_ITEMS = 1;
        protected ArrayList<FragmentView> fragments;

        public LoginAdapter(FragmentManager fragmentManager, int number, ArrayList<FragmentView> fragments) {
            super(fragmentManager);
            this.NUM_ITEMS = number;
            this.fragments = fragments;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment;
                    return fragments.get(0);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return fragments.get(1);
                default:
                    return null;
            }
        }

    }
}