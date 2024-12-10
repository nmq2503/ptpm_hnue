package com.nmq.foodninjaver2.features.auth;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
            // Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
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
            sessionManager.createLoginSession(email);

            Toast.makeText(this, "Login successful! Welcome " + email, Toast.LENGTH_SHORT).show();

            // Kiểm tra nếu là tài khoản admin
            if (email.equals("quanggg2503@gmail.com") && password.equals("12345678")) {
                // Sử dụng Intent để chuyển đến AdminActivity và xóa hết các Activity trước đó
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Xóa tất cả Activity trước đó
                startActivity(intent);
            } else {
                // Chuyển đến MainActivity nếu không phải admin
                if (sessionManager.isLoggedIn()) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}