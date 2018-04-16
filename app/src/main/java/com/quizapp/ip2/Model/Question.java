package com.quizapp.ip2.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by aaron on 19/03/2018.
 */

public class Question implements Parcelable{

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

    public Question(String correctAnswer, ArrayList<String> wrongAnswers, String questionImage, String questionString) {
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.questionImage = questionImage;
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



    /*** Parcelable methods - do not include in class diagram
     * These methods allow the Question class to implement Parcelable, to allow an arraylist of questions to be passed into a bundle for use in other activities***/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.correctAnswer);
        dest.writeStringList(this.wrongAnswers);
        dest.writeString(this.questionImage);
        dest.writeInt(this.questionId);
        dest.writeString(this.questionString);
    }

    protected Question(Parcel in) {
        this.correctAnswer = in.readString();
        this.wrongAnswers = in.createStringArrayList();
        this.questionImage = in.readString();
        this.questionId = in.readInt();
        this.questionString = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
