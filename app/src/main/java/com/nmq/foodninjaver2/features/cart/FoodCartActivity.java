package com.nmq.foodninjaver2.features.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;

import java.util.ArrayList;
import java.util.List;

public class FoodCartActivity extends AppCompatActivity implements FoodCartAdapter.QuantityChangeListener {

    private RecyclerView foodCartItems;
    private TextView subtotalValue, deliveryValue, discountValue, totalValue;
    private ImageView backBtn;
    private ScrollView scrollViewCart;

    private List<FoodCartModel> foodCartList;
    private FoodCartAdapter foodCartAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcart);

        initializeUIComponents();
        setupButtonListeners();

        foodCartList = new ArrayList<>();
        // Thêm dữ liệu mẫu vào giỏ hàng
        addSampleDataToCart();
        displayCartItems(foodCartList);
    }

    private void initializeUIComponents() {
        foodCartItems = findViewById(R.id.cardView);
        subtotalValue = findViewById(R.id.textViewSubtotalAmount);
        deliveryValue = findViewById(R.id.textViewDeliveryAmount);
        discountValue = findViewById(R.id.textViewDiscountAmount);
        totalValue = findViewById(R.id.textViewTotalAmount);
        backBtn = findViewById(R.id.backBtn);
        scrollViewCart = findViewById(R.id.scrollviewcart);
    }

    private void setupButtonListeners() {
        backBtn.setOnClickListener(v -> finish());

        findViewById(R.id.buttonPlaceOrder).setOnClickListener(v -> {
            // Mô phỏng đặt hàng thành công
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void addSampleDataToCart() {
        // Thêm dữ liệu mẫu vào giỏ hàng (Thay vì lấy từ cơ sở dữ liệu)
        foodCartList.add(new FoodCartModel(1, "Soup", "", 12.99, 2, R.drawable.img1));
        foodCartList.add(new FoodCartModel(2, "Noodles", "", 8.49, 1, R.drawable.img2));
        foodCartList.add(new FoodCartModel(3, "Pasta", "", 10.99, 3, R.drawable.img3));
    }

    private void displayCartItems(List<FoodCartModel> foodCartList) {
        foodCartAdapter = new FoodCartAdapter(this, foodCartList, this);
        foodCartItems.setAdapter(foodCartAdapter);
        foodCartItems.setLayoutManager(new GridLayoutManager(this, 1));

        updatePriceDetails(foodCartList);

        if (foodCartList.isEmpty()) {
            scrollViewCart.setVisibility(View.GONE);
            findViewById(R.id.buttonPlaceOrder).setEnabled(false);
        } else {
            scrollViewCart.setVisibility(View.VISIBLE);
            findViewById(R.id.buttonPlaceOrder).setEnabled(true);
        }
    }

    private void updatePriceDetails(List<FoodCartModel> foodCartList) {
        double subtotal = 0.0;
        double deliveryCharge = 5.0; // Giá cố định
        double discount = 0.0; // Giảm giá cố định
        for (FoodCartModel model : foodCartList) {
            subtotal += model.getPrice() * model.getQuantity();
        }

        double total = subtotal + deliveryCharge - discount;

        subtotalValue.setText(String.format("$%.2f", subtotal));
        deliveryValue.setText(String.format("$%.2f", deliveryCharge));
        discountValue.setText(String.format("$%.2f", discount));
        totalValue.setText(String.format("$%.2f", total));
    }

    @Override
    public void onQuantityChanged(FoodCartModel model, int newQuantity) {
        // Cập nhật lại số lượng món trong giỏ hàng
        model.setQuantity(newQuantity);
        foodCartAdapter.notifyDataSetChanged();
        updatePriceDetails(foodCartList);
    }

    /**
     *
     */
    @Override
    public void onQuantityChanged() {

    }

    private void saveOrder() {
        // Thực hiện lưu trữ đơn hàng (Chức năng không cần thiết ở đây)
    }

    @Override
    public void onTotalPriceChanged(double totalPrice) {
        // Cập nhật lại giá trị tổng khi thay đổi số lượng món
        updatePriceDetails(foodCartList);
    }
}
