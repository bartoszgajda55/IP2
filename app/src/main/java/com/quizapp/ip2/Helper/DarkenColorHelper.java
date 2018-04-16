package com.quizapp.ip2.Helper;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by Aaron on 13/04/2018.
 */

public class DarkenColorHelper {

    public DarkenColorHelper(){

    }

    @ColorInt
    public int darkenColor(@ColorInt int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.75f;
        return Color.HSVToColor(hsv);
    }

}
