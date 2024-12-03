package com.nmq.foodninjaver2.features.profile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nmq.foodninjaver2.R;

class ProductDetailActivity extends AppCompatActivity {

    private TextView productNameTextView, productPriceTextView, productDescriptionTextView;
    private ImageView productImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);  // Đảm bảo layout đúng tên

        // Lấy dữ liệu từ Intent
        String productName = getIntent().getStringExtra("product_name");
        String productPrice = getIntent().getStringExtra("product_price");
        String productDescription = getIntent().getStringExtra("product_description");

        // Ánh xạ các TextView và ImageView từ layout
        productNameTextView = findViewById(R.id.product_name_detail);
        productPriceTextView = findViewById(R.id.product_price_detail);
        productDescriptionTextView = findViewById(R.id.product_description_detail);
        productImageView = findViewById(R.id.product_image_detail);

        // Hiển thị thông tin sản phẩm
        productNameTextView.setText(productName);
        productPriceTextView.setText(productPrice);
        productDescriptionTextView.setText(productDescription);

    }

}
