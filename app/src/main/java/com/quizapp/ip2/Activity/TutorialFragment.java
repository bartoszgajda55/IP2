package com.quizapp.ip2.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.Helper.LoadImageHelper;
import com.quizapp.ip2.R;

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
                LoadImageHelper loadImageHelper = new LoadImageHelper(getContext());
                drawable = loadImageHelper.load(txt);
            }
        });
        networkThread.start();
        while(drawable==null){
            System.out.println("Loading Image ...");
        }
        imgTutorial.setImageDrawable(drawable);
        return view;
    }
}
