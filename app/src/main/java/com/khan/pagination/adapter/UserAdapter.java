package com.khan.pagination.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khan.pagination.R;
import com.khan.pagination.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final ArrayList<UserModel> userList;
    private final Context context;

    public UserAdapter(ArrayList<UserModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = userList.get(position);

        Picasso.get().load(userModel.getAvatar()).into(holder.imageView);
        holder.firstNameTV.setText(userModel.getFirstName());
        holder.lastNameTV.setText(userModel.getLastName());
        holder.emailTV.setText(userModel.getEmail());
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView firstNameTV, lastNameTV, emailTV;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            firstNameTV = itemView.findViewById(R.id.first_name_tv);
            lastNameTV = itemView.findViewById(R.id.last_name_tv);
            emailTV = itemView.findViewById(R.id.email_tv);
        }
    }
}

