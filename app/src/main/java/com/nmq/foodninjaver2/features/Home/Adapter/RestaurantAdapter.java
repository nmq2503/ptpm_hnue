package com.nmq.foodninjaver2.features.Home.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.RestaurantDetail.ItemRestaurantActivity;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;
import com.nmq.foodninjaver2.features.Restaurant.RestaurantDetailActivity;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private ArrayList<RestaurantDomain> restaurantDomains;
    private ArrayList<RestaurantDomain> restaurantList;
    private OnRestaurantClickListener listener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(RestaurantDomain restaurant);
    }
    public RestaurantAdapter(ArrayList<RestaurantDomain> restaurantList, OnRestaurantClickListener listener) {
        this.restaurantList = restaurantList;
        this.listener = listener;
    }

    public RestaurantAdapter(ArrayList<RestaurantDomain> restaurantDomains) {
        this.restaurantDomains = restaurantDomains;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_res, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        RestaurantDomain currentItem = restaurantDomains.get(position);
        // Gan ten nha hang
        holder.title.setText(restaurantDomains.get(position).getTitle());
        // anh
        String picRes = currentItem.getPic();
        Context context = holder.itemView.getContext();
        if (picRes == null || picRes.isEmpty()) {
            // Nếu không có ảnh, hiển thị ảnh mặc định
            holder.pic.setImageResource(R.drawable.icon_undefine_user);
        } else {
            // Nếu pic là tên file trong drawable
            int drawableResourceId = context.getResources().getIdentifier(
                    picRes, "drawable", context.getPackageName()
            );

            if (drawableResourceId != 0) {
                // Nếu tìm thấy ảnh trong drawable, load ảnh
                Glide.with(context)
                        .load(drawableResourceId)
                        .error(R.drawable.icon_undefine_user) // Ảnh mặc định nếu có lỗi
                        .into(holder.pic);
            } else {
                // Nếu không tìm thấy, hiển thị ảnh mặc định
                holder.pic.setImageResource(R.drawable.icon_undefine_user);
            }
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), RestaurantDetailActivity.class);
            intent.putExtra("object", restaurantDomains.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        if (restaurantDomains != null){
            return restaurantDomains.size();
        }
        return 0;
    }

    public void updateList(ArrayList<RestaurantDomain> newList) {
        restaurantDomains.clear();
        restaurantDomains = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.res_name);
            pic = itemView.findViewById(R.id.imgRes);;

        }
    }
}