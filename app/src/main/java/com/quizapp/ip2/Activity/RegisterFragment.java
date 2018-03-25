package com.quizapp.ip2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.quizapp.ip2.R;

/**
 * Created by aaron on 08/03/2018.
 */

public class RegisterFragment extends Fragment {


    EditText username;
    EditText email;
    EditText firstName;
    EditText surname;
    EditText password;
    EditText passwordConfirm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        username = (EditText) view.findViewById(R.id.txtUsername);
        email = (EditText) view.findViewById(R.id.txtEmail);
        firstName = (EditText) view.findViewById(R.id.txtFirstname);
        surname = (EditText) view.findViewById(R.id.txtSurname);
        password = (EditText) view.findViewById(R.id.txtPassword);
        passwordConfirm = (EditText) view.findViewById(R.id.txtConfirmPassword);


        //SUCCESS
        Button btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Authenticate Register and Add to database
                register();
            }
        });

        return view;
    }

    public void register() {


        Intent intent = new Intent(getContext(), TutorialActivity.class);
        startActivity(intent);
    }

}