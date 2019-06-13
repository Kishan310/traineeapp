package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Userlist extends AppCompatActivity {

    private List<User> listUsers;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewUsers;
    private AppCompatTextView textViewfirstname;
    private AppCompatTextView textViewlastname;
    private AppCompatTextView textViewemailid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        databaseHelper = new DatabaseHelper(this);

    }

    private void initViews() {
        textViewfirstname = (AppCompatTextView) findViewById(R.id.txt_firstname);
        textViewlastname = (AppCompatTextView) findViewById(R.id.txt_lastname);
        textViewemailid = (AppCompatTextView) findViewById(R.id.txt_emailid);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }


    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            private ArrayAdapter usersRecyclerAdapter;

            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
