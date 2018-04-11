package com.quizapp.ip2.Activity;

import android.content.Intent;
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
        final String title = this.getArguments().getString("title");
        txtTitle.setText(title);
        txtDesc.setText(this.getArguments().getString("desc"));

        imgTask.setImageDrawable(getContext().getDrawable(this.getArguments().getInt("img")));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.equals("Add Quiz")){
                    Intent intent = new Intent(getContext(), AddQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(title.equals("Delete Quiz")){
                    Intent intent = new Intent(getContext(), DeleteQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(title.equals("Edit Quiz")){
                    Intent intent = new Intent(getContext(), EditQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(title.equals("Lookup/Edit User")){
                    //todo alert dialog, ask for username or email, use find api to get user id and all details, pass in as bundle.
                    Intent intent = new Intent(getContext(), LookupUserActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else{
                    Intent intent = new Intent(getContext(), AddAdminActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }

            }
        });

        return view;
    }
}
