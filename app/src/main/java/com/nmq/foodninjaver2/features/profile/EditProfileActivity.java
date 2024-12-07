package com.nmq.foodninjaver2.features.profile;

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
<<<<<<< HEAD
    private EditText edtNameUser, edtEmailUser, edtPasswordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog);  // sử dụng đúng layout

        // Kết nối với các EditText và Button
        edtNameUser = findViewById(R.id.edtNameUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);

        // Nhận dữ liệu từ Intent
=======
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog);

        // Nhận dữ liệu từ Intent
        EditText editUsername = findViewById(R.id.edit_username);
        EditText editEmail = findViewById(R.id.edit_email);

>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        // Hiển thị dữ liệu vào EditText
<<<<<<< HEAD
        if (username != null && email != null) {
            edtNameUser.setText(username);
            edtEmailUser.setText(email);
        }

        // Nút lưu
        Button saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(v -> {
            String newUsername = edtNameUser.getText().toString();
            String newEmail = edtEmailUser.getText().toString();
            String newPassword = edtPasswordUser.getText().toString();

            // Kiểm tra trường dữ liệu
            if (!newUsername.isEmpty() && !newEmail.isEmpty() && !newPassword.isEmpty()) {
                if (isValidEmail(newEmail)) {
                    updateUserInfo(email, newUsername, newEmail, newPassword);
                } else {
                    Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
=======
        editUsername.setText(username);
        editEmail.setText(email);

        // Nút lưu
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            String newUsername = editUsername.getText().toString();
            String newEmail = editEmail.getText().toString();

            if (!newUsername.isEmpty() && !newEmail.isEmpty()) {
                // updateUserInfo(email, newUsername, newEmail);
>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
            } else {
                Toast.makeText(this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút hủy
<<<<<<< HEAD
        Button cancelButton = findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(v -> finish());
    }

    // Hàm cập nhật thông tin người dùng
    private void updateUserInfo(String oldEmail, String newUsername, String newEmail, String newPassword) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        ContentValues values = new ContentValues();
        values.put("user_name", newUsername);
        values.put("email", newEmail);
        values.put("password", newPassword);

        int rowsUpdated = dbHelper.getWritableDatabase().update(
                "USER",  // Tên bảng đúng là "USER"
                values,
                "email = ?",  // Cập nhật thông qua email cũ
                new String[]{oldEmail}
        );

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }


    // Hàm kiểm tra email hợp lệ
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
=======
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> finish());
    }

//     // Hàm cập nhật thông tin
//    private void updateUserInfo(String oldEmail, String newUsername, String newEmail) {
//        DataBaseHelper dbHelper = new DataBaseHelper(this);
//        ContentValues values = new ContentValues();
//        values.put(DataBaseHelper.COLUMN_USERNAME, newUsername);
//        values.put(DataBaseHelper.COLUMN_EMAIL, newEmail);
//
//        int rowsUpdated = dbHelper.getWritableDatabase().update(
//                DataBaseHelper.TABLE_USERS,
//                values,
//                DataBaseHelper.COLUMN_EMAIL + " = ?",
//                new String[]{oldEmail}
//        );
//
//        if (rowsUpdated > 0) {
//            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
//            finish();
//        } else {
//            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private

    boolean isValidEmail(String email) {
        // Kiểm tra định dạng email (có thể sử dụng regex cho email hợp lệ)
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

>>>>>>> c9b9677f806da0d97276aa06acaedcb253efd308
    }
}
