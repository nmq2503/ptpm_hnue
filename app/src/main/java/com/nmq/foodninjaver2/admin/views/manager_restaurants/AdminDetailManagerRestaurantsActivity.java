package com.nmq.foodninjaver2.admin.views.manager_restaurants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.nmq.foodninjaver2.core.modelBase.RestaurantModel;
import com.nmq.foodninjaver2.core.modelBase.UserModel;

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
//                Intent intent = new Intent(AdminDetailManagerUsersActivity.this, AdminAddUserActivity.class);
//                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RestaurantModel selectedRestaurant = adapter.getSelectedRestaurant();
//                if (selectedRestaurant != null) {
//                    editSelectedUser(selectedRestaurant);
//                } else {
//                    Toast.makeText(AdminDetailManagerUsersActivity.this, "Vui lòng chọn người dùng cần sửa", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            // RestaurantModel selectedRestaurant = adapter.getSelectedRestaurant();
//            if (selectedRestaurant != null) {
//                // Gọi hàm xác nhận xóa
//                confirmDeleteUser(selectedRestaurant);
//            } else {
//                Toast.makeText(AdminDetailManagerUsersActivity.this, "Vui lòng chọn người dùng cần xóa", Toast.LENGTH_SHORT).show();
//            }
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
        // updateUsers();
    }
}