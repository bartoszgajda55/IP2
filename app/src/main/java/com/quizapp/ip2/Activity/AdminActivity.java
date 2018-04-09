package com.quizapp.ip2.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.Helper.DownloadImageTask;
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
                Intent intent = new Intent(getBaseContext(), HomepageActivity.class);
                startActivity(intent);
            }
        });

        //Add admin tasks to arraylist
        arrayList.add(new AdminTask("Add Quiz","Add a new quiz to the database",R.drawable.icon_plus));
        arrayList.add(new AdminTask("Delete Quiz","Remove a quiz from the database",R.drawable.icon_x));
        arrayList.add(new AdminTask("Edit Quiz","Edit an already existing quiz",R.drawable.icon_pen));
        arrayList.add(new AdminTask("Ban User","Ban a user by entering their email and a ban reason",R.drawable.icon_user));
        arrayList.add(new AdminTask("Add Admin","Add a new admin by entering their email",R.drawable.icon_add));

        for (int x=0; x<5; x++){
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
