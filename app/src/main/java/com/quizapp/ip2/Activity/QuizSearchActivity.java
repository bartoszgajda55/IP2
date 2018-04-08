package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allan on 15/03/2018.
 */

public class QuizSearchActivity extends FragmentedActivity {

    private String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_search);
        Bundle b = getIntent().getExtras();
        search = b.getString("search");

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutLinear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final EditText textSearch = (EditText) findViewById(R.id.txtSearch);

        if(!b.getBoolean("all")) {
            toolbar.setTitle("Showing results for: " + "\"" + search + "\"");
        } else {
            toolbar.setTitle("Showing all quizzes");
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });

        Drawable searchIcon = getDrawable(R.drawable.icon_search);
        searchIcon.setTint(getResources().getColor(R.color.colorLight));


        textSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            Intent intent = new Intent(getBaseContext(), QuizSearchActivity.class);
                            Bundle b = new Bundle();
                            b.putString("search", textSearch.getText().toString());
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

        //ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        final RequestTask rt = new RequestTask();

        System.out.println("QUIZ PRINT: " + rt.sendGetRequest("quiz"));
        try {
            JSONArray resultset = new JSONArray(rt.sendGetRequest("quiz"));
            for (int i = 0; i < resultset.length(); i++) {
                JSONObject result = resultset.getJSONObject(i);

                QuizPreviewFragment quizPreview = new QuizPreviewFragment();
                Bundle searchBundle = new Bundle();
                String searchTitle = result.getString("QuizName");
                String searchDesc = result.getString("QuizDescription");
                String searcImg = result.getString("QuizImage");
                System.out.println(Color.parseColor("#"+result.getString("QuizColor")));
                int searchColor = Color.parseColor("#" + result.getString("QuizColor"));
                //int searchColor = R.color.colorPrimary;

                searchBundle.putString("title", searchTitle);
                searchBundle.putString("desc", searchDesc);
                searchBundle.putString("img", searcImg);
                searchBundle.putInt("color", searchColor);
                quizPreview.setArguments(searchBundle);
                RelativeLayout rel = new RelativeLayout(this);
                rel.setId(View.generateViewId());
                getSupportFragmentManager().beginTransaction().add(rel.getId(),quizPreview).commit();
                linearLayout.addView(rel);

               // fragments.add(quizPreview);


            }
        } catch (JSONException e){
            Log.e("ERROR", "Invalid JSON");
        }

    }

}
