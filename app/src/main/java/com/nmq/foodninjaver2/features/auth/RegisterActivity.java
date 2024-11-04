package com.nmq.foodninjaver2.features.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edtUsername, edtEmail, edtPassword;
    AppCompatButton btnRegistration;
    DataBaseHelper dataBaseHelper;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.etdUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegistration = findViewById(R.id.btnRegistration);
        tvLogin = findViewById(R.id.tvLogin);

        dataBaseHelper = new DataBaseHelper(this);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (!dataBaseHelper.checkEmail(email)) {
                if (validPassword(password)) {
                    // Add user to database
                    if (dataBaseHelper.insertUser(username, email, password)) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        // Chuyển sang màn hình đăng nhập
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                } else {
                    Toast.makeText(this, "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validPassword(String password) {
        return password.length() >= 8;
    }
}