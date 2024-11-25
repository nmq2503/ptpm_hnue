package com.nmq.foodninjaver2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nmq.foodninjaver2.dataBase.RestaurantDatabaseHelper;
import com.nmq.foodninjaver2.features.PopularMenuActivity;
import com.nmq.foodninjaver2.features.RestaurantDetailActivity;

public class MainActivity extends AppCompatActivity {
    Button btnTest;
    ListView listViewRestaurants;
    private RestaurantDatabaseHelper dbHelper;

    public static final String PREFS_NAME = "app_prefs";
    public static final String KEY_FIRST_LAUNCH = "first_launch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnTest = findViewById(R.id.btnTest);
        if (btnTest != null) {
            btnTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Hiển thị Toast khi nhấn nút
                    Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            // In log nếu btnTest không được tìm thấy
            Log.e("MainActivity", "Button not found in layout!");
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}