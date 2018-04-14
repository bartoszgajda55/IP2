package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.R;

/**
 * Created by Aaron on 12/04/2018.
 */

public class QuestionPreviewFragment extends Fragment {

    int questionid;
    String question;
    String answer;
    String wrongAnswer1, wrongAnswer2, wrongAnswer3;
    String img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_preview_fragment, container, false);
        TextView txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        ImageView imgQuizImg = (ImageView) view.findViewById(R.id.imgQuestionImg);

        questionid = getArguments().getInt("id");
        question = getArguments().getString("question");
        answer = getArguments().getString("answer");
        wrongAnswer1 = getArguments().getString("wrongAnswer1");
        wrongAnswer2 = getArguments().getString("wrongAnswer2");
        wrongAnswer3 = getArguments().getString("wrongAnswer3");
        img = getArguments().getString("img");

        txtQuestion.setText(question);

        new DownloadImageTask(imgQuizImg).execute(img);

        final Bundle args = getArguments();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LOAD AdminEditQuestionActivity for this question
                Intent intent = new Intent(getContext(), AdminEditQuestionActivity.class);
                intent.putExtra("bundle", args);

                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



        return view;

    }

}
