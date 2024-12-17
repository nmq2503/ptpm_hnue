package com.nmq.foodninjaver2.features.Home.DetailRestaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.HomeActivity;
import com.nmq.foodninjaver2.features.Home.Adapter.RestaurantAdapter;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;

import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity {
    private RecyclerView rvRestaurantList;
    private RestaurantAdapter adapterRes;
    private ArrayList<RestaurantDomain> restaurantList, originalRestaurantList;
    private ImageButton btnBack;
    private EditText edtTimKiem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        rvRestaurantList = findViewById(R.id.rvRestaurantList);
        rvRestaurantList.setLayoutManager(new GridLayoutManager(this,2));

        restaurantList = new ArrayList<>();
        restaurantList.add(new RestaurantDomain("Fast Food", "res3"));
        restaurantList.add(new RestaurantDomain("Ninja Bakery", "cake2"));
        restaurantList.add(new RestaurantDomain("Superstar Cafe", "res4"));
        restaurantList.add(new RestaurantDomain("Healthy Food", "res1"));
        restaurantList.add(new RestaurantDomain("Nhà hàng 5", "res5"));

        originalRestaurantList = new ArrayList<>(restaurantList);
        adapterRes = new RestaurantAdapter(restaurantList);
        rvRestaurantList.setAdapter(adapterRes);


        // Back
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetailActivity.this, HomeActivity.class);
                startActivity(intent);
//              finish();
            }
        });

        // Tìm kiếm
        edtTimKiem = findViewById(R.id.edtTimKiem);
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void filterList(String string) {
        ArrayList<RestaurantDomain> filteredList = new ArrayList<>();
        for (RestaurantDomain item : originalRestaurantList) {
            if (item.getTitle().toLowerCase().contains(string.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterRes.updateList(filteredList);
    }
}