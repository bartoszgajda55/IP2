package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.R;

/**
 * Created by Aaron on 11/03/2018.
 */

public class QuizPreviewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_preview_fragment, container, false);
        TextView txtQuizTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtQuizDesc = (TextView) view.findViewById(R.id.txtDesc);
        ImageView imgQuizImg = (ImageView) view.findViewById(R.id.imgQuizImg);

        txtQuizTitle.setText(this.getArguments().getString("title"));
        txtQuizDesc.setText(this.getArguments().getString("desc"));
        final String txt = this.getArguments().getString("img");

        new DownloadImageTask(imgQuizImg).execute(txt);

        ImageView backgroundShape = (ImageView) view.findViewById(R.id.imgBackground);

        DrawableCompat.setTint(backgroundShape.getDrawable(), this.getArguments().getInt("color"));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", QuizPreviewFragment.this.getArguments().getString("title"));
                bundle.putInt("color", QuizPreviewFragment.this.getArguments().getInt("color"));
                bundle.putString("img", QuizPreviewFragment.this.getArguments().getString("img"));
                bundle.putInt("question", 0);
                bundle.putInt("correct", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        return view;
    }

}
