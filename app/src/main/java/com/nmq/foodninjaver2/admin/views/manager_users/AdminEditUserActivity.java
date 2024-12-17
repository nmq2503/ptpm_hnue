package com.nmq.foodninjaver2.admin.views.manager_users;

import android.Manifest;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.repository.AdminRepository;
import com.nmq.foodninjaver2.repositories.MainRepository;
import com.nmq.foodninjaver2.utils.ValidateFunction;

import java.io.File;
import java.io.FileOutputStream;

public class AdminEditUserActivity extends AppCompatActivity {

    private AdminRepository adminRepository = new AdminRepository(this);
    private MainRepository mainRepository = new MainRepository(this);
    private static final int PICK_IMAGE_REQUEST = 1;
    private int idSelectedUser;
    private String oldEmail;

    Button btnSave;
    Button btnCancel;
    ImageView ivAddPicture;
    ImageView ivRemovePicture;
    EditText edtNameUser;
    EditText edtEmailUser;
    EditText edtPasswordUser;
    RadioGroup radioGroupRole;
    RadioButton radioUser;
    RadioButton radioManager;

    int selectedRole = 0;
    String urlImageProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_edit_user);

        edtNameUser = findViewById(R.id.edtNameUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        ivAddPicture = findViewById(R.id.ivAddPicture);
        ivRemovePicture = findViewById(R.id.ivRemovePicture);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        radioGroupRole = findViewById(R.id.radioGroupRole);
        radioUser = findViewById(R.id.radioUser);
        radioManager = findViewById(R.id.radioManager);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        idSelectedUser = intent.getIntExtra("user_id", -1);
        String userName = intent.getStringExtra("user_name");
        oldEmail = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String firstName = intent.getStringExtra("first_name");
        String lastName = intent.getStringExtra("last_name");
        String phoneNumber = intent.getStringExtra("phone_number");
        String address = intent.getStringExtra("address");
        String dateOfBirth = intent.getStringExtra("date_of_birth");
        urlImageProfile = intent.getStringExtra("url_image_profile");
        selectedRole = intent.getIntExtra("role_id", 0);

        // Thiết lập dữ liệu vào các thành phần giao diện
        edtNameUser.setText(userName);
        edtEmailUser.setText(oldEmail);
        edtPasswordUser.setText(password);

        // Gán giá trị vào RadioButton dựa trên roleId
        if (selectedRole != 0) {
            if (selectedRole == 2) {
                radioManager.setChecked(true);
            } else {
                radioUser.setChecked(true);
            }
        }

        // Hiển thị ảnh hồ sơ nếu có
        if (urlImageProfile != null && !urlImageProfile.isEmpty()) {
            // Hiển thị dấu "x" để xóa ảnh
            ivRemovePicture.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(new File(urlImageProfile))
                    .placeholder(R.drawable.icon_undefine_user)
                    .error(R.drawable.icon_undefine_user)
                    .into(ivAddPicture);
        }

        // Đặt lắng nghe sự kiện thay đổi trạng thái của RadioGroup
        radioGroupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kiểm tra ID của RadioButton được chọn
                if (checkedId == R.id.radioManager) {
                    selectedRole = 2; // Nếu chọn "Chủ cửa hàng", roleId = 2
                } else {
                    selectedRole = 3; // Ngược lại, roleId = 1
                }
            }
        });

        ivAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }

    private void saveUserToDatabase() {
        // Lấy dữ liệu từ các EditText
        String name = edtNameUser.getText().toString();
        String email = edtEmailUser.getText().toString();
        String password = edtPasswordUser.getText().toString();
        int roleId = selectedRole;

        // Kiểm tra email có tồn tại không
        if (!email.equals(oldEmail) && adminRepository.isEmailExist(email, idSelectedUser)) {
            Toast.makeText(this, "Email đã tồn tại! Vui lòng sử dụng email khác.", Toast.LENGTH_SHORT).show();
            return;
        }

        String internalImagePath = null;
        // Lưu ảnh vào bộ nhớ trong
        if (urlImageProfile != null) {
            internalImagePath = saveImageToInternalStorage(urlImageProfile);
        }

        boolean isSuccess = adminRepository.updateUserInDatabase(idSelectedUser , name, email, password, internalImagePath, roleId);

        if (isSuccess) {
            Toast.makeText(this, "Thông tin người dùng đã được lưu!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi lưu người dùng!", Toast.LENGTH_SHORT).show();
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

        if (selectedRole == 0) {
            Toast.makeText(this, "Vui lòng chọn vai trò!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Nếu tất cả kiểm tra đều hợp lệ
        return true;
    }

    public void removePicture(View view) {
        // Xóa ảnh và ẩn dấu "x"
        String imagePath = urlImageProfile;

        // Kiểm tra xem đường dẫn ảnh có hợp lệ và không phải null
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);

            // Kiểm tra xem tệp ảnh có tồn tại không
            if (imageFile.exists()) {
                boolean isDeleted = imageFile.delete();

                // Xóa ảnh nếu tồn tại
                if (isDeleted) {
                    Log.d("DeleteUser", "Ảnh đã được xóa thành công.");
                } else {
                    Log.d("DeleteUser", "Không thể xóa ảnh.");
                }
            } else {
                // Nếu ảnh không tồn tại, bỏ qua
                Log.d("DeleteUser", "Ảnh không tồn tại: " + imagePath);
            }
        }

        ivAddPicture.setImageResource(R.drawable.img_manager_add_picture); // Đặt lại ảnh mặc định
        ivRemovePicture.setVisibility(View.GONE); // Ẩn dấu "x"
        urlImageProfile = null;
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
                directory.mkdirs();
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

    // Định nghĩa ActivityResultCallback cho việc chọn ảnh
    private final ActivityResultCallback<ActivityResult> resultCallback = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                // Xử lý dữ liệu khi người dùng chọn ảnh
                Intent data = result.getData();
                if (data != null) {
                    // Lấy URI của ảnh
                    urlImageProfile = mainRepository.getRealPathFromURI(data.getData());
                    // Hiển thị ảnh trong ImageView ivAddPicture
                    ivAddPicture.setImageURI(data.getData());

                    // Hiển thị dấu "x" để xóa ảnh
                    ivRemovePicture.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(AdminEditUserActivity.this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Đăng ký activityResultLauncher
    private final androidx.activity.result.ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback);
}