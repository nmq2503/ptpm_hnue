package com.nmq.foodninjaver2.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nmq.foodninjaver2.MainActivity;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.auth.LoginActivity;

public class ThirdSplashActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third_splash);

        button = findViewById(R.id.btnNext);

        button.setOnClickListener(view -> {
            startActivity(new Intent(ThirdSplashActivity.this, LoginActivity.class));
            finish();
        });
    }
}