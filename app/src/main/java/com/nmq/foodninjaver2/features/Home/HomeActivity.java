package com.nmq.foodninjaver2.features.Home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.admin.views.AdminActivity;
import com.nmq.foodninjaver2.core.SessionManager;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.features.Home.Adapter.PopularMenuAdapter;
import com.nmq.foodninjaver2.features.Home.Adapter.RestaurantAdapter;
import com.nmq.foodninjaver2.features.Home.RestaurantDetail.RestaurantDetailActivity;
import com.nmq.foodninjaver2.features.Home.MenuDetail.MenuDetailActivity;
import com.nmq.foodninjaver2.features.Home.Model.MenuDomain;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;
import com.nmq.foodninjaver2.features.auth.LoginActivity;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.features.cart.FoodCartActivity;
import com.nmq.foodninjaver2.features.profile.ProfileActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private PopularMenuAdapter adapter;
    private RestaurantAdapter adapterRes;
    private RecyclerView recyclerViewMenuList, recyclerViewRes;
    private ArrayList<MenuDomain> menuList, originalMenuList;
    private ArrayList<RestaurantDomain> restaurantList, originalRestaurantList;
    private EditText edtTimKiem;
    private TextView tvViewMore, tvViewMoreMenu;
    private ImageView imgAvt;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Anh xa view
        edtTimKiem = findViewById(R.id.edtTimKiem);
        tvViewMoreMenu = findViewById(R.id.tvViewMoreMenu);
        tvViewMore = findViewById(R.id.tvViewMore);
        imgAvt = findViewById(R.id.imgAvt);
        recyclerViewMenuList = findViewById(R.id.rvMenu);
        recyclerViewRes = findViewById(R.id.rvRestaurant);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        // Khoi tao database
        dataBaseHelper = new DataBaseHelper(this);

        // Khởi tạo SessionManager để lấy thông tin phiên đăng nhập
        SessionManager sessionManager = new SessionManager(this);

        // Lấy danh sách món ăn từ DatabaseHelper
        menuList = dataBaseHelper.getAllMenuItems();
        originalMenuList = new ArrayList<>(menuList); // Luu DS goc

        // Lấy danh sách nhà hàng từ DatabaseHelper
        restaurantList = dataBaseHelper.getAllRestaurants();
        originalRestaurantList = new ArrayList<>(restaurantList);

        // Thiet lap RecyclerView
        recyclerViewMenu();
        recyclerViewRes();

        // Profile
        int userId = sessionManager.getUserId();
        if (userId == -1) {
            // Nếu userId không tồn tại, chuyển về trang đăng nhập
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            String imgUrl = dbHelper.getUserProfile(userId);
            imgAvt = findViewById(R.id.imgAvt);
            if (imgUrl != null) {
                imgAvt.setImageResource(R.drawable.icon_undefine_user);
            } else {
                Glide.with(this)
                        .load(imgUrl)
                        .placeholder(R.drawable.icon_undefine_user) // Hiển thị khi ảnh đang tải
                        .error(R.drawable.icon_undefine_user)       // Hiển thị nếu tải ảnh thất bại
                        .into(imgAvt);
            }
        }

        // Xu ly tim kiem
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable CharSequence) {}
        });

//        // View More Menu
        tvViewMoreMenu = findViewById(R.id.tvViewMoreMenu);
        tvViewMoreMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenuDetailActivity.class);
                startActivity(intent);
            }
        });

        // View more Res
        tvViewMore = findViewById(R.id.tvViewMore);
        tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RestaurantDetailActivity.class);
                startActivity(intent);
            }
        });

        // Thanh bar
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.action_profile){
                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
            }
            else if (item.getItemId() == R.id.action_cart) {
                Intent intentCart= new Intent(HomeActivity.this, FoodCartActivity.class);
                startActivity(intentCart);
            } return true;

        });

        imgAvt.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });
    }

    private void showLogoutConfirmationDialog() {
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
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        tvTitle.setText("Bạn có chắc chắn muốn đăng xuất không?");
        btnDelete.setText("Đồng ý");
        btnCancel.setText("Hủy");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }

    // Hàm thiết lập RecyclerView cho menu
    private void recyclerViewMenu() {
        LinearLayoutManager layoutManagerMenu = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewMenuList.setLayoutManager(layoutManagerMenu);

        ArrayList<MenuDomain> limitedMenuList = new ArrayList<>();
        if (menuList.size() > 4) {
            limitedMenuList.addAll(menuList.subList(0, 4)); // Lấy 4 món đầu tiên
        } else {
            limitedMenuList.addAll(menuList); // Nếu ít hơn 4 món, lấy toàn bộ
        }
        adapter = new PopularMenuAdapter(limitedMenuList);
        recyclerViewMenuList.setAdapter(adapter);
    }

    // Hàm thiết lập RecyclerView cho nhà hàng
    private void recyclerViewRes() {
        LinearLayoutManager layoutManagerRes = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRes.setLayoutManager(layoutManagerRes);

        ArrayList<RestaurantDomain> limitedResList = new ArrayList<>();
        if (restaurantList.size() > 4) {
            limitedResList.addAll(restaurantList.subList(0, 4)); // Lấy 4 nhà hàng đầu tiên
        } else {
            limitedResList.addAll(restaurantList); // Nếu ít hơn 4 nhà hàng, lâý toàn bộ
        }
        adapterRes = new RestaurantAdapter(limitedResList);
        recyclerViewRes.setAdapter(adapterRes);
    }

    // Lọc danh sách dựa trên chuỗi tìm kiếm
    private void filterList(String text) {
        // Lọc danh sách menu
        ArrayList<MenuDomain> filteredList = new ArrayList<>();
        for (MenuDomain item : originalMenuList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);

        // Lọc danh sách nhà hàng
        ArrayList<RestaurantDomain> filteredRestaurantList = new ArrayList<>();
        for (RestaurantDomain restaurant : originalRestaurantList) {
            if (restaurant.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredRestaurantList.add(restaurant);
            }
        }
        adapterRes.updateList(filteredRestaurantList);
    }
}