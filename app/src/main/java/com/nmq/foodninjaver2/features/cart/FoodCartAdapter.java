package com.nmq.foodninjaver2.features.cart;

import android.content.Context;
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

    public FoodCartAdapter(Context context, List<FoodCartModel> foodList, QuantityChangeListener listener) {
        this.context = context;
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


        holder.itemName.setText(food.getName());
        holder.itemIngredient.setText(food.getIngredient());
        holder.itemPrice.setText(String.format("$%.2f", food.getPrice()));
        holder.itemQuantity.setText(String.valueOf(food.getQuantity()));

        holder.increaseButton.setOnClickListener(v -> increaseQuantity(food, holder, position));
        holder.decreaseButton.setOnClickListener(v -> decreaseQuantity(food, position, holder));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    private void decreaseQuantity(FoodCartModel food, int position, FoodCartViewHolder holder) {
        if (food.getQuantity() > 1) {
            food.setQuantity(food.getQuantity() - 1);
            holder.itemQuantity.setText(String.valueOf(food.getQuantity()));
            notifyTotalPrice();
        } else {
            removeItemFromCart(food, position);
        }
    }

    private void increaseQuantity(FoodCartModel food, FoodCartViewHolder holder, int position) {
        food.setQuantity(food.getQuantity() + 1);
        holder.itemQuantity.setText(String.valueOf(food.getQuantity()));
        notifyTotalPrice();
    }

    private void removeItemFromCart(FoodCartModel food, int position) {
        foodList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, foodList.size());
        notifyTotalPrice();
    }

    private void notifyTotalPrice() {
        double totalPrice = 0.0;
        for (FoodCartModel item : foodList) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        quantityChangeListener.onTotalPriceChanged(totalPrice);
    }

    static class FoodCartViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView itemImg;
        private TextView itemName;
        private TextView itemIngredient;
        private TextView itemPrice;
        private TextView itemQuantity;
        private Button decreaseButton;
        private Button increaseButton;

        public FoodCartViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodCom);
            itemImg = itemView.findViewById(R.id.dish_image);
            itemName = itemView.findViewById(R.id.dish_title);
            itemIngredient = itemView.findViewById(R.id.restaurant_name);
            itemPrice = itemView.findViewById(R.id.dish_price);
            itemQuantity = itemView.findViewById(R.id.quantity_text);
            decreaseButton = itemView.findViewById(R.id.button_minus);
            increaseButton = itemView.findViewById(R.id.button_plus);
        }
    }

    public interface QuantityChangeListener {
        void onQuantityChanged(FoodCartModel model, int newQuantity);

        void onQuantityChanged();
        void onTotalPriceChanged(double totalPrice);
    }
}