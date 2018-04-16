package com.quizapp.ip2.Helper;

import android.util.Log;

import com.quizapp.ip2.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allan on 09/04/2018.
 */

public class UserHelper {

    public static User user;

    public UserHelper(User user) {
        this.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserHelper.user = user;
    }

    //Upload this user object to the database
    public static void uploadUser(){
        JSONObject jsonUser = new JSONObject();

        try {
            jsonUser.put("email", getUser().getEmail());
            jsonUser.put("firstname", getUser().getFirstName());
            jsonUser.put("surname", getUser().getSurname());
            jsonUser.put("password", getUser().getPassword());
            jsonUser.put("profileimage", getUser().getProfilePicture());
            jsonUser.put("adminstatus", getUser().getAdminStatus());
            jsonUser.put("xp", getUser().getXp());
            jsonUser.put("quizesscompleted", getUser().getQuizzessCompleted());
            jsonUser.put("correctanswers", getUser().getCorrectAnswers());

            PostTask pt = new PostTask();
            String[] response = pt.sendPostRequest("user/"+getUser().getUserID()+"/edit", jsonUser.toString(), "POST");

            if(!response[0].equals("200")){
                Log.e("POST ERROR", "Could not post... " + response[0] + " " + response[1]);
            }
        } catch (JSONException e){
            Log.e("JSON Error", "Error parsing json");
        }
    }
}
