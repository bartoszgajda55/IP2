package com.quizapp.ip2.Model;

import java.util.ArrayList;
import java.util.Collections;

//import com.quizapp.ip2.Helper.LoadImageHelper;

/**
 * Created by aaron on 19/03/2018.
 */

public class Quiz {

    //todo id and methods
    private int id;
    private String title, description;
    private int color; //color resource
    private String image;

    private ArrayList<Question> questions = new ArrayList<>(); //TODO Add question and set question method

    public Quiz() {
    }

    public Quiz(int id, String title, String description, Integer color, String image){
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
