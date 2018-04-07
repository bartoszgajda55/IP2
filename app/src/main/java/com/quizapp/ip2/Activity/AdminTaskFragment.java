package com.quizapp.ip2.Activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quizapp.ip2.R;

/**
 * Created by Allan on 07/04/2018.
 */

public class AdminTaskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.admin_task_fragment, container, false);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
        ImageView imgTask = (ImageView) view.findViewById(R.id.imgTask);

        ImageView backgroundShape = (ImageView) view.findViewById(R.id.imgBackground);

        DrawableCompat.setTint(backgroundShape.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorLightGray));

        txtTitle.setText(this.getArguments().getString("title"));
        txtDesc.setText(this.getArguments().getString("desc"));

        imgTask.setImageDrawable(getContext().getDrawable(this.getArguments().getInt("img")));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Do the task
            }
        });

        return view;
    }
}
