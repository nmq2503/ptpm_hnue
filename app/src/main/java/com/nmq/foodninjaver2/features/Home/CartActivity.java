package com.nmq.foodninjaver2.features.Home;

<<<<<<< HEAD
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
=======
import android.content.Intent;
import android.os.Bundle;
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nmq.foodninjaver2.R;
<<<<<<< HEAD
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

public class CartActivity extends AppCompatActivity {

    private DataBaseHelper dbHelper; // Đối tượng database helper
    private TextView cartSummary;    // TextView để hiển thị giỏ hàng
=======

public class CartActivity extends AppCompatActivity {

>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

<<<<<<< HEAD
        dbHelper = new DataBaseHelper(this); // Khởi tạo database helper
        cartSummary = findViewById(R.id.cart_summary); // Liên kết TextView với layout

=======
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_cart);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_cart) {
                Toast.makeText(CartActivity.this, "Cart", Toast.LENGTH_SHORT).show();
//                return true;
            } else if (item.getItemId() == R.id.action_profile){
                Intent intentProfile = new Intent(CartActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
            }
            else if (item.getItemId() == R.id.action_home) {
                Intent intentCart= new Intent(CartActivity.this, HomeActivity.class);
                startActivity(intentCart);
            } return true;

        });
<<<<<<< HEAD
        int orderId = 1; // ID đơn hàng
        loadCartItems(orderId);
    }
    // Phương thức load dữ liệu giỏ hàng từ database
    private void loadCartItems(int orderId) {
        Cursor cartItems = dbHelper.getCartItems(orderId);

        if (cartItems != null && cartItems.moveToFirst()) {
            StringBuilder cartDetails = new StringBuilder();

            do {
                @SuppressLint("Range") String name = cartItems.getString(cartItems.getColumnIndex("name"));
                @SuppressLint("Range") double price = cartItems.getDouble(cartItems.getColumnIndex("price"));
                @SuppressLint("Range") int quantity = cartItems.getInt(cartItems.getColumnIndex("quantity"));
                @SuppressLint("Range") double totalPrice = cartItems.getDouble(cartItems.getColumnIndex("total_price"));

                // Ghi chi tiết giỏ hàng vào StringBuilder
                cartDetails.append("Món: ").append(name)
                        .append(", Số lượng: ").append(quantity)
                        .append(", Giá: ").append(price)
                        .append(", Tổng giá: ").append(totalPrice)
                        .append("\n");
            } while (cartItems.moveToNext());

            // Hiển thị trong TextView
            cartSummary.setText(cartDetails.toString());
        } else {
            // Hiển thị thông báo khi giỏ hàng trống
            cartSummary.setText("Giỏ hàng của bạn hiện đang trống.");
        }

        // Đóng Cursor để giải phóng tài nguyên
        if (cartItems != null) {
            cartItems.close();
        }
=======
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
    }
}