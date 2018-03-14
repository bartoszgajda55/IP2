package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import  com.quizapp.ip2.Helper.EmailHandler;
import  com.quizapp.ip2.R;

/**
 * Created by aaron on 08/03/2018.
 */

public class LoginFragment extends FragmentView {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        //SUCCESS
        Button btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Remember me");
                builder.setMessage("Do you want Quizzy to remember your password?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //CREATE LOCAL FILE - STORE LOGIN
                        logIn();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logIn();

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();



            }
        });

        Button btnResetPassword = (Button) view.findViewById(R.id.btnForgotPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test button click");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Reset Password");
                final EditText text = new EditText(getActivity());
                builder.setMessage("Forgotten your password? Enter your email below to receive an email containing your password");
                builder.setView(text);
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        //CHECK IF EMAIL EXISTS
                        EmailHandler eh = new EmailHandler();
                        eh.sendMail(text.getText().toString(), "Password Recovery", "TODO: connect to database and send password if exists");
                        Toast.makeText(getActivity(),"Email sent...", Toast.LENGTH_SHORT);
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
        return view;
    }


    private void logIn(){

        Intent intent = new Intent(getContext(), HomepageActivity.class);
        startActivity(intent);


    }

    public static FragmentView newInstance(int page) {
        LoginFragment logFragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        logFragment.setArguments(args);
        return logFragment;
    }


}