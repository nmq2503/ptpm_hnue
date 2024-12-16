package com.nmq.foodninjaver2.features.Home.MenuDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.HomeActivity;
import com.nmq.foodninjaver2.features.Home.RestaurantDetail.RestaurantDetailActivity;

public class ItemMenuActivity extends AppCompatActivity {
    ImageView menuItemBackBtn, imgItemMenu;
    TextView titleTv, priceTv, ratingTv, timeTv, descriptionsTv;
    RatingBar ratingBar;
    RecyclerView rvReview;
    TextView addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_menu);

        // Ánh xạ các thành phần giao diện
        menuItemBackBtn = findViewById(R.id.menuItemBackBtn);
        imgItemMenu = findViewById(R.id.imgItemMenu);
        titleTv = findViewById(R.id.titleTv);
        priceTv = findViewById(R.id.priceTv);
        ratingBar = findViewById(R.id.ratingBar);
        ratingTv = findViewById(R.id.ratingTv);
        timeTv = findViewById(R.id.timeTv);
        descriptionsTv = findViewById(R.id.descriptionsTv);
        addToCartBtn = findViewById(R.id.addToCartBtn);
        rvReview = findViewById(R.id.rvReview);

        menuItemBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemMenuActivity.this, MenuDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}