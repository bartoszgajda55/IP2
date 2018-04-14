package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class); //todo animation?
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //Add admin tasks to arraylist
        arrayList.add(new AdminTask("Quizzes","Add, edit or delete a quiz",R.drawable.icon_plus));
        arrayList.add(new AdminTask("Lookup/Edit User","Ban, promote or view a user's details",R.drawable.icon_user));

        for (int x=0; x<2; x++){
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
