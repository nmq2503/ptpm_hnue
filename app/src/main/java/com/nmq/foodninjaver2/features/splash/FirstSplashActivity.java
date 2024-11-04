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

public class FirstSplashActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Lấy `SharedPreferences` để kiểm tra trạng thái lần đầu mở ứng dụng
        SharedPreferences preferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean(MainActivity.KEY_FIRST_LAUNCH, true);

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
                if (isFirstLaunch) {
                    // Nếu là lần đầu, chuyển sang `SecondSplashActivity`
                    startActivity(new Intent(FirstSplashActivity.this, SecondSplashActivity.class));

                    // Cập nhật trạng thái không còn là lần đầu mở ứng dụng
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(MainActivity.KEY_FIRST_LAUNCH, false);
                    editor.apply();
                } else {
                    // Nếu không phải lần đầu, chuyển đến `LoginActivity`
                    startActivity(new Intent(FirstSplashActivity.this, SecondSplashActivity.class));
                }

                finish(); // Kết thúc `FirstSplashActivity` để người dùng không quay lại
            }
        };
        countdownTimer.start();
    }

}