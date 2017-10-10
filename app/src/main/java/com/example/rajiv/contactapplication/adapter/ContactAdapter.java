package com.example.rajiv.contactapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rajiv.contactapplication.R;
import com.example.rajiv.contactapplication.pojo.Contact;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rajiv on 10-10-2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>  {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Contact> mContactList = Collections.EMPTY_LIST;

    public ContactAdapter(Context mContext, List<Contact> mContactList) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContactList = mContactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.adapter_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        final Contact finalContact = mContactList.get(position);
        holder.nameTextView.setText(finalContact.getName());

        if (finalContact.getEmails().size() > 0 && finalContact.getEmails().get(0) != null) {
            holder.emailTextView.setText(finalContact.getEmails().get(0).address);
        }

        if (finalContact.getNumbers().size() > 0 && finalContact.getNumbers().get(0) != null) {
            holder.phoneTextView.setText(finalContact.getNumbers().get(0).number);
        }
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, phoneTextView, emailTextView;
        View view;

        public ContactViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            nameTextView = (TextView) view.findViewById(R.id.tvName);
            phoneTextView = (TextView) view.findViewById(R.id.tvPhone);
            emailTextView = (TextView) view.findViewById(R.id.tvEmail);

        }
    }
}
