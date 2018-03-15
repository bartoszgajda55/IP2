package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

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
        final EditText textSearch = (EditText) findViewById(R.id.txtSearch);
        textSearch.setText(search);

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

        /**
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);**/

         //TODO display quizzes that match search term
         for (int x=0; x<3; x++){
         QuizPreviewFragment frag = new QuizPreviewFragment();
         Bundle bundle = new Bundle();
         String title = textSearch.getText().toString(); //TODO get from database
         String desc = "Description"; //TODO get from database
         String img = "https://cdn3.iconfinder.com/data/icons/brain-games/1042/Quiz-Games-grey.png"; //TODO get from database
         String color = ""; //TODO get from database
         bundle.putString("title", title);
         bundle.putString("desc", desc);
         bundle.putString("img", img);
         bundle.putString("color", color);
         frag.setArguments(bundle);
         RelativeLayout rel = new RelativeLayout(this);
         rel.setId(View.generateViewId());
         getSupportFragmentManager().beginTransaction().add(rel.getId(),frag).commit();
         linearLayout.addView(rel);
         }
    }

    public void goBack(View view){
        Intent intent = new Intent(getBaseContext(), HomepageActivity.class);
        startActivity(intent);
    }
}
