package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WebServicesActivity extends AppCompatActivity implements View.OnClickListener {
    //    private String url = "https://reqres.in/api/users?page=2";
    EditText edtName, edtJob, txtresponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_services);

        init();

    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtJob = findViewById(R.id.edt_job);
        edtName = findViewById(R.id.edt_name);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_put).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                getResponse("https://reqres.in/api/unknown/2");
                break;
            case R.id.btn_post:
                postService("https://reqres.in/api/users");
                break;
            case R.id.btn_put:
                putResponse("https://reqres.in/api/users/2");
            case R.id.btn_delete:
                deleteResponse("https://reqres.in/api/users/2");
        }
    }

    private void getResponse(String url) {
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parseGetResponse(response);
                Log.e("onResponse: ", response);
                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error :", error.toString());
                Toast.makeText(WebServicesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(mStringRequest);
    }

    private void parseGetResponse(String response) {
        try {
            JSONObject responseJSONObject = new JSONObject(response);
            int page = responseJSONObject.getInt("page");
            String perpage = responseJSONObject.getString("per_page");
            String total = responseJSONObject.getString("total");
            String totalPage = responseJSONObject.getString("total_pages");
            JSONArray dataJSONArray = responseJSONObject.getJSONArray("data");
            for (int i = 0; i < dataJSONArray.length(); i++) {
                JSONObject dataJSONObject = dataJSONArray.getJSONObject(i);
                int id = dataJSONObject.getInt("id");
                String email = dataJSONObject.getString("email");
                String firstname = dataJSONObject.getString("first_name");
                String lastname = dataJSONObject.getString("last_name");
                String avatar = dataJSONObject.getString("avatar");
            }

        } catch (Exception e) {
            Log.e("parseGetResponse", e + "");
        }

    }

    private void postService(String url) {
        final String name = edtName.getText().toString().trim();
        final String job = edtJob.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(WebServicesActivity.this, response, Toast.LENGTH_LONG).show();
                        parsePostResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WebServicesActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("job", job);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parsePostResponse(String response) {
        try {
            JSONObject responseJSONObject = new JSONObject(response);
            int id = responseJSONObject.getInt("id");
            String name = responseJSONObject.getString("name");
            String job = responseJSONObject.getString("job");
            String createdAt = responseJSONObject.getString("createdAt");
            txtresponse.setText(createdAt);
            txtresponse.setText(job);
            txtresponse.setText(name);
            txtresponse.setText(id);
        } catch (Exception e) {
            Log.e("parsePOSTResponse", e.toString());
        }
    }

    private void putResponse(String url) {
        final String name = edtName.getText().toString().trim();
        final String job = edtJob.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen
                parsePutResponse(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WebServicesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("job", job);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void parsePutResponse(String response) {
        try {
            JSONObject responseJsonObject = new JSONObject(response);
            String updateAt = responseJsonObject.getString("updateAt");
            String name = responseJsonObject.getString("name");
            int id = responseJsonObject.getInt("id");
            txtresponse.setText(name);
            txtresponse.setText(id);
            txtresponse.setText(updateAt);
        } catch (Exception e) {
            Log.e("parsePUTResponse", e.toString());
        }
    }

    private void deleteResponse(String url) {
        RequestQueue mRequestQueue;
        StringRequest mStringRequest;
        mRequestQueue = Volley.newRequestQueue(this);
        mStringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseDeleteResponse(response);
//                Log.e("onResponse: ", response);
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

    private void parseDeleteResponse(String response) {
        try {
            JSONObject responseJSONObject = new JSONObject(response);
            JSONArray batterssJSONArray = responseJSONObject.getJSONArray("batterss");
            for (int i = 0; i < batterssJSONArray.length(); i++) {
                JSONObject response1JSONObject = batterssJSONArray.getJSONObject(i);
                JSONArray battersJSONArray = response1JSONObject.getJSONArray("batters");
                for (int j = 0; j < battersJSONArray.length(); j++) {
                    JSONObject response1lJSONObject = battersJSONArray.getJSONObject(i);
                    JSONArray batterJSONArray = responseJSONObject.getJSONArray("batter");
                    for (int k = 0; k < batterJSONArray.length(); k++) {
                        JSONObject response1llJSONObject = batterJSONArray.getJSONObject(i);
                        String type = response1JSONObject.getString("type");
                        txtresponse.setText(response);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("parseDELETERespone", e.toString());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
