package com.nmq.foodninjaver2.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.core.modelBase.UserModel;

import java.util.List;

public class ManagerUsersAdapter extends RecyclerView.Adapter<ManagerUsersAdapter.ManagerUsersViewHolder> {
    private List<UserModel> userList;

    // Constructor
    public ManagerUsersAdapter(List<UserModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ManagerUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manager_user, parent, false);
        return new ManagerUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerUsersViewHolder holder, int position) {
        // Get current user
        UserModel user = userList.get(position);

        // Bind data to views
        holder.tvUserName.setText(user.getUserName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText(user.getRole());
    }

    @Override
    public int getItemCount() {
        return userList.size(); // Return the total number of users
    }

    // ViewHolder class
    public static class ManagerUsersViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvEmail, tvRole;

        public ManagerUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            tvUserName = itemView.findViewById(R.id.tvNameUser);
            tvEmail = itemView.findViewById(R.id.tvEmailUser);
            tvRole = itemView.findViewById(R.id.tvRoleUser);
        }
    }
}
