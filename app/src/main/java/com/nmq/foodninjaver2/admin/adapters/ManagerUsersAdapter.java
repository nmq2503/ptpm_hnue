package com.nmq.foodninjaver2.admin.adapters;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.core.modelBase.UserModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManagerUsersAdapter extends RecyclerView.Adapter<ManagerUsersAdapter.ManagerUsersViewHolder> {
    private List<UserModel> userList;
    private int selectedPosition = RecyclerView.NO_POSITION; // Vị trí phần tử được chọn, ban đầu không có gì được chọn

    // Constructor
    public ManagerUsersAdapter(List<UserModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ManagerUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manager_user, parent, false);
        return new ManagerUsersViewHolder(view, this); // Truyền Adapter vào ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerUsersViewHolder holder, int position) {
        // Lấy đối tượng người dùng hiện tại
        UserModel user = userList.get(position);

        // Gắn dữ liệu vào các View
        holder.tvUserName.setText(user.getUserName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText(user.getRoleName());

        // Đường dẫn ảnh lưu trong database
        String imagePath = user.getUrlImageProfile();

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Glide.with(holder.itemView.getContext())
                        .load(imageFile)
                        .placeholder(R.drawable.icon_undefine_user)
                        .error(R.drawable.icon_undefine_user)
                        .into(holder.ivProfileImage);
            } else {
                holder.ivProfileImage.setImageResource(R.drawable.icon_undefine_user);
            }
        } else {
            holder.ivProfileImage.setImageResource(R.drawable.icon_undefine_user);
        }

        // Đổi màu nền nếu phần tử được chọn
        holder.itemView.findViewById(R.id.container).setBackgroundColor(
                selectedPosition == position
                        ? Color.parseColor("#FFDDDD") // Màu nền khi được chọn
                        : Color.parseColor("#FFFFFF") // Màu nền mặc định
        );
    }

    @Override
    public int getItemCount() {
        return userList.size(); // Return the total number of users
    }

    // Hàm lấy người dùng được chọn
    public UserModel getSelectedUser() {
        return selectedPosition != RecyclerView.NO_POSITION ? userList.get(selectedPosition) : null;
    }

    // ViewHolder class
    public static class ManagerUsersViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvEmail, tvRole;
        ImageView ivProfileImage;

        public ManagerUsersViewHolder(@NonNull View itemView, final ManagerUsersAdapter adapter) {
            super(itemView);
            // Initialize views
            tvUserName = itemView.findViewById(R.id.tvNameUser);
            tvEmail = itemView.findViewById(R.id.tvEmailUser);
            tvRole = itemView.findViewById(R.id.tvRoleUser);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);

            // Set click listener for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousPosition = adapter.selectedPosition;
                    adapter.selectedPosition = getAdapterPosition(); // Cập nhật vị trí được chọn
                    adapter.notifyItemChanged(previousPosition); // Cập nhật lại item trước đó
                    adapter.notifyItemChanged(adapter.selectedPosition); // Cập nhật item được chọn
                }
            });
        }
    }

    public void clearSelection() {
        int previousPosition = selectedPosition;
        selectedPosition = RecyclerView.NO_POSITION;
        notifyItemChanged(previousPosition); // Cập nhật lại phần tử trước đó
    }

    public void filter(String text) {
        List<UserModel> filteredList = new ArrayList<>();
        for (UserModel item : userList) {
            if (item.getUserName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        updateData(filteredList); // Cập nhật danh sách RecyclerView
    }

    // Thêm phương thức cập nhật danh sách
    public void updateData(List<UserModel> newList) {
        this.userList = newList;
        notifyDataSetChanged();
    }
}
