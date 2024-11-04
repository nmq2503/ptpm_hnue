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

public class SecondSplashActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_splash);

        button = findViewById(R.id.btnNext);

        button.setOnClickListener(view -> {
            startActivity(new Intent(SecondSplashActivity.this, ThirdSplashActivity.class));
        });
    }
}