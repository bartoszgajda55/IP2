package com.quizapp.ip2.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.quizapp.ip2.Helper.LoadImageHelper;

/**
 * Created by aaron on 19/03/2018.
 */

public class User {

    private String username;
    private String email;
    private String firstName;
    private String surname;
    private String password;
    private Drawable userImage;

    public User(String username, String email, String firstName, String surname, String password) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
    }

    public User(String username, String email, String firstName, String surname, String password, String userImage, Context context) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.userImage = new LoadImageHelper(context).load(userImage);
    }

    public Drawable getUserImage() {
        return userImage;
    }

    public void setUserImage(Drawable userImage) {
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
