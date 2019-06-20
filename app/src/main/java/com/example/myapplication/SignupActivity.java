package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private EditText firstnameEdittext;
    private EditText lastnameEdittext;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmpasswordEditText;
    private DatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstnameEdittext = findViewById(R.id.edt_enteryourfirstname);
        lastnameEdittext = findViewById(R.id.edt_enteryourlastname);
        emailEditText = findViewById(R.id.edt_entertheemail);
        passwordEditText = findViewById(R.id.edt_enteryourpassword);
        confirmpasswordEditText = findViewById(R.id.edt_enteryourconfirmpasswod);
        databaseHelper = new DatabaseHelper(this);
        firstnameEdittext.setText("aaa");
        lastnameEdittext.setText("aaa");
        emailEditText.setText("aaa@aaa.aaa");
        passwordEditText.setText("aaaaaaaa");
        confirmpasswordEditText.setText("aaaaaaaa");


        TextView signin = findViewById(R.id.txt_haveanaacount);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SigninActivity.class);
                startActivity(intent);
            }

        });
        Button btnsignup = findViewById(R.id.btn_signup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lastname = lastnameEdittext.getText().toString();
                final String firstname = firstnameEdittext.getText().toString();
                final String emailid = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String confirmpassword = confirmpasswordEditText.getText().toString();
                if (!isValidFirstname(firstname)) {
                    firstnameEdittext.setError("please valid first name");
                } else if (!isValidLastname(lastname)) {
                    lastnameEdittext.setError("please valid last name");
                } else if (!isValidEmail(emailid)) {
                    emailEditText.setError("please valid emailid");
                } else if (!isValidPassword(password)) {
                    passwordEditText.setError("please valid password");
                } else if (!isValidConfirmpassword(password, confirmpassword)) {
                    confirmpasswordEditText.setError("please valid confirmpassword");
                } else if (databaseHelper.checkUser(emailid)) {
                    Toast.makeText(SignupActivity.this, "Email are already exist", Toast.LENGTH_SHORT).show();
                } else {

                    UserModel user = new UserModel();
                    user.setEmail(emailid);
                    user.setPassword(password);
                    user.setFirstname(firstname);
                    user.setLastname(lastname);
                    user.setGender("");

                    boolean isUserCreated = databaseHelper.addUser(user);
                    if (isUserCreated) {
                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
//
            }
        });

        TextView txtsignin = findViewById(R.id.txt_haveanaacount);
        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean isValidFirstname(String firstname) {
        String FIRST_NAME_PATTERN = "[_A-Za-z]+([A-Za-z]{1,})";

        Pattern pattern = Pattern.compile(FIRST_NAME_PATTERN);
        Matcher matcher = pattern.matcher(firstname);
        return matcher.matches();
    }

    private boolean isValidLastname(String lastname) {
        int maxLength;
        String LAST_NAME_PATTERN = "[_A-Za-z]+([A-Za-z]{1,})";

        Pattern pattern = Pattern.compile(LAST_NAME_PATTERN);
        Matcher matcher = pattern.matcher(lastname);
        return matcher.matches();
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

    private boolean isValidConfirmpassword(String password, String conpassword) {
        if (conpassword.equals(password)) {
            return true;
        }
        return false;
    }
}