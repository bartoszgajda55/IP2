package com.quizapp.ip2.Helper;

import com.quizapp.ip2.Model.Quiz;

/**
 * Created by Allan on 09/04/2018.
 */

public class QuizHelper {

    public static Quiz quiz;

    public QuizHelper(Quiz quiz) {
        this.quiz = quiz;
    }

    public static Quiz getQuiz() {
        return quiz;
    }

    public static void setQuiz(Quiz quiz) {
        QuizHelper.quiz = quiz;
    }

}
