package com.quizapp.ip2.Activity;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_preview_fragment, container, false);
        TextView txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        ImageView imgQuizImg = (ImageView) view.findViewById(R.id.imgQuestionImg);

        txtQuestion.setText(this.getArguments().getString("question"));
        final String txt = this.getArguments().getString("img");

        new DownloadImageTask(imgQuizImg).execute(txt);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LOAD AdminEditQuestionActivity for this question

            }
        });

        return view;

    }

}
