package com.nmq.foodninjaver2.features.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
<<<<<<< HEAD
import android.database.Cursor;
=======
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
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
<<<<<<< HEAD
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.features.Home.CartActivity;
=======
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308

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
<<<<<<< HEAD
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView editIcon = findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
=======
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView editIcon = findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
            // Lấy thông tin người dùng từ database
//            String currentEmail = "example@gmail.com"; // Giả sử bạn đang lưu email đăng nhập hiện tại
//            DataBaseHelper dbHelper = new DataBaseHelper(this);
//            Cursor cursor = dbHelper.getUserData(currentEmail);

//            if (cursor != null && cursor.moveToFirst()) {
////                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_USERNAME));
////                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_EMAIL));
//
//                // Chuyển sang EditProfileActivity và truyền dữ liệu
//                // Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
////                intent.putExtra("username", username);
////                intent.putExtra("email", email);
//                // startActivity(intent);
//
//                cursor.close(); // Đóng cursor sau khi sử dụng
//            } else {
//                Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
//            }
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
        });

        // Lặp qua các nút "Buy Again" để gắn sự kiện
        Button buyAgainButton1 = findViewById(R.id.buy_again_button1);
        Button buyAgainButton2 = findViewById(R.id.buy_again_button2);
        Button buyAgainButton3 = findViewById(R.id.buy_again_button3);
        Button buyAgainButton4 = findViewById(R.id.buy_again_button4);

        View.OnClickListener buyAgainListener = v -> {
<<<<<<< HEAD
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
=======
            Toast.makeText(this, "Buy Again Clicked!", Toast.LENGTH_SHORT).show();
            // Thêm logic đặt hàng lại ở đây
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
        };

        buyAgainButton1.setOnClickListener(buyAgainListener);
        buyAgainButton2.setOnClickListener(buyAgainListener);
        buyAgainButton3.setOnClickListener(buyAgainListener);
<<<<<<< HEAD
        buyAgainButton4.setOnClickListener(buyAgainListener);
    }
}
=======
    }
}
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
