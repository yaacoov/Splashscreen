/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package com.appdvl.yaacoov.splashscreen.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appdvl.yaacoov.splashscreen.R;
import com.appdvl.yaacoov.splashscreen.model.pojo.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @date 1/22/2016
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> {

    private static final String TAG = ContactAdapter.class.getSimpleName();
    private final FlowerClickListener mListener;
    private List<Contact> mContact;

    public ContactAdapter(FlowerClickListener listener) {
        mContact = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
   View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_view, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Contact currContect = mContact.get(position);

        holder.mName.setText(currContect.getName());
        holder.mAddress.setText(currContect.getAddress());
        holder.mEmail.setText(currContect.getEmail());
        holder.mGender.setText(currContect.getGender());


    }

    @Override
    public int getItemCount() {
        return mContact.size();
    }

    public void addContact(Contact contact) {
        mContact.add(contact);
        notifyDataSetChanged();
    }

    /**
     * @param position
     * @return
     */
    public Contact getSelectedContact(int position) {
        return mContact.get(position);
    }

    public void reset() {
        mContact.clear();
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView mName,mEmail,mAddress,mGender;

        Holder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.contactName);
            mAddress = (TextView) itemView.findViewById(R.id.contactAddress);
            mEmail = (TextView) itemView.findViewById(R.id.contactEmail);
            mGender = (TextView) itemView.findViewById(R.id.contactGender);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface FlowerClickListener {

        void onClick(int position);
    }
}
