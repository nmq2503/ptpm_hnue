package com.nmq.foodninjaver2.features.auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;

import java.io.File;
import java.io.FileOutputStream;

public class UploadPreviewActivity extends AppCompatActivity {

    private AuthRepository authRepository;

    ImageView ivBackBtn;
    ImageView ivProfile;
    Button btnContinue;

    private String urlImageProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_preview);

        initViews();

        authRepository = new AuthRepository(this);

        ivBackBtn.setOnClickListener(v -> {
            finish();
        });

        btnContinue.setOnClickListener(v -> {
            nextScreen();
        });
    }

    private void initViews() {
        Intent intent = getIntent();
        urlImageProfile = intent.getStringExtra("url_image_profile");

        ivBackBtn = findViewById(R.id.ivBackBtn);
        ivProfile = findViewById(R.id.ivProfile);
        btnContinue = findViewById(R.id.btnContinue);

        if (urlImageProfile != null && !urlImageProfile.isEmpty()) {
            Glide.with(this)
                    .load(new File(urlImageProfile))
                    .placeholder(R.drawable.icon_undefine_user)
                    .error(R.drawable.icon_undefine_user)
                    .into(ivProfile);
        }
    }

    private void nextScreen() {
        String firstName = getIntent().getStringExtra("first_name");
        String lastName = getIntent().getStringExtra("last_name");
        String phoneNumber = getIntent().getStringExtra("phone_number");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String userName = getIntent().getStringExtra("user_name");
        String internalImagePath = null;

        if (urlImageProfile != null) {
            internalImagePath = saveImageToInternalStorage(urlImageProfile);
        }

        boolean isSuccessful = authRepository.insertUser(userName, firstName, lastName, email, password, phoneNumber, internalImagePath);

        if (isSuccessful) {
            Intent intent = new Intent(UploadPreviewActivity.this, RegisterSuccessNotificationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImageToInternalStorage(String originalImagePath) {
        try {
            // Xử lý hướng ảnh
            Bitmap bitmap = handleImageOrientation(originalImagePath);
            if (bitmap == null) {
                // Toast.makeText(this, "Không thể xử lý ảnh!", Toast.LENGTH_SHORT).show();
                return null;
            }

            // Tạo thư mục lưu ảnh
            File directory = new File(getFilesDir(), "user_images"); // Thư mục "user_images"
            if (!directory.exists()) {
                directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            // Tạo tên file duy nhất
            String fileName = "user_" + System.currentTimeMillis() + ".jpg";

            // Lưu ảnh vào thư mục
            File file = new File(directory, fileName);
            FileOutputStream fos = new FileOutputStream(file);

            // Nén ảnh và lưu
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Trả về đường dẫn đầy đủ của file ảnh
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }

    private Bitmap handleImageOrientation(String imagePath) {
        try {
            // Đọc thông tin EXIF
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Matrix matrix = new Matrix();

            // Xoay ảnh theo thông tin EXIF
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                default:
                    return bitmap; // Không cần xoay
            }

            // Tạo bitmap mới với hướng xoay đúng
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}