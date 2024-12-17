package com.nmq.foodninjaver2.features.cart;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView cartTextName, cartTextPrice, cartItemCount;
    public ImageView cartItemImage, buttonMinus, buttonPlus;
    private ItemClickListener itemClickListener;
    private AppCompatButton removeItemBtn;

    // Constructor
    @SuppressLint("WrongViewCast")
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartTextName = itemView.findViewById(R.id.dish_title);
        cartTextPrice = itemView.findViewById(R.id.dish_price);
        cartItemCount = itemView.findViewById(R.id.quantity_text);
        cartItemImage = itemView.findViewById(R.id.dish_image);
        buttonMinus = itemView.findViewById(R.id.button_minus);
        buttonPlus = itemView.findViewById(R.id.button_plus);
        removeItemBtn = itemView.findViewById(R.id.removeItemBtn);

        // Set click listener for buttons
        buttonMinus.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        removeItemBtn.setOnClickListener(this);

        // Set click listener for itemView (if needed)
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
