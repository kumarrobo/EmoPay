package com.example.emopay.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.emopay.ui.activity.MainActivity2;
import com.example.emopay.model.User;

public class SharedPrefManager {

    public static final String ISLOGIN ="IsLOoggedIn" ;
    private static final int PRIVATE_MODE = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static final String SHARED_PREF_NAME = "login";
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager sharedPrefManager;
    @SuppressLint("StaticFieldLeak")
      Context context;
    public static final String KEY_TOKEN = "token";
    public static final String KEY_TOKENTYPE = "tokentype";

    @SuppressLint("CommitPrefEdits")
    public SharedPrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (sharedPrefManager == null) {
            sharedPrefManager = new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(ISLOGIN,true);
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_TOKENTYPE, user.getTokentype());
        editor.apply();
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, MainActivity2.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
        /*else {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
        }*/

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(ISLOGIN, false);
    }
}