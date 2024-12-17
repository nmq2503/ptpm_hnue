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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.exifinterface.media.ExifInterface;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.repository.AdminRepository;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminEditUserActivity;
import com.nmq.foodninjaver2.core.modelBase.UserModel;
import com.nmq.foodninjaver2.repositories.MainRepository;
import com.nmq.foodninjaver2.utils.ValidateFunction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminEditRestaurantActivity extends AppCompatActivity {

    private AdminRepository adminRepository = new AdminRepository(this);
    private MainRepository mainRepository = new MainRepository(this);
    private static final int PICK_IMAGE_REQUEST = 1;
    private int idSelectedRestaurant;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_edit_restaurant);

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

        Intent intent = getIntent();

        idSelectedRestaurant = intent.getIntExtra("restaurant_id", -1);
        String restaurantName = intent.getStringExtra("restaurant_name");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");
        String phoneNumber = intent.getStringExtra("phone_number");
        String openingHours = intent.getStringExtra("opening_hours");
        String closingHours = intent.getStringExtra("closing_hours");
        selectedImagePath = intent.getStringExtra("url_image_restaurant");
        selectedOwnerId = intent.getIntExtra("owner_id", -1);

        edtNameRestaurant.setText(restaurantName);
        edtAddressRestaurant.setText(address);
        edtEmailRestaurant.setText(email);
        edtPhoneNumberRestaurant.setText(phoneNumber);
        edtOpeningHourRestaurant.setText(openingHours);
        edtClosingHourRestaurant.setText(closingHours);

        owners = adminRepository.getAllRestaurantOwners();

        emailsOwners = new ArrayList<>();
        for (UserModel owner : owners) {
            emailsOwners.add(owner.getEmail());
        }

        adapterAutoCompleteOwnerRestaurant = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                emailsOwners
        );

        autoCompleteOwnerRestaurant.setAdapter(adapterAutoCompleteOwnerRestaurant);

        if (selectedOwnerId != -1) {
            for (UserModel owner : owners) {
                if (owner.getUserId() == selectedOwnerId) {
                    autoCompleteOwnerRestaurant.setText(owner.getEmail(), false);
                    selectedOwnerId = owner.getUserId();
                    break;
                }
            }
        }

        if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
            // Hiển thị dấu "x" để xóa ảnh
            ivRemovePicture.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(new File(selectedImagePath))
                    .placeholder(R.drawable.icon_undefine_user)
                    .error(R.drawable.icon_undefine_user)
                    .into(ivAddPicture);
        }

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

        autoCompleteOwnerRestaurant.setOnClickListener(view -> {
            autoCompleteOwnerRestaurant.showDropDown();
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
                    saveRestaurantToDatabase();
                }
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

        boolean isSuccess = adminRepository.updateRestaurantInDatabase(idSelectedRestaurant, name, address, email, phoneNumber, openingHour, closingHour, internalImagePath, ownerId);

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

    public void removePicture(View view) {
        // Xóa ảnh và ẩn dấu "x"
        String imagePath = selectedImagePath;

        // Kiểm tra xem đường dẫn ảnh có hợp lệ và không phải null
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);

            // Kiểm tra xem tệp ảnh có tồn tại không
            if (imageFile.exists()) {
                boolean isDeleted = imageFile.delete();

                // Xóa ảnh nếu tồn tại
                if (isDeleted) {
                    Log.d("DeleteRestaurant", "Ảnh đã được xóa thành công.");
                } else {
                    Log.d("DeleteRestaurant", "Không thể xóa ảnh.");
                }
            } else {
                // Nếu ảnh không tồn tại, bỏ qua
                Log.d("DeleteRestaurant", "Ảnh không tồn tại: " + imagePath);
            }
        }

        ivAddPicture.setImageResource(R.drawable.img_manager_add_picture); // Đặt lại ảnh mặc định
        ivRemovePicture.setVisibility(View.GONE); // Ẩn dấu "x"
        selectedImagePath = null;
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
            File directory = new File(getFilesDir(), "restaurant_images");
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
                    selectedImagePath = mainRepository.getRealPathFromURI(data.getData());
                    // Hiển thị ảnh trong ImageView ivAddPicture
                    ivAddPicture.setImageURI(data.getData());

                    // Hiển thị dấu "x" để xóa ảnh
                    ivRemovePicture.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(AdminEditRestaurantActivity.this, "Không có ảnh được chọn", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Đăng ký activityResultLauncher
    private final androidx.activity.result.ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback);
}