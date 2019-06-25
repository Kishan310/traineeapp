package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder>{

    private List<ContactModel> contactModelList;
    private Context mContext;
    public AllContactsAdapter(List<ContactModel> contactVOList, Context mContext){
        this.contactModelList = contactVOList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_single_contect_view, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ContactModel contactModel = contactModelList.get(position);
        holder.txt_contactname.setText(contactModel.getContactName());
        holder.txt_phonenumber.setText(contactModel.getContactPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        TextView txt_contactname,txt_phonenumber;

        public ContactViewHolder(View itemView) {
            super(itemView);
            txt_contactname= itemView.findViewById(R.id.txt_contactname);
            txt_phonenumber = itemView.findViewById(R.id.txt_phonenumber);
        }
    }
}