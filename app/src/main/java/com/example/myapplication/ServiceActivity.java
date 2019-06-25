package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    //button objects
    private Button buttonStart;
    private Button buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //getting buttons from xml
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        //attaching onclicklistener to buttons
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonStart) {
            //start the service here
        } else if (view == buttonStop) {
            //stop the service here
        }
    }
}