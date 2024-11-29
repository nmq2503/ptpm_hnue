package com.nmq.foodninjaver2.features.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nmq.foodninjaver2.MainActivity;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.core.SessionManager;

public class FirstSplashActivity extends AppCompatActivity {

    SessionManager sessionManager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);

        // Kiểm tra trạng thái đăng nhập
        if (sessionManager.isLoggedIn()) {
            // Nếu đã đăng nhập, chuyển đến MainActivity và kết thúc SplashActivity
            startActivity(new Intent(this, MainActivity.class));
            finish(); // Kết thúc FirstSplashActivity
            return; // Ngăn chặn xử lý tiếp nội dung splash
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_splash);

        handle();
    }

    private void handle() {
        // Khởi tạo ProgressBar (cần gọi findViewById)
        progressBar = findViewById(R.id.progressBar);

        final int[] count = {0}; // Sử dụng mảng để có thể thay đổi giá trị bên trong CountDownTimer
        int miles = 4000;
        int countInterval = 1000;
        progressBar.setProgress(0);

        CountDownTimer countdownTimer = new CountDownTimer(miles, countInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                count[0]++;
                progressBar.setProgress((count[0] * 100) / (miles / countInterval));
            }

            @Override
            public void onFinish() {
                count[0]++;
                progressBar.setProgress(100);

                // Điều hướng dựa vào trạng thái lần đầu mở ứng dụng
                startActivity(new Intent(FirstSplashActivity.this, SecondSplashActivity.class));
                finish(); // Kết thúc `FirstSplashActivity` để người dùng không quay lại
            }
        };
        countdownTimer.start();
    }

}