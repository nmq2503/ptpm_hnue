package com.nmq.foodninjaver2.features.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

public class EditProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog);

        // Nhận dữ liệu từ Intent
        EditText editUsername = findViewById(R.id.edit_username);
        EditText editEmail = findViewById(R.id.edit_email);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        // Hiển thị dữ liệu vào EditText
        editUsername.setText(username);
        editEmail.setText(email);

        // Nút lưu
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            String newUsername = editUsername.getText().toString();
            String newEmail = editEmail.getText().toString();

            if (!newUsername.isEmpty() && !newEmail.isEmpty()) {
                updateUserInfo(email, newUsername, newEmail);
            } else {
                Toast.makeText(this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút hủy
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> finish());
    }

    // Hàm cập nhật thông tin
    private void updateUserInfo(String oldEmail, String newUsername, String newEmail) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_USERNAME, newUsername);
        values.put(DataBaseHelper.COLUMN_EMAIL, newEmail);

        int rowsUpdated = dbHelper.getWritableDatabase().update(
                DataBaseHelper.TABLE_USERS,
                values,
                DataBaseHelper.COLUMN_EMAIL + " = ?",
                new String[]{oldEmail}
        );

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private

    boolean isValidEmail(String email) {
        // Kiểm tra định dạng email (có thể sử dụng regex cho email hợp lệ)
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
}
