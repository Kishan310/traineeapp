package com.example.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends AppCompatActivity {

    private EditText firstnameEdittext;
    private EditText lastnameEdittext;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmpasswordEditText;
    private DatabaseHelper databaseHelper;
    private CircleImageView ivPhoto;
    private Uri selectedImage;
    private Bitmap photoBitmap;
    private String TAG = "SignupActivity";
    private final int PICK_IMAGE_CAMERA = 0, PICK_IMAGE_GALLERY = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();

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

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);

        databaseHelper = new DatabaseHelper(this);
        firstnameEdittext = findViewById(R.id.edt_enteryourfirstname);
        lastnameEdittext = findViewById(R.id.edt_enteryourlastname);
        emailEditText = findViewById(R.id.edt_entertheemail);
        passwordEditText = findViewById(R.id.edt_enteryourpassword);
        confirmpasswordEditText = findViewById(R.id.edt_enteryourconfirmpasswod);
        databaseHelper = new DatabaseHelper(this);

        ivPhoto = findViewById(R.id.iv_photo);
    }

    public void onClickSelectPhoto(View view) {
        if (Build.VERSION.SDK_INT < 23) {
            selectImage();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission
                    .CAMERA) == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                ActivityCompat.requestPermissions(SignupActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case 1:
                    goWithCameraPermission(grantResults);
                    break;
                default:
                    break;
            }
        }
    }

    private void goWithCameraPermission(int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SignupActivity.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("Select Option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    selectedImage = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "image_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, selectedImage);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(pickPhoto, "Compelete action using"),
                            PICK_IMAGE_GALLERY);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case PICK_IMAGE_CAMERA:
                try {
                    if (selectedImage != null) {
                        Bitmap photoBitmap = BitmapFactory.decodeFile(selectedImage.getPath());
                        setImageData(photoBitmap);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "PICK_FROM_CAMERA" + e);
                }
                break;

            case PICK_IMAGE_GALLERY:
                try {
                    if (resultCode == RESULT_OK) {
                        Uri uri = imageReturnedIntent.getData();
                        if (uri != null) {
                            Bitmap bitmap = null;
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            setImageData(bitmap);
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "PICK_FROM_GALLERY" + e);
                }
                break;
        }
    }

    private void setImageData(Bitmap bitmap) {
        try {
            if (bitmap != null) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                Glide.with(this)
                        .load(out.toByteArray())
                        .asBitmap()
                        .into(ivPhoto);
            } else {
                Toast.makeText(this, "unable to select image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "setImageData" + e);
        }
    }

}
