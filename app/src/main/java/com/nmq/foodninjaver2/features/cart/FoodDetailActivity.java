package com.nmq.foodninjaver2.features.cart;

package com.nmq.foodninjaver2.features.fooddetails;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.models.FoodCartModel;

import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {

    // UI Components
    private TextView foodName, foodPrice, foodQuantity, ingredientText;
    private Button increaseButton, decreaseButton, addToCartButton;
    private CheckBox topping1, topping2, topping3;

    // Food Information
    private String name = "Ground Beef Taco";
    private double price = 9.50;
    private int quantity = 1;

    // Add-ons price
    private double topping1Price = 2.30;
    private double topping2Price = 4.70;
    private double topping3Price = 2.50;

    // Cart (static for simplicity)
    private static List<FoodCartModel> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Bind UI components
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.price_tag);
        foodQuantity = findViewById(R.id.quantity);
        ingredientText = findViewById(R.id.ingredient_txt);

        increaseButton = findViewById(R.id.increaseItemDetailQuantity);
        decreaseButton = findViewById(R.id.decreaseItemDetailQuantity);
        addToCartButton = findViewById(R.id.add_to_cart_button);

        topping1 = findViewById(R.id.topping1);
        topping2 = findViewById(R.id.topping2);
        topping3 = findViewById(R.id.topping3);

        // Set initial data
        foodName.setText(name);
        updatePrice();

        // Event listeners
        increaseButton.setOnClickListener(v -> {
            quantity++;
            foodQuantity.setText(String.valueOf(quantity));
            updatePrice();
        });

        decreaseButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                foodQuantity.setText(String.valueOf(quantity));
                updatePrice();
            }
        });

        addToCartButton.setOnClickListener(v -> {
            double finalPrice = calculateFinalPrice();
            addFoodToCart(name, finalPrice, quantity);
            Toast.makeText(FoodDetailActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updatePrice() {
        double totalPrice = calculateFinalPrice();
        foodPrice.setText(String.format("%.2f", totalPrice));
    }

    private double calculateFinalPrice() {
        double totalPrice = price * quantity;

        // Add toppings
        if (topping1.isChecked()) {
            totalPrice += topping1Price * quantity;
        }
        if (topping2.isChecked()) {
            totalPrice += topping2Price * quantity;
        }
        if (topping3.isChecked()) {
            totalPrice += topping3Price * quantity;
        }

        return totalPrice;
    }

    private void addFoodToCart(String name, double price, int quantity) {
        // Create FoodCartModel object
        FoodCartModel food = new FoodCartModel(name, price, quantity);

        // Add to cart
        cart.add(food);
    }

    public static List<FoodCartModel> getCart() {
        return cart;
    }
}
