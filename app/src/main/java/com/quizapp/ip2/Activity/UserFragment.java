package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quizapp.ip2.R;

/**
 * Created by Aaron on 10/03/2018.
 */

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        LinearLayout friendsLayout = (LinearLayout) view.findViewById(R.id.friendLinearLayout);
        ImageView powerImage = (ImageView) view.findViewById(R.id.imgPowerIcon);
        ImageView settingsImage = (ImageView) view.findViewById(R.id.imgSettingsIcon);

        powerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Log out");
                builder.setMessage("Are you sure you want to log out?");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();


            }
        });

        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Take to Settings Page
            }
        });

        /*for (int x = 0; x < 10; x++) {
            UserPreviewFragment frag = new UserPreviewFragment();
            Bundle bundle = new Bundle();
            int place = x + 1;
            String username = "User " + 1; //TODO get username from user query result
            String level = "10"; //TODO calculate level from user query result (XP)
            bundle.putInt("place", place);
            bundle.putString("username", username);
            bundle.putString("level", level);

            bundle.putInt("color", R.color.colorLightGray);
            bundle.putInt("textColor", R.color.colorDarkGray);
            bundle.putFloat("alpha", 0.25F);

            frag.setArguments(bundle);
            RelativeLayout rel = new RelativeLayout(getContext());
            rel.setId(View.generateViewId());
            getFragmentManager().beginTransaction().add(rel.getId(), frag).commit();
            friendsLayout.addView(rel);
        }*/

        return view;
    }
}
