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

import com.quizapp.ip2.Helper.PostTask;
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

    //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";

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


        //TODO CHECK IF EMAIL ALREADY EXISTS
        /*if (!email.matches(emailPattern)) {
            Toast.makeText(getContext(), "Invalid email address...", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (username.length() > 32 || username.length() < 3) { //TODO CHECK IF USERNAME ALREADY EXISTS
            Toast.makeText(getActivity(), "Username is invalid...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (firstName.length() > 255 || firstName.length() < 3) { //TODO ENSURE ONLY LETTERS
            Toast.makeText(getActivity(), "First name is invalid...", Toast.LENGTH_SHORT).show();
            return;
        }
        if (surname.length() > 255 || surname.length() < 3) { //TODO ENSURE ONLY LETTERS
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
            String[] response = pt.sendPostRequest("user/register", jsonObject.toString());

            if(response[0].equals("201")){
                Intent intent = new Intent(getContext(), TutorialActivity.class);
                startActivity(intent);

                /*JSONObject jsonResponseArray = new JSONObject(response[1]);
                Iterator<String> keys = jsonResponseArray.keys();
                String responseName = keys.next();
                JSONObject jsonResponse = jsonResponseArray.getJSONObject(responseName);*/
                JSONArray jsonResponseArray = new JSONArray(response[1]);
                JSONObject jsonResponse = jsonResponseArray.getJSONObject(0);

                User user = new User();
                user.setUserID(jsonResponse.getInt("UserID"));
                user.setUsername(jsonResponse.getString("Username"));
                user.setEmail(jsonResponse.getString("Email"));
                user.setFirstName(jsonResponse.getString("Firstname"));
                user.setSurname(jsonResponse.getString("Surname"));
                user.setAdminStatus(jsonResponse.getInt("AdminStatus"));
                user.setXp(jsonResponse.getInt("XP"));
                user.setQuizzessCompleted(jsonResponse.getInt("QuizzessCompleted"));
                user.setCorrectAnswers(jsonResponse.getInt("CorrectAnswers"));

                UserHelper.setUser(user);

            }else{
                Toast.makeText(getActivity(), "Registration error...", Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

