package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        emailEditText = findViewById(R.id.edt_email);
        passwordEditText = findViewById(R.id.edt_pass);
        databaseHelper = new DatabaseHelper(this);
        Button btnsign = findViewById(R.id.btn_submit);
        btnsign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String pass = passwordEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email");
                } else if (!isValidPassword(pass)) {
                    passwordEditText.setError("Invalid password");
                } else {
                    if (databaseHelper.checkUser(email, pass)) {
                        isSavedUser(email, true);
                        Intent intent = new Intent(v.getContext(), DashboardActivity.class);
                        startActivityForResult(intent, 0);
                    } else {
                        Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
            }
        });

        TextView txtsignup = findViewById(R.id.txt_signup);
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        TextView txtforgetpassword = findViewById(R.id.txt_forgetpassword);
        txtforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SigninActivity.this, ForgetpasswordActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    private void isSavedUser (String email, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putBoolean("is_user_login", value);
        editor.apply();
    }

}