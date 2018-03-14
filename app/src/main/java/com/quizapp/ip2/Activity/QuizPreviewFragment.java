package com.quizapp.ip2.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Aaron on 11/03/2018.
 */

public class QuizPreviewFragment extends Fragment {

    private String quizTitle;
    private String quizDesc;
    private String quizImg;
    private String quizColor;
    private int color; //this gets passed in as R.color.<color> from the

    //components
    private TextView txtQuizTitle;
    private TextView txtQuizDesc;
    private ImageView imgQuizImg;



    @Nullable
  //  @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        txtQuizTitle.setText(quizTitle);
        txtQuizDesc.setText(quizDesc);

        Drawable drawable = loadImage(quizImg);
        imgQuizImg.setImageDrawable(drawable);

        ImageView backgroundShape = (ImageView) view.findViewById(R.id.imgBackground);

        color = R.color.colorPrimary; //todo remove this, pass it in

        DrawableCompat.setTint(backgroundShape.getDrawable(), ContextCompat.getColor(getContext(), color)); //TODO set color to database color

        return view;
    }

    private Drawable loadImage(String url){
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        }catch (Exception e) {
            //TODO handle error
            System.out.println("Exc="+e);
            return null;
        }
    }

}
