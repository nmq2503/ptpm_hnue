package com.nmq.foodninjaver2.features.Home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.Adapter.PopularMenuAdapter;
import com.nmq.foodninjaver2.features.Home.Adapter.RestaurantAdapter;
import com.nmq.foodninjaver2.features.Home.DetailRestaurant.RestaurantDetailActivity;
import com.nmq.foodninjaver2.features.Home.Model.MenuDomain;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private PopularMenuAdapter adapter;
    private RestaurantAdapter adapterRes;
    private RecyclerView recyclerViewMenuList, recyclerViewRes;
    private ArrayList<MenuDomain> menuList, originalMenuList;
    private ArrayList<RestaurantDomain> restaurantList, originalRestaurantList;
    private EditText edtTimKiem;
    private TextView tvViewMore;

    //    ArrayList<MenuDomain> listSanPhamGoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        edtTimKiem = findViewById(R.id.edtTimKiem);

        recyclerViewMenu();
        recyclerViewRes();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        // Thêm TextWatch cho EditText
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
//        tvViewMoreMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayList<MenuDomain> menuList = new ArrayList<>();
//                menuList.add(new MenuDomain("Pizza", "pizza"));
//                menuList.add(new MenuDomain("Burger", "burger"));
//                menuList.add(new MenuDomain("Hotdog", "hot_dog"));
//                menuList.add(new MenuDomain("Drink", "nuoc_ep_xoai_dao"));
//                menuList.add(new MenuDomain("Donut", "donut"));
//                menuList.add(new MenuDomain("BBQ", "bbq"));
//
//                // Truyền danh sách món ăn qua Intent
////                Intent intent = new Intent(MainActivity.this, DetailMenuActivity.class);
////                intent.putParcelableArrayListExtra("menuList", menuList);
////                startActivity(intent);
//            }
//        });
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
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                return true;
            } else if (item.getItemId() == R.id.action_profile){
                Intent intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
            }
            else if (item.getItemId() == R.id.action_cart) {
                Intent intentCart= new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intentCart);
            } return true;

        });

    }

    private void filterList(String text) {
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

    private void recyclerViewMenu() {
        LinearLayoutManager layoutManagerMenu = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager griLayoutManagerMenu = new GridLayoutManager(this, 2);
        recyclerViewMenuList = findViewById(R.id.rvMenu);
        recyclerViewMenuList.setLayoutManager(layoutManagerMenu);

        ArrayList<MenuDomain> menuList = new ArrayList<>();
        menuList.add(new MenuDomain("Pepperoni pizza", "pop_1", 100.99));
        menuList.add(new MenuDomain("Cheese Burger", "pop_2", 29.99));
        menuList.add(new MenuDomain("Hotdog", "hot_dog", 25.00));
        menuList.add(new MenuDomain("Drink", "nuoc_ep_xoai_dao", 20.00));
        menuList.add(new MenuDomain("Donut", "donut0", 40.00));
        menuList.add(new MenuDomain("BBQ", "bbq", 150.00));

        originalMenuList = new ArrayList<>(menuList);

        adapter = new PopularMenuAdapter(menuList);
        recyclerViewMenuList.setAdapter(adapter);
    }


    private void recyclerViewRes() {
        LinearLayoutManager layoutManagerRes = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRes = findViewById(R.id.rvRestaurant);
        recyclerViewRes.setLayoutManager(layoutManagerRes);

        ArrayList<RestaurantDomain> restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantDomain("Fast Food", "res3"));
        restaurantList.add(new RestaurantDomain("Ninja Bakery", "cake2"));
        restaurantList.add(new RestaurantDomain("Superstar Cafe", "res4"));
        restaurantList.add(new RestaurantDomain("Healthy Food", "res1"));

        originalRestaurantList = new ArrayList<>(restaurantList);

        adapterRes = new RestaurantAdapter(restaurantList);
        recyclerViewRes.setAdapter(adapterRes);
    }
}