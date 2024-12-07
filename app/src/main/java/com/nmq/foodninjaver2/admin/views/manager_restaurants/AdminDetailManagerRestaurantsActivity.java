package com.nmq.foodninjaver2.admin.views.manager_restaurants;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.adapters.ManagerRestaurantsAdapter;
import com.nmq.foodninjaver2.admin.adapters.ManagerUsersAdapter;
import com.nmq.foodninjaver2.admin.repository.AdminRepository;
import com.nmq.foodninjaver2.admin.views.AdminActivity;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminAddUserActivity;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminDetailManagerUsersActivity;
import com.nmq.foodninjaver2.admin.views.manager_users.AdminEditUserActivity;
import com.nmq.foodninjaver2.core.modelBase.RestaurantModel;
import com.nmq.foodninjaver2.core.modelBase.UserModel;

import java.io.File;
import java.util.List;

public class AdminDetailManagerRestaurantsActivity extends AppCompatActivity {

    ImageView ivBackBtn;
    RecyclerView recyclerViewManagerRestaurants;
    ManagerRestaurantsAdapter adapter;
    AdminRepository adminRepository;
    List<RestaurantModel> restaurantList;

    Button btnAdd;
    Button btnEdit;
    Button btnDelete;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_detail_manager_restaurants);

        ivBackBtn = findViewById(R.id.ivBackBtn);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        recyclerViewManagerRestaurants = findViewById(R.id.recyclerViewManagerRestaurants);
        // searchView = findViewById(R.id.searchView);

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false; // Không xử lý khi submit
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (newText.isEmpty()) {
//                    // Hiển thị lại danh sách đầy đủ
//                    updateRestaurants();
//                } else {
//                    // Lọc danh sách
//                    adapter.filter(newText);
//                }
//                return true;
//            }
//        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDetailManagerRestaurantsActivity.this, AdminAddRestaurantActivity.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantModel selectedRestaurant = adapter.getSelectedRestaurant();
                if (selectedRestaurant != null) {
                    editSelectedRestaurant(selectedRestaurant);
                } else {
                    Toast.makeText(AdminDetailManagerRestaurantsActivity.this, "Vui lòng chọn nhà hàng cần sửa thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            RestaurantModel selectedRestaurant = adapter.getSelectedRestaurant();
            if (selectedRestaurant != null) {
                // Gọi hàm xác nhận xóa
                confirmDeleteRestaurant(selectedRestaurant);
            } else {
                Toast.makeText(AdminDetailManagerRestaurantsActivity.this, "Vui lòng chọn nhà hàng cần xóa!", Toast.LENGTH_SHORT).show();
            }
        });


        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về AdminActivity
                Intent intent = new Intent(AdminDetailManagerRestaurantsActivity.this, AdminActivity.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });

        recyclerViewManagerRestaurants.setLayoutManager(new LinearLayoutManager(this));

        adminRepository = new AdminRepository(this);
        restaurantList = adminRepository.getAllRestaurantsWithOwners();

        adapter = new ManagerRestaurantsAdapter(restaurantList);
        recyclerViewManagerRestaurants.setAdapter(adapter);
    }

    private void editSelectedRestaurant(RestaurantModel selectedRestaurant) {
        if (selectedRestaurant != null) {
            Intent intent = new Intent(AdminDetailManagerRestaurantsActivity.this, AdminEditRestaurantActivity.class);

            intent.putExtra("restaurant_id", selectedRestaurant.getRestaurantId());
            intent.putExtra("restaurant_name", selectedRestaurant.getRestaurantName());
            intent.putExtra("address", selectedRestaurant.getAddress());
            intent.putExtra("email", selectedRestaurant.getEmail());
            intent.putExtra("phone_number", selectedRestaurant.getPhoneNumber());
            intent.putExtra("opening_hours", selectedRestaurant.getOpeningHours());
            intent.putExtra("closing_hours", selectedRestaurant.getClosingHours());
            intent.putExtra("url_image_restaurant", selectedRestaurant.getUrlImageRestaurant());
            intent.putExtra("owner_id", selectedRestaurant.getOwnerId());

            startActivity(intent);
        }
    }

    private void confirmDeleteRestaurant(RestaurantModel selectedRestaurant) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(false);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Bạn có chắc chắn muốn xóa nhà hàng này không?");
        tvTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                adapter.clearSelection();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDelete = adminRepository.deleteRestaurantById(selectedRestaurant.getRestaurantId());
                if (isDelete) {
                    // Lấy đường dẫn tệp ảnh từ URL
                    String imagePath = selectedRestaurant.getUrlImageRestaurant();

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
                    } else {
                        // Đường dẫn ảnh không hợp lệ (null hoặc trống)
                        Log.d("DeleteRestaurant", "Đường dẫn ảnh không hợp lệ.");
                    }

                    updateRestaurant();

                    Toast.makeText(AdminDetailManagerRestaurantsActivity.this, "Đã xóa nhà hàng", Toast.LENGTH_SHORT).show();
                    adapter.clearSelection();
                    dialog.dismiss();
                } else {
                    Toast.makeText(AdminDetailManagerRestaurantsActivity.this, "Xóa nhà hàng thất bại!", Toast.LENGTH_SHORT).show();
                    adapter.clearSelection();
                }
            }
        });

        dialog.show();
    }

    public void updateRestaurant() {
        restaurantList = adminRepository.getAllRestaurantsWithOwners();

        if (restaurantList != null) {
            adapter.updateData(restaurantList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.clearSelection();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật dữ liệu khi quay lại Activity
        updateRestaurant();
    }
}