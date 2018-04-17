package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Helper.StringHasher;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.Model.User;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aaron on 08/03/2018.
 */

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        Button btnLogin = (Button) view.findViewById(R.id.btnLogin);

        final EditText txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        final EditText txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        final PostTask pt = new PostTask();

        //Button pressed
        //TODO Prevent banned users from logging in
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    final String email = txtEmail.getText().toString().toLowerCase();

                    jsonObject.put("email", email);
                    jsonObject.put("password", new StringHasher().hashString(txtPassword.getText().toString()));

                    String[] response = pt.sendPostRequest("user/login", jsonObject.toString(), "POST");
                    if(response[0].equals("200")){

                        //Set UserHelper to user
                        JSONArray jsonResponseArray = new JSONArray(response[1]);
                        JSONObject jsonResponse = jsonResponseArray.getJSONObject(0);

                        User user = new User();
                        user.setUserID(jsonResponse.getInt("UserID"));
                        user.setUsername(jsonResponse.getString("Username"));
                        user.setEmail(jsonResponse.getString("Email"));
                        user.setFirstName(jsonResponse.getString("Firstname"));
                        user.setSurname(jsonResponse.getString("Surname"));
                        user.setProfilePicture(jsonResponse.getString("ProfileImage"));
                        user.setAdminStatus(jsonResponse.getInt("AdminStatus"));
                        user.setXp(jsonResponse.getInt("XP"));
                        user.setQuizzessCompleted(jsonResponse.getInt("QuizzessCompleted"));
                        user.setCorrectAnswers(jsonResponse.getInt("CorrectAnswers"));

                        UserHelper.setUser(user);

                        String[] banRequest = new RequestTask().sendGetRequest("blacklist/" + user.getUserID(), "GET");
                        if(banRequest[0].equals("404")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Remember me");
                            builder.setMessage("Do you want Quizzy to remember your password?");

                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences loginPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

                                    loginPrefsEditor.clear();
                                    loginPrefsEditor.apply();

                                    dialog.dismiss();
                                    logIn();
                                }
                            });

                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which){

                                    //TODO Store user login on local system file.
                                    SharedPreferences loginPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

                                    loginPrefsEditor.putString("email", email.toLowerCase());
                                    loginPrefsEditor.putString("password", new StringHasher().hashString(txtPassword.getText().toString()));
                                    loginPrefsEditor.apply();

                                    logIn();
                                }
                            });
                            AlertDialog ad = builder.create();
                            ad.show();
                        } else{
                            Toast.makeText(getActivity(), "You are banned...", Toast.LENGTH_SHORT).show();
                        }
                        
                    }else{
                        Toast.makeText(getActivity(), "Unknown email or password...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
                
            }
        });

        Button btnResetPassword = (Button) view.findViewById(R.id.btnForgotPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Reset Password");
                builder.setMessage("Forgotten your password? Enter your email below to receive an email containing your password");

                final EditText txtEmail = new EditText(getActivity());
                txtEmail.setSingleLine();
                txtEmail.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                int paddingDp = 20;
                int paddingPx = (int)(paddingDp * getResources().getDisplayMetrics().density);
                linearLayout.setPadding(paddingPx, 0, paddingPx, 0);

                linearLayout.addView(txtEmail);

                builder.setView(linearLayout);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        //TODO Check if email exists in the users table, if so:
                        //EmailHandler eh = new EmailHandler();
                        //eh(text.getText().toString(), "Password Recovery", "TODO: connect to database and send password if exists");
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
        getActivity().overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);


    }



}