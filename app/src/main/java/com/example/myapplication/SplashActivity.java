package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (isuserlogin()) {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, SigninActivity.class));
                    finish();
                }
            }
        }, secondsDelayed * 1000);
    }

    private boolean isuserlogin() {
        SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);
        boolean isLogin = sharedPreferences.getBoolean("is_user_login", false);
        return isLogin;
        }
}

