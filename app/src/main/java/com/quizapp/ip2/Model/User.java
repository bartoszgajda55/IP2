package com.quizapp.ip2.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;

//import com.quizapp.ip2.Helper.LoadImageHelper;

/**
 * Created by aaron on 19/03/2018.
 */

public class User {

    private int userID;
    private String username;
    private String email;
    private String firstName;
    private String surname;
    private String password;
    private int adminStatus;
    private Drawable userImage;
    private int xp;
    private int quizzessCompleted;
    private int correctAnswers;

    public User() {
    }

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
       // this.userImage = new LoadImageHelper(context).load(userImage);
    }

    public User(int userID, String username, String email, String firstName, String surname, String password, int adminStatus, Drawable userImage, int xp, int quizzessCompleted, int correctAnswers) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.adminStatus = adminStatus;
        this.userImage = userImage;
        this.xp = xp;
        this.quizzessCompleted = quizzessCompleted;
        this.correctAnswers = correctAnswers;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getQuizzessCompleted() {
        return quizzessCompleted;
    }

    public void setQuizzessCompleted(int quizzessCompleted) {
        this.quizzessCompleted = quizzessCompleted;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void addXp(int i){
        this.xp += i;
    }

    public void addQuizzessCompleted(int i){
        this.quizzessCompleted += i;
    }

    public void addCorrectAnswers(int i){
        this.correctAnswers += i;
    }
}
