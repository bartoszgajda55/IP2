package com.quizapp.ip2.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Aaron on 15/03/2018.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bitmapImage;

    public DownloadImageTask(ImageView bitmapImage){
        this.bitmapImage = bitmapImage;
    }

    protected Bitmap doInBackground(String... urls){
        String urlDisplay = urls[0];
        Bitmap image = null;
        try {
            InputStream inputStream = new URL(urlDisplay).openStream();
            image = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result){
        bitmapImage.setImageBitmap(result);
    }


}
