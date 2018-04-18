package com.quizapp.ip2.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

import java.util.ArrayList;

public class AdminActivity extends FragmentedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutLinear);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ArrayList<AdminTask> arrayList = new ArrayList();

        Drawable whiteArrow = getDrawable(R.drawable.arrow_back);
        whiteArrow.setTint(getResources().getColor(R.color.colorLight));
        toolbar.setNavigationIcon(whiteArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });

        //Add admin tasks to arraylist
        arrayList.add(new AdminTask("Quizzes","Add, edit or delete a quiz",R.drawable.icon_plus));
        arrayList.add(new AdminTask("Lookup/Edit User","Ban, promote or view a user's details",R.drawable.icon_user));
        arrayList.add(new AdminTask("Import Quiz","Import a Quiz from a JSON file",R.drawable.icon_upload));

        for (int x=0; x<arrayList.size(); x++){
            AdminTaskFragment frag = new AdminTaskFragment();
            Bundle bundle = new Bundle();
            String title = arrayList.get(x).getTitle();
            String desc = arrayList.get(x).getDescription();
            int img = arrayList.get(x).getImage();
            bundle.putString("title", title);
            bundle.putString("desc", desc);
            bundle.putInt("img", img);
            frag.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(this);
            rel.setId(View.generateViewId());
            getSupportFragmentManager().beginTransaction().add(rel.getId(),frag).commit();
            linearLayout.addView(rel);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }


    //INNER CLASS FOR ADMIN TASK
    public class AdminTask{
        String title;
        String description;
        int image;

        public AdminTask(String title, String description, int image){
            this.title = title;
            this.description = description;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }


        public int getImage() {
            return image;
        }

    }


}
