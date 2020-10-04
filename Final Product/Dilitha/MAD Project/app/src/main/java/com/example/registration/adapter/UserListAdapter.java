package com.example.registration.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registration.R;
import com.example.registration.model.User;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private ArrayList<User> dataSet;
    private OnUserClickListener listener;

    public UserListAdapter(ArrayList<User> data, OnUserClickListener listener) {
        this.dataSet = data;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int listPosition) {

        final User user = dataSet.get(holder.getAdapterPosition());

        holder.tvName.setText(user.getName());
        holder.tvPhoneNumber.setText(user.getPhoneNumber());
        holder.backgroundCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnUserClickListener {
        void onUserClicked(User user);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvPhoneNumber;
        CardView backgroundCardView;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tv_name);
            this.tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);
            this.backgroundCardView = itemView.findViewById(R.id.card_view);
        }
    }
}