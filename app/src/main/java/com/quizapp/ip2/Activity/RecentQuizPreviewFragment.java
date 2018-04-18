package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.Helper.QuizHelper;
import com.quizapp.ip2.Helper.RequestTask2;
import com.quizapp.ip2.Model.Question;
import com.quizapp.ip2.Model.Quiz;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Allan on 15/03/2018.
 */

public class RecentQuizPreviewFragment extends Fragment {

    //components
    private TextView txtQuizTitle;
    private ImageView imgQuizImg;

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent_quiz_preview_fragment, container, false);
        txtQuizTitle = (TextView) view.findViewById(R.id.txtTitle);
        imgQuizImg = (ImageView) view.findViewById(R.id.imgQuizImg);

        txtQuizTitle.setText(this.getArguments().getString("title"));
        final String txt = this.getArguments().getString("img");

        new DownloadImageTask(imgQuizImg).execute(txt);
        ImageView backgroundShape = (ImageView) view.findViewById(R.id.imgBackground);
        DrawableCompat.setTint(backgroundShape.getDrawable(), this.getArguments().getInt("color"));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Quiz quiz = new Quiz();

                ArrayList<Question> arrayList = new ArrayList<>();

                String title = RecentQuizPreviewFragment.this.getArguments().getString("title");
                int color = RecentQuizPreviewFragment.this.getArguments().getInt("color");
                String img = RecentQuizPreviewFragment.this.getArguments().getString("img");
                int id = RecentQuizPreviewFragment.this.getArguments().getInt("id");
                String desc = RecentQuizPreviewFragment.this.getArguments().getString("desc");

                RequestTask2 rt = new RequestTask2();
                try {
                    String[] response = rt.sendGetRequest("quiz/" + id + "/questions", "GET");
                    JSONArray jsonQuestions = new JSONArray(response[1]);


                    for (int i = 0; i < jsonQuestions.length(); i++) {

                        JSONObject jsonObj = jsonQuestions.getJSONObject(i);

                        int questionId = jsonObj.getInt("QuestionID");

                        String questionString = jsonObj.getString("QuestionString");

                        String rightAnswer = jsonObj.getString("CorrectAnswerString");
                        String wrongAnswer1 = jsonObj.getString("WrongAnswerString");
                        String wrongAnswer2 = jsonObj.getString("WrongAnswerString2");
                        String wrongAnswer3 = jsonObj.getString("WrongAnswerString3");

                        ArrayList<String> arrayWrong = new ArrayList<>();
                        arrayWrong.add(wrongAnswer1);
                        arrayWrong.add(wrongAnswer2);
                        arrayWrong.add(wrongAnswer3);

                        String questionImage = jsonObj.getString("QuestionImage");

                        Question question = new Question(rightAnswer, arrayWrong, questionImage, questionId, questionString);

                        arrayList.add(question);
                    }

                    quiz.setId(id);
                    quiz.setTitle(title);
                    quiz.setDescription(desc);
                    quiz.setColor(color);
                    quiz.setImage(img);
                    quiz.setQuestions(arrayList);


                } catch (JSONException e) {
                    Log.e("JSON ERROR", "Invalid Json");
                }

                QuizHelper.setQuiz(quiz);

                Intent intent = new Intent(getContext(), QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("title", title);
                bundle.putInt("color", color);
                bundle.putString("img", img);
                bundle.putInt("question", 0);
                bundle.putInt("correct", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                QuizActivity.questions = new ArrayList(QuizHelper.getQuiz().getQuizQuestions());

            }
        });
    }
}