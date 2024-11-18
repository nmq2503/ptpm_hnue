package com.nmq.foodninjaver2.features.auth;

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

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Setup Edge-to-Edge insets for the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý sự kiện cho biểu tượng Edit
        ImageView editIcon = findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Edit Profile Clicked!", Toast.LENGTH_SHORT).show();
            // Thêm logic mở Activity khác hoặc Fragment tại đây
        });

        // Lặp qua các nút "Buy Again" để gắn sự kiện
        Button buyAgainButton1 = findViewById(R.id.buy_again_button1);
        Button buyAgainButton2 = findViewById(R.id.buy_again_button2);
        Button buyAgainButton3 = findViewById(R.id.buy_again_button3);
        Button buyAgainButton4 = findViewById(R.id.buy_again_button4);

        View.OnClickListener buyAgainListener = v -> {
            Toast.makeText(ProfileActivity.this, "Buy Again Clicked!", Toast.LENGTH_SHORT).show();
            // Thêm logic đặt hàng lại ở đây
        };

        buyAgainButton1.setOnClickListener(buyAgainListener);
        buyAgainButton2.setOnClickListener(buyAgainListener);
        buyAgainButton3.setOnClickListener(buyAgainListener);
    }
}
