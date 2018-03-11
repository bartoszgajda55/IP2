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
    public static class SliderAdapter extends FragmentPagerAdapter {
        protected int NUM_ITEMS = 1;
        protected ArrayList<FragmentView> fragments;

        public SliderAdapter(FragmentManager fragmentManager, int number, ArrayList<FragmentView> fragments) {
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

            for(int i = 0; i < fragments.size(); i++){
                if(i == position){
                    return fragments.get(i);
                }
            }
            return null;
        }

    }
}