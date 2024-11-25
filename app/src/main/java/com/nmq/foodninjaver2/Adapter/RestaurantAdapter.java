package com.nmq.foodninjaver2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nmq.foodninjaver2.Model.Restaurant;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.RestaurantDetailActivity;

import java.util.List;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context context;
    private List<Restaurant> listRestaurant;
    public RestaurantAdapter(@NonNull Context context, List<Restaurant> restaurantList) {
        super(context, 0, restaurantList);
        this.context = context;
        this.listRestaurant = restaurantList;
    }
    @NonNull
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        // Tạo hoặc tái sử dụng view cho mỗi item
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        }

        // Lấy đối tượng nhà hàng tại vị trí này
        Restaurant restaurant = listRestaurant.get(position);

        // Ánh xạ các view
        TextView nameTextView = convertView.findViewById(R.id.txtRestaurantName);
        TextView descriptionTextView = convertView.findViewById(R.id.txtRestaurantDescription);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBarRestaurant);

        // Gán dữ liệu vào các view
        nameTextView.setText(restaurant.getName());
        descriptionTextView.setText(restaurant.getDescription());
        ratingBar.setRating(restaurant.getRating());
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetailActivity.class);
            intent.putExtra("restaurant_id", restaurant.getId());  // Truyền restaurant_id
            context.startActivity(intent);
        });

        return convertView;
    }
}
