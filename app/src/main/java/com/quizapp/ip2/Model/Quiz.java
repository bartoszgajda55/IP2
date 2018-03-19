package com.quizapp.ip2.Model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by aaron on 19/03/2018.
 */

public class Quiz {

    private String title, description;
    private int color; //color resource
    private Drawable image;

    private ArrayList<Question> questions = new ArrayList<>();


    public Quiz(String title, String description, Integer color, Drawable image){
        this.title = title;
        this.description = description;
        this.color = color;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> getQuizQuestions(){
        ArrayList<Question> quizQuestions = new ArrayList<>();
        Collections.shuffle(questions);

        for(int i = 0; i < 10; i++){
            quizQuestions.add(questions.get(i));
        }

        return quizQuestions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

}
