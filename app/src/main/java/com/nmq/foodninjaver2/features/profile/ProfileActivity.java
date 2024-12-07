package com.nmq.foodninjaver2.features.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.features.Home.CartActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Setup Edge-to-Edge insets for the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý sự kiện cho biểu tượng Edit
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView editIcon = findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        // Lặp qua các nút "Buy Again" để gắn sự kiện
        Button buyAgainButton1 = findViewById(R.id.buy_again_button1);
        Button buyAgainButton2 = findViewById(R.id.buy_again_button2);
        Button buyAgainButton3 = findViewById(R.id.buy_again_button3);
        Button buyAgainButton4 = findViewById(R.id.buy_again_button4);

        View.OnClickListener buyAgainListener = v -> {
            // Chuyển sang CartActivity
            Intent intent = new Intent(ProfileActivity.this, CartActivity.class);

            // Lấy dữ liệu giỏ hàng từ database,
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            int orderId = 1; //
            Cursor cursor = dbHelper.getCartItems(orderId);

            if (cursor != null && cursor.moveToFirst()) {
                // Truyền dữ liệu sang CartActivity thông qua Intent
                StringBuilder cartItems = new StringBuilder();
                do {
                    @SuppressLint("Range")
                    String itemName = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range")
                    int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                    cartItems.append(itemName).append(" x").append(quantity).append("\n");
                } while (cursor.moveToNext());

                intent.putExtra("cart_data", cartItems.toString());
                cursor.close(); // Đóng cursor sau khi sử dụng
            } else {
                Toast.makeText(this, "Giỏ hàng của bạn trống!", Toast.LENGTH_SHORT).show();
            }

            startActivity(intent);
        };

        buyAgainButton1.setOnClickListener(buyAgainListener);
        buyAgainButton2.setOnClickListener(buyAgainListener);
        buyAgainButton3.setOnClickListener(buyAgainListener);
        buyAgainButton4.setOnClickListener(buyAgainListener);
    }
}