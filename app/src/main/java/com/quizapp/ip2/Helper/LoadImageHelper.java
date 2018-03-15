package com.quizapp.ip2.Helper;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.quizapp.ip2.R;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Aaron on 15/03/2018.
 */

public class LoadImageHelper {

    Context context;
    public LoadImageHelper(Context context){
        this.context = context;
    }

    public Drawable load(String url){
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "url");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            Drawable notfound = context.getResources().getDrawable(R.drawable.notfound);
            return notfound;
        }
    }


}
