package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class UserlistActivity extends AppCompatActivity {

    private List<UserModel> listUsers;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        initViews();
    }

    private void initViews() {
        databaseHelper = new DatabaseHelper(this);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        listUsers = new ArrayList<>();

        UsersRecyclerAdapter usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers,databaseHelper);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        listUsers.clear();
        listUsers.addAll(databaseHelper.getAllUser());
        usersRecyclerAdapter.notifyDataSetChanged();
    }
}
