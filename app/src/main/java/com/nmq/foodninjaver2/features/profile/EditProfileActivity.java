package com.nmq.foodninjaver2.features.profile;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    private EditText edtNameUser, edtEmailUser, edtPasswordUser;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivAddPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_dialog);  // sử dụng đúng layout

        // Kết nối với các EditText và Button
        edtNameUser = findViewById(R.id.edtNameUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        ivAddPicture = findViewById(R.id.ivAddPicture);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");


        // Hiển thị dữ liệu vào EditText
        if (username != null && email != null) {
            edtNameUser.setText(username);
            edtEmailUser.setText(email);
        }

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
            } else {
                Toast.makeText(this, "Vui lòng không để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút hủy
        Button cancelButton = findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(v -> finish());
    }
    // Hàm xử lý sự kiện onClick được gọi từ XML
    public void onChangePicture(View view) {
        openImagePicker(); // Mở trình chọn ảnh
    }

    // Hàm mở trình chọn ảnh
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Sử dụng request code
    }

    // Hàm xử lý kết quả khi người dùng chọn ảnh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ivAddPicture.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Lỗi khi tải ảnh!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không tìm thấy ảnh!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hàm cập nhật thông tin người dùng
    private void updateUserInfo(String oldEmail, String newUsername, String newEmail, String newPassword) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        ContentValues values = new ContentValues();
        values.put("user_name", newUsername);
        values.put("email", newEmail);
        values.put("password", newPassword);

        int rowsUpdated = dbHelper.getWritableDatabase().update(
                "USER",  // T
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
    }
}
