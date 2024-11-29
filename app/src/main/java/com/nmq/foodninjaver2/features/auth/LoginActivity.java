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
import com.nmq.foodninjaver2.MainActivity;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.features.Home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtEmail, edtPassword;
    AppCompatButton btnLogin;
    DataBaseHelper dataBaseHelper;
    TextView tvRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegistration = findViewById(R.id.tvRegistration);

        dataBaseHelper = new DataBaseHelper(this);

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
        boolean isValid = dataBaseHelper.checkLogin(email, password);

        // Kiểm tra điều kiện lần đầu vào app
        SharedPreferences preferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean(MainActivity.KEY_FIRST_LAUNCH, true);

        if (isValid) {
            Toast.makeText(this, "Login successful! Welcome " + email, Toast.LENGTH_SHORT).show();

            if (isFirstLaunch) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                preferences.edit().putBoolean(MainActivity.KEY_FIRST_LAUNCH, false).apply();
            } else {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}