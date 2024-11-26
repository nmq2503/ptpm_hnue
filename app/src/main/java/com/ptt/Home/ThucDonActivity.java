package com.ptt.Home;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.ptt.Home.Domain.RestaurantDomain;

import java.util.ArrayList;

public class ThucDonActivity extends AppCompatActivity {
    TextView resName;
    ArrayList<MenuItem> menuList;
    RecyclerView rvMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thuc_don);
        RestaurantDomain object = (RestaurantDomain) getIntent().getSerializableExtra("object");

        resName = findViewById(R.id.res_name);
        if (object != null) {
            resName.setText(object.getTitle());
        }
    }
}