package com.nmq.foodninjaver2.admin.views.manager_restaurants;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.exifinterface.media.ExifInterface;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.repository.AdminRepository;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminAddUserActivity;
import com.nmq.foodninjaver2.core.modelBase.UserModel;
import com.nmq.foodninjaver2.repositories.MainRepository;
import com.nmq.foodninjaver2.utils.ValidateFunction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminAddRestaurantActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AdminRepository adminRepository = new AdminRepository(this);
    private MainRepository mainRepository = new MainRepository(this);

    List<UserModel> owners;
    List<String> emailsOwners;

    Button btnSave;
    Button btnCancel;
    ImageView ivAddPicture;
    ImageView ivRemovePicture;

    EditText edtNameRestaurant,
            edtAddressRestaurant,
            edtEmailRestaurant,
            edtPhoneNumberRestaurant,
            edtOpeningHourRestaurant,
            edtClosingHourRestaurant;

    AutoCompleteTextView autoCompleteOwnerRestaurant;
    ArrayAdapter<String> adapterAutoCompleteOwnerRestaurant;

    String selectedImagePath = null;
    int selectedOwnerId = -1;

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
                Toast.makeText(AdminAddRestaurantActivity.this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_admin_add_restaurant);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        ivAddPicture = findViewById(R.id.ivAddPicture);
        ivRemovePicture = findViewById(R.id.ivRemovePicture);
        edtNameRestaurant = findViewById(R.id.edtNameRestaurant);
        edtAddressRestaurant = findViewById(R.id.edtAddressRestaurant);
        edtEmailRestaurant = findViewById(R.id.edtEmailRestaurant);
        edtPhoneNumberRestaurant = findViewById(R.id.edtPhoneNumberRestaurant);
        edtOpeningHourRestaurant = findViewById(R.id.edtOpeningHourRestaurant);
        edtClosingHourRestaurant = findViewById(R.id.edtClosingHourRestaurant);

        autoCompleteOwnerRestaurant = findViewById(R.id.autoCompleteOwnerRestaurant);

        // Lấy danh sách các chủ cửa hàng
        owners = adminRepository.getAllRestaurantOwners();

        // Tạo danh sách email từ danh sách owner
        emailsOwners = new ArrayList<>();
        for (UserModel owner : owners) {
            emailsOwners.add(owner.getEmail()); // Lấy email từ danh sách owner
        }

        // Tạo ArrayAdapter để gắn dữ liệu vào AutoCompleteTextView
        adapterAutoCompleteOwnerRestaurant = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                emailsOwners
        );

        // Gắn adapter vào AutoCompleteTextView
        autoCompleteOwnerRestaurant.setAdapter(adapterAutoCompleteOwnerRestaurant);

        // Lắng nghe sự kiện khi người dùng chọn một email
        autoCompleteOwnerRestaurant.setOnItemClickListener((parent, view, position, id) -> {
            String selectedEmail = (String) parent.getItemAtPosition(position);

            // Tìm chủ cửa hàng tương ứng với email đã chọn
            for (UserModel owner : owners) {
                if (owner.getEmail().equals(selectedEmail)) {
                    selectedOwnerId = owner.getUserId(); // Lưu id của chủ cửa hàng
                    break;
                }
            }

            // Thực hiện các hành động khác với ID của chủ cửa hàng, ví dụ hiển thị ID
            Log.d("SelectedOwnerId", "ID của chủ cửa hàng đã chọn: " + selectedOwnerId);
        });

        // Hiển thị danh sách khi người dùng nhấn vào
        autoCompleteOwnerRestaurant.setOnClickListener(view -> {
            autoCompleteOwnerRestaurant.showDropDown();
        });

        ivAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    saveRestaurantToDatabase();
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

    private void saveRestaurantToDatabase() {
        // Lấy dữ liệu từ các EditText
        String name = edtNameRestaurant.getText().toString().trim();
        String address = edtAddressRestaurant.getText().toString().trim();
        String email = edtEmailRestaurant.getText().toString().trim();
        String phoneNumber = edtPhoneNumberRestaurant.getText().toString().trim();
        String openingHour = edtOpeningHourRestaurant.getText().toString().trim();
        String closingHour = edtClosingHourRestaurant.getText().toString().trim();
        int ownerId = selectedOwnerId;

        if (ownerId == -1) {
            Toast.makeText(this, "Vui lòng gán chủ nhà hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        String internalImagePath = null;
        // Lưu ảnh vào bộ nhớ trong
        if (selectedImagePath != null) {
            internalImagePath = saveImageToInternalStorage(selectedImagePath);
        }

        boolean isSuccess = adminRepository.saveRestaurantToDatabase(name, address, email, phoneNumber, openingHour, closingHour, internalImagePath, ownerId);

        if (isSuccess) {
            Toast.makeText(this, "Nhà hàng đã được lưu!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi lưu nhà hàng!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput() {
        // Lấy dữ liệu từ các EditText
        String name = edtNameRestaurant.getText().toString().trim();
        String address = edtAddressRestaurant.getText().toString().trim();
        String email = edtEmailRestaurant.getText().toString().trim();
        String phoneNumber = edtPhoneNumberRestaurant.getText().toString().trim();
        String openingHour = edtOpeningHourRestaurant.getText().toString().trim();
        String closingHour = edtClosingHourRestaurant.getText().toString().trim();

        // Kiểm tra tên nhà hàng
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên nhà hàng!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra địa chỉ
        if (address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập địa chỉ nhà hàng!", Toast.LENGTH_SHORT).show();
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

        // Kiểm tra số điện thoại
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!phoneNumber.matches("\\d{10,11}")) { // Kiểm tra số điện thoại có 10-11 chữ số
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra giờ mở cửa
        if (openingHour.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giờ mở cửa!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ValidateFunction.validateTime(openingHour)) { // Giả sử validateTime kiểm tra định dạng giờ HH:mm
            Toast.makeText(this, "Giờ mở cửa không hợp lệ! (Định dạng: HH:mm AM)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra giờ đóng cửa
        if (closingHour.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giờ đóng cửa!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ValidateFunction.validateTime(closingHour)) {
            Toast.makeText(this, "Giờ đóng cửa không hợp lệ! (Định dạng: HH:mm PM)", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra giờ mở cửa < giờ đóng cửa
        if (!ValidateFunction.isTimeOrderValid(openingHour, closingHour)) {
            Toast.makeText(this, "Giờ mở cửa phải nhỏ hơn giờ đóng cửa!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Nếu tất cả kiểm tra đều hợp lệ
        return true;
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
            File directory = new File(getFilesDir(), "restaurant_images"); // Thư mục "user_images"
            if (!directory.exists()) {
                directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            // Tạo tên file duy nhất
            String fileName = "restaurant_" + System.currentTimeMillis() + ".jpg";

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