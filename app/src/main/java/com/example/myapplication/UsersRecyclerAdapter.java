package com.example.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by lalit on 10/10/2016.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;
    private DatabaseHelper databaseHelper;


    public UsersRecyclerAdapter(List<User> listUsers,DatabaseHelper databaseHelper) {
        this.listUsers = listUsers;
        this.databaseHelper = databaseHelper;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.textViewfName.setText(listUsers.get(position).getFirstname());
        holder.textViewlName.setText(listUsers.get(position).getLastname());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteUser(listUsers.get(position).getEmail());
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewfName,textViewlName,textViewEmail,imageViewDelete;

        public UserViewHolder(View view) {
            super(view);
            textViewfName =  view.findViewById(R.id.txt_usersfirstname);
            textViewlName = view.findViewById(R.id.txt_userslastname);
            textViewEmail = view.findViewById(R.id.txt_usersemailid);
            imageViewDelete = view.findViewById(R.id.btn_delete);
        }
    }


}
