package com.quizapp.ip2.Activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.quizapp.ip2.R;

public class AdminImportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_import);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        toolbar.setTitle("Import Quiz");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorLight));
    }
}
