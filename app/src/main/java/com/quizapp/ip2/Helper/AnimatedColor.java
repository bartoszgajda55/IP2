package com.quizapp.ip2.Helper;

import android.graphics.Color;

/**
 * Created by Aaron on 15/03/2018.
 */
public class AnimatedColor {
    private final int startColor, endColor;
    private final float[] startHSV, endHSV;
    private float[] transition = new float[3];


    public AnimatedColor(int start, int end) {
        startColor = start;
        endColor = end;
        startHSV = toHSV(start);
        endHSV = toHSV(end);
    }

    public int with(float delta) {
        if (delta <= 0) return startColor;
        if (delta >= 1) return endColor;
        return Color.HSVToColor(transition(delta));
    }

    private float[] transition(float delta) {
        transition[0] = (endHSV[0] - startHSV[0]) * delta + startHSV[0];
        transition[1] = (endHSV[1] - startHSV[1]) * delta + startHSV[1];
        transition[2] = (endHSV[2] - startHSV[2]) * delta + startHSV[2];
        return transition;
    }

    private float[] toHSV(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv;
    }
}
