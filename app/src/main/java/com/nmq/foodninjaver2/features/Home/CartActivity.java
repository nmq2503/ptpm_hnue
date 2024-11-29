package com.nmq.foodninjaver2.features.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nmq.foodninjaver2.R;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

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
    }
}