package com.nmq.foodninjaver2.features.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminAddUserActivity;
import com.nmq.foodninjaver2.repositories.MainRepository;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UploadPhotoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Mã request cho thư viện
    private static final int TAKE_PHOTO_REQUEST = 2; // Mã request cho camera
    private MainRepository mainRepository = new MainRepository(this);

    ImageView ivBackBtn;
    ImageView ivGallery, ivCamera;
    Button btnContinue;

    String selectedImagePath = null;

    // Định nghĩa ActivityResultCallback cho việc chọn ảnh
    private final ActivityResultCallback<ActivityResult> resultCallback = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                // Xử lý dữ liệu khi người dùng chọn ảnh
                Intent data = result.getData();
                if (data != null) {
                    // Lấy URI của ảnh
                    selectedImagePath = mainRepository.getRealPathFromURI(data.getData());
                    nextScreen();
                }
            } else {
                // Toast.makeText(UploadPhotoActivity.this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Đăng ký activityResultLauncher
    private final androidx.activity.result.ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_photo);

        initViews();

        ivBackBtn.setOnClickListener(v -> {
            finish();
        });

        ivGallery.setOnClickListener(v -> {
            checkAndRequestPermissionsOpenGallery();
        });

        ivCamera.setOnClickListener(v -> {
            // Toast.makeText(this, "Đang phát triển!", Toast.LENGTH_SHORT).show();
            checkAndRequestPermissions(Manifest.permission.CAMERA, this::takePicture);
        });

        btnContinue.setOnClickListener(v -> {
            nextScreen();
        });
    }

    private void checkAndRequestPermissions(String permission, Runnable onPermissionGranted) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted.run();
        } else {
            requestPermissions(new String[]{permission}, TAKE_PHOTO_REQUEST);
        }
    }

    private void takePicture() {
        // Kiểm tra xem thiết bị có camera không
        if (!isCameraAvailable()) {
            Toast.makeText(UploadPhotoActivity.this, "Thiết bị của bạn không có camera hoặc camera không khả dụng!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Đang phát triển!", Toast.LENGTH_SHORT).show();
    }

    // Kiểm tra xem thiết bị có camera hay không
    private boolean isCameraAvailable() {
        PackageManager packageManager = getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    // Kiểm tra và yêu cầu quyền
        private void checkAndRequestPermissionsOpenGallery() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Android 13+ sử dụng READ_MEDIA_IMAGES
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PICK_IMAGE_REQUEST);
                } else {
                    openGallery();
                }
            } else {
                // Android 12 trở xuống sử dụng READ_EXTERNAL_STORAGE
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
                } else {
                    openGallery();
                }
            }
        }

    // Mở thư viện chọn ảnh
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void initViews() {
        ivBackBtn = findViewById(R.id.ivBackBtn);
        ivGallery = findViewById(R.id.ivGallery);
        ivCamera = findViewById(R.id.ivCamera);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void nextScreen() {
        Intent intent = new Intent(UploadPhotoActivity.this, UploadPreviewActivity.class);
        intent.putExtra("user_name", getIntent().getStringExtra("user_name"));
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("password", getIntent().getStringExtra("password"));

        intent.putExtra("first_name", getIntent().getStringExtra("first_name"));
        intent.putExtra("last_name", getIntent().getStringExtra("last_name"));
        intent.putExtra("phone_number", getIntent().getStringExtra("phone_number"));
        intent.putExtra("url_image_profile", selectedImagePath);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TAKE_PHOTO_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}