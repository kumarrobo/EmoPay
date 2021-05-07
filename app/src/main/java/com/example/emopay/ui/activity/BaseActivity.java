package com.example.emopay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emopay.viewmodel.SharedPrefManager;

import java.util.concurrent.TimeUnit;

public class BaseActivity extends AppCompatActivity
{
    private static final String TAG =BaseActivity.class.getSimpleName() ;
    private static android.os.Handler handler = new android.os.Handler();
    private static Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (handler == null) {
            handler = new Handler();
        } else {
            handler.removeCallbacks(runnable);
        }
        if (runnable == null)
            runnable = new Runnable() {
                @Override
                public void run() {
                    SharedPrefManager.getInstance(getApplicationContext()).logoutUser();
                    //do your task here
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                   // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(BaseActivity.this, "User logout due to inactivity", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"logout due to inactivity");
                }
            };
        start();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stop();
        start();

    }

    void start() {
        handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(15));
    }

    void stop() {
        handler.removeCallbacks(runnable);
    }

    /*public static final long TIMEOUT_IN_MILLI = 1000 * 60;
    public static final String PREF_FILE = "App_Pref";
    public static final String KEY_SP_LAST_INTERACTION_TIME = "KEY_SP_LAST_INTERACTION_TIME";
    private static final String TAG =BaseActivity.class.getSimpleName() ;

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (isValidLogin())
            getSharedPreference().edit().putLong(KEY_SP_LAST_INTERACTION_TIME, System.currentTimeMillis()).apply();
        else logout();
    }

    public SharedPreferences getSharedPreference() {
        return getSharedPreferences(PREF_FILE, MODE_PRIVATE);
    }

    public boolean isValidLogin() {
        long last_edit_time = getSharedPreference().getLong(KEY_SP_LAST_INTERACTION_TIME, 0);
        return last_edit_time == 0 || System.currentTimeMillis() - last_edit_time < TIMEOUT_IN_MILLI;
    }

    public void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        Toast.makeText(this, "User logout due to inactivity", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"logout due to inactivity");
        getSharedPreference().edit().remove(KEY_SP_LAST_INTERACTION_TIME).apply(); // make shared preference null.
    }*/
}
/*implements LogoutListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(App.getApplicationTheme());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Set Listener to receive events
        ApplicationClass.registerSessionListener(this);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //reset session when user interact
        ApplicationClass.resetSession();

    }

    @Override
    public void onSessionLogout() {
        // Do You Task on session out
        startActivity(new Intent(this,LoginActivity.class));
    }

}*/