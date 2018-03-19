package com.quizapp.ip2.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by aaron on 19/03/2018.
 */

public class Question {

    private String correctAnswer;
    private ArrayList<String> wrongAnswers = new ArrayList<>();

    public Question(String correctAnswer, ArrayList<String> wrongAnswers){
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getWrongAnswers() {
        return wrongAnswers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setWrongAnswers(ArrayList<String> wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }
}
