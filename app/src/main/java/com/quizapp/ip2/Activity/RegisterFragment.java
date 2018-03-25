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
import android.widget.Toast;

import com.quizapp.ip2.R;

/**
 * Created by aaron on 08/03/2018.
 */

public class RegisterFragment extends Fragment {


    EditText username;
    EditText firstName;
    EditText surname;
    EditText password;
    EditText passwordConfirm;
    String email;
    String emailPattern;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);


        final EditText emailValidate = (EditText) view.findViewById(R.id.txtEmail);
        email = emailValidate.getText().toString().trim();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        username = (EditText) view.findViewById(R.id.txtUsername);
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

        if (username.length() > 32) {
            Toast.makeText(getActivity(), "No more than 32 characters!",
                    Toast.LENGTH_SHORT).show();
        }

        if (email.matches(emailPattern)) {
            Toast.makeText(getContext(), "valid email address", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }

        if (firstName.length() > 255) {
            Toast.makeText(getActivity(), "Character limit exceeded!",
                    Toast.LENGTH_SHORT).show();
        }
        if (surname.length() > 255) {
            Toast.makeText(getActivity(), "Character limit exceeded!",
                    Toast.LENGTH_SHORT).show();
        }
        if (password.length() > 255 && password == passwordConfirm) {
            Toast.makeText(getActivity(), "Character limit exceeded!",
                    Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(getContext(), TutorialActivity.class);
        startActivity(intent);
    }
}

