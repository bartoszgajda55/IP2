package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.StringHasher;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.Model.User;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aaron on 08/03/2018.
 */

public class RegisterFragment extends Fragment {


    EditText usernameField;
    EditText firstNameField;
    EditText surnameField;
    EditText passwordField;
    EditText passwordConfirmField;
    EditText emailField;

    String username;
    String firstName;
    String surname;
    String email;

    final static Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    PostTask pt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        usernameField = (EditText) view.findViewById(R.id.txtUsername);
        firstNameField = (EditText) view.findViewById(R.id.txtFirstname);
        surnameField = (EditText) view.findViewById(R.id.txtSurname);
        passwordField = (EditText) view.findViewById(R.id.txtPassword);
        passwordConfirmField = (EditText) view.findViewById(R.id.txtConfirmPassword);
        emailField = (EditText) view.findViewById(R.id.txtEmail);

        //SUCCESS
        Button btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryRegister();
            }
        });

        pt = new PostTask();
        return view;
    }

    public void tryRegister() {

        username = usernameField.getText().toString();
        firstName = firstNameField.getText().toString();
        surname = surnameField.getText().toString();
        email = emailField.getText().toString().toLowerCase();

        // Check if email matches pattern
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if(!matcher.find()){
            Toast.makeText(getActivity(), "Email is invalid..", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject jsonObj = new JSONObject();
        PostTask ptUsername = new PostTask();


        //Check to see if username of email is taken
        try {
                jsonObj.put("email",email);
                String[] response = ptUsername.sendPostRequest("user/find", jsonObj.toString());
                if(response[0].equals("200")){ //400?
                    Toast.makeText(getActivity(), "Email is taken...", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    jsonObj.remove("email");
                    jsonObj.put("username",username);
                    String[] response2 = ptUsername.sendPostRequest("user/find", jsonObj.toString());

                    if(response2[0].equals("200")){ //400?
                        Toast.makeText(getActivity(), "Username is taken...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

        }catch(JSONException e){
            Log.e("JSON Error", "Invalid Json");
            return;
        }

        if (username.length() > 32 || username.length() < 3) {
            Toast.makeText(getActivity(), "Username is invalid...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (firstName.length() > 255 || firstName.length() < 3 || firstName.matches(".*\\d+.*")) {
            Toast.makeText(getActivity(), "First name is invalid...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (surname.length() > 255 || surname.length() < 3 || surname.matches(".*\\d+.*")) {
            Toast.makeText(getActivity(), "Surname is invalid...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwordField.getText().length() > 255 || passwordField.getText().length() < 3 || (!passwordField.getText().toString().matches(".*\\d+.*"))){
            Toast.makeText(getActivity(), "Passwords must have more than 3 characters and at least 1 number...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!passwordField.getText().toString().equals(passwordConfirmField.getText().toString())) {
            Toast.makeText(getActivity(), "Passwords do not match...", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
            jsonObject.put("email",email);
            jsonObject.put("firstname",firstName);
            jsonObject.put("surname",surname);
            jsonObject.put("password",new StringHasher().hashString(passwordField.getText().toString()));
            final String[] response = pt.sendPostRequest("user/register", jsonObject.toString());

            if(response[0].equals("201")){

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
                        logIn(response);
                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                        SharedPreferences loginPreferences = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();

                        loginPrefsEditor.putString("email", email.toLowerCase());
                        loginPrefsEditor.putString("password", new StringHasher().hashString(passwordField.getText().toString()));
                        loginPrefsEditor.apply();

                        logIn(response);
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();

                /////////


            }else{
                Toast.makeText(getActivity(), "Registration error...", Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void logIn(String[] response){
        Intent intent = new Intent(getContext(), TutorialActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_ver_in, R.anim.slide_ver_out);


        try {
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
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}

