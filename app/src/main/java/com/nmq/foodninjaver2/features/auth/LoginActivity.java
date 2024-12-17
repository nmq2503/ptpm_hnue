package com.nmq.foodninjaver2.features.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.views.AdminActivity;
import com.nmq.foodninjaver2.core.SessionManager;
import com.nmq.foodninjaver2.features.Home.HomeActivity;
import com.nmq.foodninjaver2.features.cart.FoodCartActivity;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtEmail, edtPassword;
    AppCompatButton btnLogin;
    AuthRepository authRepository;
    TextView tvRegistration;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegistration = findViewById(R.id.tvRegistration);

        authRepository = new AuthRepository(this);

        btnLogin.setOnClickListener(v -> {
            handleLogin();
        });

        tvRegistration.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void handleLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (!isValidEmail(email)) {
            edtEmail.setError("Invalid email");
            return;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Password cannot be empty");
            return;
        }

        authenticateUser(email, password);
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void authenticateUser(String email, String password) {
        boolean isValid = authRepository.checkLogin(email, password);

        if (isValid) {
            int userId = authRepository.getUserIdByEmail(email);
            sessionManager.createLoginSession(email, userId);

            Toast.makeText(this, "Đăng nhập thành công! Chào mừng " + email, Toast.LENGTH_SHORT).show();

            if (email.equals("quanggg2503@gmail.com") && password.equals("12345678")) {
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Xóa tất cả Activity trước đó
                startActivity(intent);
            } else {
                if (sessionManager.isLoggedIn()) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không hợp lệ! Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
        }
    }
}