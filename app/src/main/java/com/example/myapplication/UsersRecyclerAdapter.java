package com.example.myapplication;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class UsersRecyclerAdapter extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recycle);
//    }
//}
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by lalit on 10/10/2016.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public UsersRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycle, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewfirstName.setText(listUsers.get(position).getFirstname());
        holder.textViewlastName.setText(listUsers.get(position).getLastname());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
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

        public AppCompatTextView textViewfirstName;
        public AppCompatTextView textViewlastName;
        public AppCompatTextView textViewEmail;

        public UserViewHolder(View view) {
            super(view);
            textViewfirstName = (AppCompatTextView) view.findViewById(R.id.textViewFirstName);
            textViewlastName = (AppCompatTextView) view.findViewById(R.id.textViewLastName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
        }
    }


}
