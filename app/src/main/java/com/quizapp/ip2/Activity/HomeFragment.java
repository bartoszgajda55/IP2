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
import android.widget.RelativeLayout;

import com.quizapp.ip2.Helper.RequestTask;
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



        //To display the featured quizzes in a slider
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();

        final RequestTask rt = new RequestTask();

        try {
            String[] featuredResponse = rt.sendGetRequest("featuredQuiz", "GET");
            JSONArray jsonFeaturedQuizzesArray = new JSONArray(featuredResponse[1]);
            for(int i = 0; i < jsonFeaturedQuizzesArray.length(); i++){

                JSONArray jsonFeaturedQuizArray = new JSONArray(featuredResponse[1]);
                JSONObject jsonFeaturedQuiz = jsonFeaturedQuizArray.getJSONObject(i);
                int quizId = jsonFeaturedQuiz.getInt("QuizID");

                String[] quizResponse = rt.sendGetRequest("quiz/"+quizId, "GET");
                System.out.println("//RESPONSE// "+quizResponse[1]);
                JSONObject jsonQuiz = new JSONObject(quizResponse[1]);

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
                fragments.add(quizPreview);

            }
        } catch (JSONException e){
            Log.e("ERROR", "Invalid JSON");
        }


        featuredAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragments.size(), fragments);
        featuredPager.setAdapter(featuredAdapter);
        featuredNavigationDots.setupWithViewPager(featuredPager, true);
        if(featuredNavigationDots.getTabCount() < 2){
            featuredNavigationDots.setVisibility(View.INVISIBLE);
        }

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

        ArrayList<Fragment> fragmentsRecentGrid = new ArrayList<>();
        RecentQuizGridFragment recentGrid = new RecentQuizGridFragment();
        fragmentsRecentGrid.add(recentGrid);

        JSONObject jsonUser = new JSONObject();
        try {
            jsonUser.put("UserID", UserHelper.getUser().getUserID());

            RequestTask rt = new RequestTask();
            String[] response = rt.sendGetRequest("recentQuiz/"+UserHelper.getUser().getUserID(), "GET");
            if(response[0].equals("200")){

                JSONArray recentQuizArray = new JSONArray(response[1]);
                Bundle page1bundle = new Bundle();
                page1bundle.putInt("Quiz1ID", );
                page1bundle.putInt("Quiz2ID", );
                page1bundle.putInt("Quiz3ID", );
                page1bundle.putInt("Quiz4ID", );

                if(recentQuizArray.length() > 4 ){
                    RecentQuizGridFragment recentGrid2 = new RecentQuizGridFragment();
                    fragmentsRecentGrid.add(recentGrid2);
                }

                Log.e("array", recentQuizArray.toString());
                for(int i=0; i<recentQuizArray.length(); i++) {
                    JSONObject jsonRecentQuiz = recentQuizArray.getJSONObject(i);


                    String[] quizResponse = new RequestTask().sendGetRequest("quiz/" + jsonRecentQuiz.getInt("QuizID"), "GET");
                    JSONObject jsonQuiz = new JSONObject(quizResponse[1]);

                    RecentQuizPreviewFragment quizRecent = new RecentQuizPreviewFragment();
                    Bundle recentBundle = new Bundle();
                    int recentId = jsonQuiz.getInt("QuizID");
                    String recentTitle = jsonQuiz.getString("QuizName");
                    String recentImg = jsonQuiz.getString("QuizImage");
                    int recentColor = Color.parseColor("#" + jsonQuiz.getString("QuizColor"));
                    recentBundle.putInt("id", recentId);
                    recentBundle.putString("title", recentTitle);
                    recentBundle.putString("img", recentImg);
                    recentBundle.putInt("color", recentColor);

                    quizRecent.setArguments(recentBundle);

                    RelativeLayout rel = new RelativeLayout(getContext());
                    rel.setId(View.generateViewId());
                    getFragmentManager().beginTransaction().add(rel.getId(), quizRecent).commit();
                    gridLayout.addView(rel, i);
                }

            }else{
                Log.e("ERROR", "No user");
            }
        }catch (JSONException e){
            Log.e("JSON ERROR", "Bad JSON");
        }

        //Display recent quizzes in a scrolling grid pane
        /*********************************************************************/

        recentAdapter = new FragmentedActivity.SliderAdapter(getActivity().getSupportFragmentManager(), fragmentsRecentGrid.size(), fragmentsRecentGrid);
        recentPager.setAdapter(recentAdapter);
        recentNavigationDots.setupWithViewPager(recentPager, true);
        if(recentNavigationDots.getTabCount() < 2){
            recentNavigationDots.setVisibility(View.INVISIBLE);
        }
    }
}
