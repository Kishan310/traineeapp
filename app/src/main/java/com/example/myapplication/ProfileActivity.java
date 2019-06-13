package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileActivity extends AppCompatActivity {
    private EditText firstnameEditText, lastnameEditText, EmailidEditText;
    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";
    private Pattern pattern;
    private Matcher matcher;
    private DatabaseHelper databaseHelper;

    //    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseHelper = new DatabaseHelper(this);

        firstnameEditText = findViewById(R.id.edt_profileFirstName);
        lastnameEditText = findViewById(R.id.edt_profileLastName);
        EmailidEditText = findViewById(R.id.edt_email);
        EmailidEditText = findViewById(R.id.edt_email);
        Button btnupdate = findViewById(R.id.btn_Update);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = firstnameEditText.getText().toString();
                final String lastname = lastnameEditText.getText().toString();
                final String email = EmailidEditText.getText().toString();
//                final String password = PaEditText.getText().toString();


                if (!isValidfristname(firstname)) {
                    firstnameEditText.setError("Invalid fristanme");
                } else if (!isValidlastname(lastname)) {
                    lastnameEditText.setError("Invalid lastanme");
                } else if (!isValidEmail(email)) {
                    EmailidEditText.setError("Invalid Email");
                } else {
                    User user = new User();
                    user.setLastname(lastname);
                    user.setFirstname(firstname);
                    user.setEmail(email);
                    user.setGender("");

                    boolean isupdateuser = databaseHelper.updateuser(user);

                    if(isupdateuser) {
                        Intent intent = new Intent(ProfileActivity.this, SigninActivity.class);
                        startActivityForResult(intent, 0);
                    }else{

                        Toast.makeText(ProfileActivity.this, "data updated", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        setDefaultdata();
    }

    private void setDefaultdata() {

    }

    private boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidlastname(String lastname) {
        return lastname.length() > 0;
    }

    private boolean isValidfristname(String fristname) {
        return fristname.length() > 0;

    }

    private boolean isValidpassword(String password) {
        if (password != null && password.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isValiconfirmpassword(String password, String confirmpassword) {
        if (password == confirmpassword ){
            return true;
        }
        return false;
    }
}