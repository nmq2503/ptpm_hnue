package com.nmq.foodninjaver2.admin.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.core.modelBase.RestaurantModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManagerRestaurantsAdapter extends RecyclerView.Adapter<ManagerRestaurantsAdapter.ManagerRestaurantsViewHolder> {
    private List<RestaurantModel> restaurantList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    // Constructor
    public ManagerRestaurantsAdapter(List<RestaurantModel> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public ManagerRestaurantsAdapter.ManagerRestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manager_restaurant, parent, false);
        return new ManagerRestaurantsViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerRestaurantsViewHolder holder, int position) {
        // Lấy đối tượng người dùng hiện tại
        RestaurantModel restaurant = restaurantList.get(position);

        // Gắn dữ liệu vào các View
        holder.tvNameRestaurant.setText(restaurant.getRestaurantName());
        holder.tvAddressRestaurant.setText(restaurant.getAddress());
        holder.tvRoleUser.setText(restaurant.getOwnerName());

        // Đường dẫn ảnh lưu trong database
        String imagePath = restaurant.getUrlImageRestaurant();

        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Glide.with(holder.itemView.getContext())
                        .load(imageFile)
                        .placeholder(R.drawable.icon_undefine_user)
                        .error(R.drawable.icon_undefine_user)
                        .into(holder.ivRestaurantImage);
            } else {
                holder.ivRestaurantImage.setImageResource(R.drawable.icon_undefine_user);
            }
        } else {
            holder.ivRestaurantImage.setImageResource(R.drawable.icon_undefine_user);
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
        return restaurantList.size(); // Return the total number of users
    }

    // Hàm lấy người dùng được chọn
    public RestaurantModel getSelectedRestaurant() {
        return selectedPosition != RecyclerView.NO_POSITION ? restaurantList.get(selectedPosition) : null;
    }

    // ViewHolder class
    public static class ManagerRestaurantsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameRestaurant, tvAddressRestaurant, tvRoleUser;
        ImageView ivRestaurantImage;

        public ManagerRestaurantsViewHolder(@NonNull View itemView, final ManagerRestaurantsAdapter adapter) {
            super(itemView);
            // Initialize views
            tvNameRestaurant = itemView.findViewById(R.id.tvNameRestaurant);
            tvAddressRestaurant = itemView.findViewById(R.id.tvAddressRestaurant);
            tvRoleUser = itemView.findViewById(R.id.tvRoleUser);
            ivRestaurantImage = itemView.findViewById(R.id.ivRestaurantImage);

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
        List<RestaurantModel> filteredList = new ArrayList<>();
        for (RestaurantModel item : restaurantList) {
            if (item.getRestaurantName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        updateData(filteredList); // Cập nhật danh sách RecyclerView
    }

    // Thêm phương thức cập nhật danh sách
    public void updateData(List<RestaurantModel> newList) {
        this.restaurantList = newList;
        notifyDataSetChanged();
    }
}