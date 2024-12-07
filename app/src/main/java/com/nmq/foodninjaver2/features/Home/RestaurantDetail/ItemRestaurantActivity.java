package com.nmq.foodninjaver2.features.Home.RestaurantDetail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;

import java.util.ArrayList;

public class ItemRestaurantActivity extends AppCompatActivity {
    TextView resName;
    ArrayList<MenuItem> menuList;
    RecyclerView rvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_restaurant);
        RestaurantDomain object = (RestaurantDomain) getIntent().getSerializableExtra("object");

        resName = findViewById(R.id.res_name);
        if (object != null) {
            resName.setText(object.getTitle());
        }
    }
}