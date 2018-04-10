package com.quizapp.ip2.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.R;

public class UserPreviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user_preview, container, false);

        TextView username = (TextView) view.findViewById(R.id.username);
        TextView level = (TextView) view.findViewById(R.id.level);
        TextView place = (TextView) view.findViewById(R.id.place);

        ImageView imgBg = (ImageView) view.findViewById(R.id.imgBg);
        ImageView imgUserIcon = (ImageView) view.findViewById(R.id.imgPerson);
        ImageView imgLevelIcon = (ImageView) view.findViewById(R.id.imgLevel);

        username.setText(this.getArguments().getString("username"));
        level.setText(this.getArguments().getString("level"));
        place.setText(Integer.toString(this.getArguments().getInt("place")));

        username.setTextColor(getResources().getColor(this.getArguments().getInt("textColor")));
        level.setTextColor(getResources().getColor(this.getArguments().getInt("textColor")));
        place.setTextColor(getResources().getColor(this.getArguments().getInt("textColor")));

        DrawableCompat.setTint(imgBg.getDrawable(), ContextCompat.getColor(getContext(), this.getArguments().getInt("color")));
        DrawableCompat.setTint(imgUserIcon.getDrawable(), ContextCompat.getColor(getContext(), this.getArguments().getInt("textColor")));
        //DrawableCompat.setTint(imgLevelIcon.getDrawable(), ContextCompat.getColor(getContext(), this.getArguments().getInt("textColor")));

        imgBg.setAlpha(this.getArguments().getFloat("alpha"));


        if(this.getArguments().getInt("place") > 999){ //Moves the user icon along to prevent clipping
            ViewGroup.MarginLayoutParams marginsUserIcon = (ViewGroup.MarginLayoutParams)  imgUserIcon.getLayoutParams();

            int topPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            int leftPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

            marginsUserIcon.setMargins(leftPx, topPx, 0, 0);
            imgUserIcon.setLayoutParams(marginsUserIcon);



        }


        return view;
    }
}

