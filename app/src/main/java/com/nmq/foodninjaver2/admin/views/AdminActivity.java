package com.nmq.foodninjaver2.admin.views;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.repository.AdminRepository;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminDetailManagerUsersActivity;
import com.nmq.foodninjaver2.features.auth.LoginActivity;

public class AdminActivity extends AppCompatActivity {

    TextView tvSubtitleUser;
    TextView tvSubtitleRestaurant;
    CardView cvManagerUsers;
    CardView cvManagerRestaurants;

    ImageView ivLogoAdmin;

    private AdminRepository adminRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        tvSubtitleUser = findViewById(R.id.tvSubtitleUser);
        tvSubtitleRestaurant = findViewById(R.id.tvSubtitleRestaurant);
        cvManagerUsers = findViewById(R.id.cvManagerUsers);
        cvManagerRestaurants = findViewById(R.id.cvManagerRestaurants);

        ivLogoAdmin = findViewById(R.id.ivLogoAdmin);

        adminRepository = new AdminRepository(this);

        int userCount = adminRepository.getUserCount();
        int restaurantCount = adminRepository.getRestaurantCount();

        tvSubtitleUser.setText("Số lượng: " + userCount);
        tvSubtitleRestaurant.setText("Số lượng: " + restaurantCount);

        cvManagerUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdminDetailManagerUsersActivity.class);
                startActivity(intent);
            }
        });

        ivLogoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

//    private void showLogoutConfirmationDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Xác nhận đăng xuất");
//        builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
//        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Điều hướng về màn hình LoginActivity
//                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish(); // Kết thúc Activity hiện tại
//            }
//        });
//        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Đóng dialog
//                dialog.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void showLogoutConfirmationDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        tvTitle.setText("Bạn có chắc chắn muốn đăng xuất không?");
        btnDelete.setText("Đồng ý");
        btnCancel.setText("Hủy");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }
}