package com.quizapp.ip2.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.quizapp.ip2.Helper.LoadImageHelper;

import java.util.ArrayList;

/**
 * Created by aaron on 19/03/2018.
 */

public class Question {

    private String correctAnswer;
    private ArrayList<String> wrongAnswers = new ArrayList<>();
    private Drawable questionImage;

    public Question(String correctAnswer, ArrayList<String> wrongAnswers, String questionImage, Context context){
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.questionImage = new LoadImageHelper(context).load(questionImage);
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

    public Drawable getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(Drawable questionImage) {
        this.questionImage = questionImage;
    }
}
