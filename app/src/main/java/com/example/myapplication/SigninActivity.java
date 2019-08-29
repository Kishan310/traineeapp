package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity {
    private EditText EmailEditText;
    private EditText passwordEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        EmailEditText = findViewById(R.id.edt_email);
        passwordEditText = findViewById(R.id.edt_pass);
        databaseHelper = new DatabaseHelper(this);
        EmailEditText.setText("aaa@aaa.aaa");
        passwordEditText.setText("aaaaaaaa");
        Button btnsign = findViewById(R.id.btn_submit);
        btnsign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                displayToken();
                final String pass = passwordEditText.getText().toString();
                final String email = EmailEditText.getText().toString();
                if (!isValidEmail(email)) {
                    EmailEditText.setError("Invalid Email");
                } else if (!isValidPassword(pass)) {
                    passwordEditText.setError("Invalid password");
                } else {
                    if (databaseHelper.checkUser(email, pass)) {
                        isUserLoggedIn(email, true);
                        Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        Toast.makeText(SigninActivity.this, "signin sucessfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SigninActivity.this, "Invalid email or Password", Toast.LENGTH_SHORT).show();
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

    private void isUserLoggedIn(String Email, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", Email);
        editor.putBoolean("is_user_login", value);
        editor.apply();
    }

    public void displayToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("getInstanceId failed", task.getException().toString());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("Token-------", token);
                    }
                });
    }
}