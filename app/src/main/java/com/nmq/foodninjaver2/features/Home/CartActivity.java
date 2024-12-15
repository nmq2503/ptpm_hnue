package com.nmq.foodninjaver2.features.Home;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.Home.CartAdapter;

import java.util.ArrayList;
import java.util.List;
import com.nmq.foodninjaver2.features.Home.CartItem;
public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCart;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Khởi tạo RecyclerView
        recyclerCart = findViewById(R.id.recycler_cart);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));

        // Danh sách sản phẩm(lấy từ database)
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Product 1", 2, 20.0, R.drawable.monan));
        cartItems.add(new CartItem("Product 2", 1, 35.0, R.drawable.pop_1));
        cartItems.add(new CartItem("Product 3", 3, 15.0, R.drawable.pop_2));

        // Gắn Adapter
        cartAdapter = new CartAdapter(cartItems);
        recyclerCart.setAdapter(cartAdapter);
    }
}
