package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebServicesActivity extends AppCompatActivity implements View.OnClickListener{
//    private String url = "https://reqres.in/api/users?page=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_services);

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse("https://reqres.in/api/users?page=2");
                postResponse("https://reqres.in/api/users");
                putResponse("https://reqres.in/api/users/2");
                deleteResponse("https://reqres.in/api/users/2");
            }
        });

    }

    private void getResponse(String url) {

        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("onResponse: ",response );
                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error :", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void postResponse(String url){
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("onResponse: ",response );
                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error :", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void putResponse(String url){
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("onResponse: ",response );
                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error :", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void deleteResponse(String url){
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("onResponse: ",response );
                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error :", error.toString());
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    @Override
    public void onClick(View view) {

    }
}
