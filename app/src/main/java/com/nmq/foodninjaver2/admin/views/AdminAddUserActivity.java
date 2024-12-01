package com.nmq.foodninjaver2.admin.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.repositories.MainRepository;
import com.nmq.foodninjaver2.utils.ValidateFunction;

import java.io.File;
import java.io.FileOutputStream;

public class AdminAddUserActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AdminRepository adminRepository = new AdminRepository(this);
    private MainRepository mainRepository = new MainRepository(this);
    private ValidateFunction validateFunction = new ValidateFunction();

    Button btnSave;
    Button btnCancel;
    ImageView ivAddPicture;
    ImageView ivRemovePicture;
    EditText edtNameUser;
    EditText edtEmailUser;
    EditText edtPasswordUser;

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
                    // Hiển thị ảnh trong ImageView ivAddPicture
                    ivAddPicture.setImageURI(data.getData());

                    // Hiển thị dấu "x" để xóa ảnh
                    ivRemovePicture.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(AdminAddUserActivity.this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_admin_add_user);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        ivAddPicture = findViewById(R.id.ivAddPicture);
        ivRemovePicture = findViewById(R.id.ivRemovePicture);
        edtNameUser = findViewById(R.id.edtNameUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);

        ivAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra và lưu dữ liệu
                if (validateInput()) {
                    saveUserToDatabase();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveUserToDatabase() {
        // Lấy dữ liệu từ các EditText
        String name = edtNameUser.getText().toString();
        String email = edtEmailUser.getText().toString();
        String password = edtPasswordUser.getText().toString();

        String internalImagePath = null;
        // Lưu ảnh vào bộ nhớ trong
        if (selectedImagePath != null) {
           internalImagePath = saveImageToInternalStorage(selectedImagePath);
        }

        boolean isSuccess = adminRepository.saveUserToDatabase(name, email, password, internalImagePath);

        if (isSuccess) {
            Toast.makeText(this, "Người dùng đã được lưu!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi lưu người dùng!", Toast.LENGTH_SHORT).show();
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

    private boolean validateInput() {
        String name = edtNameUser.getText().toString().trim();
        String email = edtEmailUser.getText().toString().trim();
        String password = edtPasswordUser.getText().toString().trim();

        // Kiểm tra tên người dùng
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên người dùng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra email
        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ValidateFunction.validateEmail(email)) {
            Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra mật khẩu
        if (password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ValidateFunction.validatePassword(password)) {
            Toast.makeText(this, "Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm 1 số và 1 ký tự đặc biệt!", Toast.LENGTH_LONG).show();
            return false;
        }

        // Nếu tất cả kiểm tra đều hợp lệ
        return true;
    }

    public void removePicture(View view) {
        // Xóa ảnh và ẩn dấu "x"
        ivAddPicture.setImageResource(R.drawable.img_manager_add_picture); // Đặt lại ảnh mặc định
        ivRemovePicture.setVisibility(View.GONE); // Ẩn dấu "x"
        selectedImagePath = null;
    }

    // Kiểm tra và yêu cầu quyền
    private void checkAndRequestPermissions() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, mở thư viện
                openGallery();
            } else {
                // Quyền bị từ chối, hiển thị thông báo
                Toast.makeText(this, "Ứng dụng cần quyền truy cập thư viện để chọn ảnh!", Toast.LENGTH_SHORT).show();
            }
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