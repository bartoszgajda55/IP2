package com.quizapp.ip2.Helper;

/**
 * Created by Aaron on 08/04/2018.
 */

public class LevelParser {

    //This class accepts XP as an input and returns a level as an output
    //Takes integer, returns integer

    int level;

    static final double CONSTANT = 0.25;

    public LevelParser(int xp){

        Double calclevel = CONSTANT * Math.sqrt(xp);
        this.level = calclevel.intValue();

    }

    public int getLevel(){
        return this.level;
    }
}
