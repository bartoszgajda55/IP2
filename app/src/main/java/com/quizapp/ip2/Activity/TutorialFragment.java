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
 * Created by Allan on 15/03/2018.
 */

public class TutorialFragment extends Fragment {
    ImageView imgTutorial;
    View view;
    private Drawable drawable = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tutorial_fragment, container, false);

        imgTutorial = (ImageView) view.findViewById(R.id.imgTutorial);
        TextView txtTutorialTitle = (TextView) view.findViewById(R.id.txtTutorialTitle);
        TextView txtTutorialDesc= (TextView) view.findViewById(R.id.txtTutorialDesc);

        /**GifImageView gifImageView = (GifImageView) view.findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.unlock);
        gifImageView.setScaleX(2F);
        gifImageView.setScaleY(3F);**/

        txtTutorialTitle.setText(this.getArguments().getString("title"));
        txtTutorialDesc.setText(this.getArguments().getString("desc"));


        final String txt = this.getArguments().getString("img");
        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                drawable = loadImage(txt);
            }
        });
        networkThread.start();
        while(drawable==null){
            System.out.println("Loading Image ...");
            imgTutorial.setImageDrawable(drawable);}
        return view;
    }

    private Drawable loadImage(String url){
        //TODO FIX Image Resetting to NULL after scrolling
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "url");
            return d;
        }catch (Exception e) {
            imgTutorial.setImageResource(R.drawable.notfound);
            System.out.println("Exc="+e);
            return null;
        }
    }
}
