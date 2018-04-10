package com.quizapp.ip2.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

//import com.quizapp.ip2.Helper.LoadImageHelper;

import java.util.ArrayList;

/**
 * Created by aaron on 19/03/2018.
 */

public class Question {

    private String correctAnswer;
    private ArrayList<String> wrongAnswers = new ArrayList<>();
    private String questionImage;
    private int questionId;
    private String questionString;


    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public Question(String correctAnswer, ArrayList<String> wrongAnswers, String questionImage, int questionId, String questionString) {
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.questionImage = questionImage;
        this.questionId = questionId;
        this.questionString = questionString;
    }

    public Question(String correctAnswer, ArrayList<String> wrongAnswers, String questionImage, int questionId) {
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.questionImage = questionImage;
        this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Question(String correctAnswer, ArrayList<String> wrongAnswers, String questionImage){
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

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }
}
