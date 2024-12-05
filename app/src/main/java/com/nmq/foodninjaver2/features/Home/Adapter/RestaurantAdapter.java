package com.nmq.foodninjaver2.features.Home.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.RestaurantDetail.ItemRestaurantActivity;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private ArrayList<RestaurantDomain> restaurantDomains;

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
        holder.title.setText(restaurantDomains.get(position).getTitle());
//        String picUrl = "";

        @SuppressLint("DiscouragedApi") int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(restaurantDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        holder.pic.setImageResource(drawableResourceId);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), ItemRestaurantActivity.class);
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