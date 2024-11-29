package com.nmq.foodninjaver2.features.cart;

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

        if (foodList.size() > 0) {
            foodList.remove(position);
            notifyItemRemoved(position);
        }


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

        int currentPosition = holder.getAdapterPosition();
        foodList.remove(currentPosition);
        notifyItemRemoved(currentPosition);

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

        public FoodCartViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodCom);
            itemImg = itemView.findViewById(R.id.dish_image);
            itemName = itemView.findViewById(R.id.tvProductName);
            itemIngredient = itemView.findViewById(R.id.restaurant_name);
            itemPrice = itemView.findViewById(R.id.tvProductPrice);
            itemQuantity = itemView.findViewById(R.id.tvQuantity);
            decreaseButton = itemView.findViewById(R.id.btnDecrease);
            increaseButton = itemView.findViewById(R.id.btnIncrease);
            removeButton = itemView.findViewById(R.id.removeItemBtn);
        }
    }
}
