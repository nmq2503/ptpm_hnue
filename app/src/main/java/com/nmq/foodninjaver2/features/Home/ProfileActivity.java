package com.nmq.foodninjaver2.features.Home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nmq.foodninjaver2.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_profile) {
                Toast.makeText(ProfileActivity.this, "Profile", Toast.LENGTH_SHORT).show();
//                return true;
            } else if (item.getItemId() == R.id.action_home){
                Intent intentHome = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intentHome);
                finish();
            }
            else if (item.getItemId() == R.id.action_cart) {
                Intent intentCart= new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intentCart);
                finish();
            } return true;

        });
    }
}