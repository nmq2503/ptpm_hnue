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
import com.nmq.foodninjaver2.utils.ValidateFunction;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edtUsername, edtEmail, edtPassword;
    AppCompatButton btnRegistration;
    AuthRepository authRepository;
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

        authRepository = new AuthRepository(this);

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
            Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
        } else {
            if (!authRepository.checkEmail(email)) {
                if (ValidateFunction.validatePassword(password)) {
                    Intent intent = new Intent(RegisterActivity.this, RegisterProcessActivity.class);
                    intent.putExtra("user_name", username);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm 1 số và 1 ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Email đã tồn tại! Vui lòng nhập email khác!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}