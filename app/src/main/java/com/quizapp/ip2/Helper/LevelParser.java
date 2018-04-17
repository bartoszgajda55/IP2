package com.quizapp.ip2.Helper;

/**
 * Created by Aaron on 08/04/2018.
 */

public class LevelParser {

    //This class accepts XP as an input and returns a level as an output
    //Takes integer, returns integer

    int level;
    int xp;
    static final double CONSTANT = 0.25;

    public LevelParser(int xp){
        this.xp=xp;
        Double calclevel = CONSTANT * Math.sqrt(xp);
        this.level = calclevel.intValue() + 1;

    }

    public int getLevel(){
        return this.level;
    }

    public int nextLevel(){
        double nextLevel = (CONSTANT * Math.sqrt(xp));
        int x = Double.toString(nextLevel).indexOf(".");

        String string = (Double.toString(nextLevel)+"0").substring(x+1,x+3);

        int perc = Integer.parseInt(string);
        return perc;
    }
}
