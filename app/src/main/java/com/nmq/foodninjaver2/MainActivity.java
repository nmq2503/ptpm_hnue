package com.nmq.foodninjaver2;

import android.os.Bundle;
import android.widget.Button;

import android.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.features.auth.Product;
import com.nmq.foodninjaver2.features.auth.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "app_prefs";
    public static final String KEY_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ánh xạ RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // Thiết lập LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách sản phẩm
        List<Product> products = new ArrayList<>();
        products.add(new Product("Spicy Fresh Crab 1", "35$", "Delicious spicy crab dish 1", R.drawable.monan));
        products.add(new Product("Spicy Fresh Crab 2", "50$", "Delicious spicy crab dish 2", R.drawable.monan));
        products.add(new Product("Spicy Fresh Crab 3", "100$", "Delicious spicy crab dish 3", R.drawable.monan));
        products.add(new Product("Spicy Fresh Crab 4", "500$", "Delicious spicy crab dish 4", R.drawable.monan));

        // Gán Adapter cho RecyclerView
        ProductAdapter adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);

        // Thiết lập padding cho hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buyAgainButton1 = findViewById(R.id.buy_again_button1);
        Button buyAgainButton2 = findViewById(R.id.buy_again_button2);
        Button buyAgainButton3 = findViewById(R.id.buy_again_button3);
        Button buyAgainButton4 = findViewById(R.id.buy_again_button4);

        // Thiết lập sự kiện click cho các nút
        buyAgainButton1.setOnClickListener(v -> showPurchaseSuccessDialog());
        buyAgainButton2.setOnClickListener(v -> showPurchaseSuccessDialog());
        buyAgainButton3.setOnClickListener(v -> showPurchaseSuccessDialog());
        buyAgainButton4.setOnClickListener(v -> showPurchaseSuccessDialog());
    }

    // Hàm hiển thị thông báo mua hàng thành công
    private void showPurchaseSuccessDialog() {
        // Tạo một AlertDialog
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Mua hàng thành công")
                .setMessage("Sản phẩm của bạn đã được mua thành công!")
                .setPositiveButton("OK", null)
                .show();


    }
}
