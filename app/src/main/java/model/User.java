package model;

import com.google.android.gms.location.places.Place;

import java.util.List;

import okhttp3.Route;

/**
 * Created by AndySchluenz on 11.01.16.
 */
public class User {

    private static User instance = null;

    private String UserId;

    protected User() {
    }

    public static User getInstance() {
        if (User.instance == null) {
            User.instance = new User();
        }
        return User.instance;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserId() {
        return UserId;
    }

}
