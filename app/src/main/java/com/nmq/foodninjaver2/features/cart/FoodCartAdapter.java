package com.nmq.foodninjaver2.features.cart;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;


import java.util.List;


public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartAdapter.FoodCartViewHolder> {
    private List<FoodCartModel> foodList;

    public FoodCartAdapter(List<FoodCartModel> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new FoodCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCartViewHolder holder, int position) {
        FoodCartModel food = foodList.get(position);
        holder.itemImg.setImageResource(food.getImgId());
        holder.itemName.setText(food.getName());
        holder.itemIngredient.setText(food.getIngredient());
        holder.itemPrice.setText(String.format("$%.2f", food.getPrice()));
        holder.itemQuantity.setText(String.valueOf(food.getQuantity()));

        holder.decreaseButton.setOnClickListener(v -> {
            food.setQuantity(food.getQuantity() - 1);
            if (food.getQuantity() == 0) {
                foodList.remove(position);
                notifyItemRemoved(position);
                return;
            }
            holder.itemQuantity.setText(String.valueOf(food.getQuantity()));
            notifyItemChanged(position);
        });

        holder.increaseButton.setOnClickListener(v -> {
            food.setQuantity(food.getQuantity() + 1);
            holder.itemQuantity.setText(String.valueOf(food.getQuantity()));
            notifyItemChanged(position);
        });

        holder.removeButton.setOnClickListener(v -> {
            foodList.remove(position);
            food.setQuantity(0);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodCartViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView itemImg;
        private TextView itemName;
        private TextView itemIngredient;
        private TextView itemPrice;
        private TextView itemQuantity;
        private Button decreaseButton;
        private Button increaseButton;
        private Button removeButton;

        @SuppressLint("WrongViewCast")
        public FoodCartViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các view từ layout
            cardView = itemView.findViewById(R.id.foodCom); // ID của CardView chứa món ăn
            itemImg = itemView.findViewById(R.id.dish_image); // ID của ImageView hiển thị ảnh món ăn
            itemName = itemView.findViewById(R.id.dish_title); // ID của TextView tên món ăn
            itemIngredient = itemView.findViewById(R.id.restaurant_name); // ID của TextView thành phần món ăn
            itemPrice = itemView.findViewById(R.id.dish_price); // ID của TextView giá món ăn
            itemQuantity = itemView.findViewById(R.id.quantity_text); // ID của TextView hiển thị số lượng món ăn
            decreaseButton = itemView.findViewById(R.id.button_minus); // ID của TextView nút giảm số lượng
            increaseButton = itemView.findViewById(R.id.button_plus); // ID của TextView nút tăng số lượng
            removeButton = itemView.findViewById(R.id.removeItemBtn); // ID của Button xóa món ăn
        }
    }
}