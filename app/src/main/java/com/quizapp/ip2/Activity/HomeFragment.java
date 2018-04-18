package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Helper.RequestTask2;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aaron on 10/03/2018.
 */

public class HomeFragment extends Fragment {


    private FragmentPagerAdapter featuredAdapter;
    private FragmentPagerAdapter recentAdapter;
    private ViewPager featuredPager;
    private ViewPager recentPager;
    private TabLayout featuredNavigationDots;
    private TabLayout recentNavigationDots;
    private Button btnBrowseAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        featuredPager = (ViewPager) view.findViewById(R.id.featuredSlider);
        recentPager = (ViewPager) view.findViewById(R.id.recentSlider);
        featuredNavigationDots = (TabLayout) view.findViewById(R.id.tabFeaturedNavigationDots);
        recentNavigationDots = (TabLayout) view.findViewById(R.id.tabRecentNavigationDots);
        final EditText searchText = (EditText) view.findViewById(R.id.txtSearch);
        btnBrowseAll = (Button) view.findViewById(R.id.btnBrowseAll);

        //Listen for search submit (click ENTER/RETURN)
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            Intent intent = new Intent(getContext(), QuizSearchActivity.class);
                            Bundle b = new Bundle();
                            b.putString("search", searchText.getText().toString());
                            //"all" key is a boolean value, if true, will show every quiz in the database. Else the query will be used.
                            b.putBoolean("all", false);
                            intent.putExtras(b);
                            startActivity(intent);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });



        //Listen for button click to show all quizzes
        btnBrowseAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), QuizSearchActivity.class);
                Bundle b = new Bundle();
                b.putString("search", "all");
                //"all" key is a boolean value, if true, will show every quiz in the database. Else the query will be used.
                b.putBoolean("all", true);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //To display the featured quizzes in a slider

        loadFeatured();
        loadRecent();
    }


    public void loadFeatured(){
        final ArrayList<Integer> featuredId = new ArrayList<>();

        RequestTask featuredResponse = new RequestTask(new RequestTask.AsyncResponse() {
            @Override
            public void processFinish(String[] response) {
                final ArrayList<Fragment> fragmentsFeatured = new ArrayList<Fragment>();
                try {

                    JSONArray jsonFeaturedQuizzesArray = new JSONArray(response[1]);
                    for(int i = 0; i < jsonFeaturedQuizzesArray.length(); i++){

                        JSONArray jsonFeaturedQuizArray = new JSONArray(response[1]);
                        JSONObject jsonFeaturedQuiz = jsonFeaturedQuizArray.getJSONObject(i);
                        int quizId = jsonFeaturedQuiz.getInt("QuizID");
                        featuredId.add(quizId);
                    }
                } catch (JSONException e){
                    Log.e("ERROR", "Invalid JSON");
                }
                    for(int i=0; i<featuredId.size(); i++){
                        RequestTask ft = new RequestTask(new RequestTask.AsyncResponse() {
                            @Override
                            public void processFinish(String[] output) {
                                try {
                                    JSONObject jsonQuiz = new JSONObject(output[1]);

                                    QuizPreviewFragment quizPreview = new QuizPreviewFragment();
                                    Bundle featuredBundle = new Bundle();
                                    String featuredTitle = jsonQuiz.getString("QuizName");
                                    String featuredDesc = jsonQuiz.getString("QuizDescription");
                                    String featuredImg = jsonQuiz.getString("QuizImage");
                                    int featuredId = jsonQuiz.getInt("QuizID");
                                    int featuredColor = Color.parseColor("#" + jsonQuiz.getString("QuizColor"));

                                    featuredBundle.putString("title", featuredTitle);
                                    featuredBundle.putString("desc", featuredDesc);
                                    featuredBundle.putString("img", featuredImg);
                                    featuredBundle.putInt("id", featuredId);
                                    featuredBundle.putInt("color", featuredColor);

                                    quizPreview.setArguments(featuredBundle);
                                    fragmentsFeatured.add(quizPreview);

                                    featuredAdapter = new FragmentedActivity.SliderAdapter(getChildFragmentManager(), fragmentsFeatured.size(), fragmentsFeatured);
                                    featuredPager.setAdapter(featuredAdapter);
                                }catch (JSONException e){
                                    Log.e("JSON ERROR", "Bad JSON");
                                    e.printStackTrace();
                                }
                            }
                            });
                        ft.sendGetRequest("quiz/"+featuredId.get(i), "GET");
                        }
                    featuredNavigationDots.setupWithViewPager(featuredPager, true);
                    }
            });
         featuredResponse.sendGetRequest("featuredQuiz", "GET");

    }

    private void loadRecent(){
        ArrayList<Fragment> fragmentsRecentGrid = new ArrayList<>();
        JSONObject jsonUser = new JSONObject();
        try {
            jsonUser.put("UserID", UserHelper.getUser().getUserID());

            RequestTask2 rt2 = new RequestTask2();
            String[] response = rt2.sendGetRequest("recentQuiz/"+UserHelper.getUser().getUserID(), "GET");
            if(response[0].equals("200")){

                JSONArray recentQuizArray = new JSONArray(response[1]);

                RecentQuizGridFragment recentGrid = new RecentQuizGridFragment();

                RecentQuizGridFragment recentGrid2;
                ArrayList<Integer> grid2 = new ArrayList<>();
                recentGrid2 = new RecentQuizGridFragment();
                ArrayList<Integer> grid1 = new ArrayList<>();

                for(int i=0; i<recentQuizArray.length(); i++){

                    if(i>=4){
                        int id = recentQuizArray.getJSONObject(i).getInt("QuizID");
                        grid2.add(id);
                    }else{
                        int id = recentQuizArray.getJSONObject(i).getInt("QuizID");
                        grid1.add(id);
                    }
                }

                Bundle bundle1 = new Bundle();
                bundle1.putIntegerArrayList("list", grid1);
                recentGrid.setArguments(bundle1);
                fragmentsRecentGrid.add(recentGrid);

                if(recentQuizArray.length()>4){
                    Bundle bundle2 = new Bundle();
                    bundle2.putIntegerArrayList("list", grid2);
                    recentGrid2.setArguments(bundle2);
                    fragmentsRecentGrid.add(recentGrid2);
                }

                recentAdapter = new FragmentedActivity.SliderAdapter(getChildFragmentManager(), fragmentsRecentGrid.size(), fragmentsRecentGrid);
                recentPager.setAdapter(recentAdapter);

                recentNavigationDots.setupWithViewPager(recentPager, true);
                if(recentNavigationDots.getTabCount() < 2){
                    recentNavigationDots.setVisibility(View.INVISIBLE);
                }
            }else{
                Log.e("ERROR", "No user");
            }
        }catch (JSONException e){
            Log.e("JSON ERROR", "Bad JSON");
            e.printStackTrace();
        }
    }
}
