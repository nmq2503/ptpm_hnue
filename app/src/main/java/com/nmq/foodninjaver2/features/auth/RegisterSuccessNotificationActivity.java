package com.nmq.foodninjaver2.features.auth;

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

public class RegisterSuccessNotificationActivity extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_success_notification);

        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            nextScreen();
        });
    }

    private void nextScreen() {
        Intent intent = new Intent(RegisterSuccessNotificationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}