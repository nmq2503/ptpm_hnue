package com.nmq.foodninjaver2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nmq.foodninjaver2.Adapter.RestaurantAdapter;
import com.nmq.foodninjaver2.Model.Restaurant;
import com.nmq.foodninjaver2.RestaurantDetailActivity;
import com.nmq.foodninjaver2.dataBase.RestaurantDatabaseHelper;
import com.nmq.foodninjaver2.features.splash.SecondSplashActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnNavigate;
    ListView listViewRestaurants;
    private RestaurantDatabaseHelper dbHelper;

    public static final String PREFS_NAME = "app_prefs";
    public static final String KEY_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        dbHelper = new RestaurantDatabaseHelper(this);
        dbHelper.insertRestaurant("Nhà hàng A", "Mô tả nhà hàng A", 4.5f);
        dbHelper.insertRestaurant("Nhà hàng B", "Mô tả nhà hàng B", 3.5f);
        dbHelper.insertRestaurant("Nhà hàng C", "Mô tả nhà hàng C", 5.0f);
        List<Restaurant> restaurants = dbHelper.getAllRestaurants();

        // Tạo adapter cho ListView
        RestaurantAdapter adapter = new RestaurantAdapter(this, restaurants);

        // Liên kết ListView với adapter
        if (restaurants.isEmpty()) {
            Log.e("MainActivity", "Không có nhà hàng trong cơ sở dữ liệu.");
            Toast.makeText(this, "Không có nhà hàng trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            // Tạo adapter cho ListView

            // Liên kết ListView với adapter
            listViewRestaurants = findViewById(R.id.listViewRestaurants);
            listViewRestaurants.setAdapter(adapter);
        }
        listViewRestaurants.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant selectedRestaurant = restaurants.get(i);

                // Chuyển sang RestaurantDetailActivity và truyền dữ liệu nhà hàng
                Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("restaurant_id", selectedRestaurant.getId());  // Truyền ID của nhà hàng
                startActivity(intent);
                return false;
            }
        });








        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}