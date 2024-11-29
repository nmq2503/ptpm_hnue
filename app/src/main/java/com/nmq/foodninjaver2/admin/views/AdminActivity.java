package com.nmq.foodninjaver2.admin.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

public class AdminActivity extends AppCompatActivity {

    TextView tvSubtitleUser;
    TextView tvSubtitleRestaurant;

    private AdminRepository adminRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        tvSubtitleUser = findViewById(R.id.tvSubtitleUser);
        tvSubtitleRestaurant = findViewById(R.id.tvSubtitleRestaurant);

        adminRepository = new AdminRepository(this);

        int userCount = adminRepository.getUserCount();
        int restaurantCount = adminRepository.getRestaurantCount();

        tvSubtitleUser.setText("Số lượng: " + userCount);
        tvSubtitleRestaurant.setText("Số lượng: " + restaurantCount);
    }
}