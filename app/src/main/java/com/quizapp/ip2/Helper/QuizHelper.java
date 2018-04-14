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

   /* public static void uploadQuiz(){
        JSONObject jsonQuiz = new JSONObject();

        try {
            jsonQuiz.put("quizname", getQuiz().getTitle());
            jsonQuiz.put("quizdescription", getQuiz().getDescription());
            jsonQuiz.put("quizimage", getQuiz().getImage());
            jsonQuiz.put("quizcolor", getQuiz().getColor());

            PostTask pt = new PostTask();
            String[] response = pt.sendPostRequest("quiz/"+getQuiz().getId()+"/edit", jsonQuiz.toString(), "POST");

            if(!response[0].equals("200")){
                Log.e("POST ERROR", "Could not post... " + response[0] + " " + response[1]);
            }
        } catch (JSONException e){
            Log.e("JSON Error", "Error parsing json");
        }
    }

    private static void uploadQuestions(){
        JSONObject jsonQuestion = new JSONObject();

        try {
           //todo make json object of question

        }
    }*/
}
