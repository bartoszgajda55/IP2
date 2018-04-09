package com.quizapp.ip2.Helper;

import com.quizapp.ip2.Model.User;

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
}
