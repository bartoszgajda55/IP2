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
    private int color; //this gets passed in as R.color.<color> from the

    private Drawable drawable = null;
    //components
    private TextView txtQuizTitle;
    private TextView txtQuizDesc;
    private ImageView imgQuizImg;



    @Nullable
  //  @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_preview_fragment, container, false);
        txtQuizTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtQuizDesc = (TextView) view.findViewById(R.id.txtDesc);
        imgQuizImg = (ImageView) view.findViewById(R.id.imgQuizImg);

        txtQuizTitle.setText(this.getArguments().getString("title"));
        txtQuizDesc.setText(this.getArguments().getString("desc"));
        final String txt = this.getArguments().getString("img");

        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                drawable = loadImage(txt);
            }
        });
        networkThread.start();
        ImageView backgroundShape = (ImageView) view.findViewById(R.id.imgBackground);

        color = R.color.colorPrimary; //TODO remove this, pass it in

        DrawableCompat.setTint(backgroundShape.getDrawable(), ContextCompat.getColor(getContext(), color)); //TODO set color to database color
        while(drawable==null){
            System.out.println("Loading Image ...");
        imgQuizImg.setImageDrawable(drawable);}
        return view;
    }

    //TODO onClick to open quiz associated with fragment
    //TODO add Navigation dots

    private Drawable loadImage(String url){
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "url");
            return d;
        }catch (Exception e) {
            imgQuizImg.setImageResource(R.drawable.notfound);
            System.out.println("Exc="+e);
            return null;
        }
    }

}
