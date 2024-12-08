package com.nmq.foodninjaver2.features.cart;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;

import java.util.ArrayList;
import java.util.List;

public class FoodCartActivity extends AppCompatActivity {
    private RecyclerView foodCartItems;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcart);
        List<FoodCartModel> foodCartList = new ArrayList<FoodCartModel>();
        foodCartList.add(new FoodCartModel("Red n hot pizza", "Spicy chicken, beef", 15.30, 2, R.drawable.btn1));
        foodCartList.add(new FoodCartModel("Greek salad", "with baked salmon", 12.00, 2, R.drawable.btn2));

        foodCartItems = findViewById(R.id.cardView);

        FoodCartAdapter foodCartAdapter = new FoodCartAdapter(foodCartList);
        foodCartItems.setAdapter(foodCartAdapter);
        foodCartItems.setLayoutManager(new GridLayoutManager(this, 1));
    }
}
