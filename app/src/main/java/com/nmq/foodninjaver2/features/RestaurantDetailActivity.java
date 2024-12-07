package com.nmq.foodninjaver2.features;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nmq.foodninjaver2.Adapter.MonAnAdapter;
import com.nmq.foodninjaver2.MainActivity;
import com.nmq.foodninjaver2.Model.Comment;
import com.nmq.foodninjaver2.Model.MonAn;
import com.nmq.foodninjaver2.Model.Product;
import com.nmq.foodninjaver2.Model.Restaurant;
import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.RestaurantDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {
     ImageView imageRestaurant;
     TextView textRestaurantName;
     TextView textDistance;
     TextView textRating;
     TextView textDescription;
     Button viewAllText;
     ImageView  crab1, crab2, crab3, crab4;
     ImageButton backButton;
     LinearLayout popularMenuContainer, commentsContainer;
     RecyclerView lvMonAn;
     ArrayList<MonAn> listMonAn;
     MonAnAdapter adapterMonAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_detail);

        imageRestaurant = findViewById(R.id.imageRestaurant);
        textRestaurantName = findViewById(R.id.textRestaurantName);
        textDistance = findViewById(R.id.textDistance);
        textRating = findViewById(R.id.textRating);
        textDescription = findViewById(R.id.textDescription);
        popularMenuContainer = findViewById(R.id.popularMenuContainer);
        commentsContainer = findViewById(R.id.commentsContainer);
        viewAllText = findViewById(R.id.viewAllText);
        backButton = findViewById(R.id.backButton);
        lvMonAn = findViewById(R.id.lvMonAn);
        listMonAn = new ArrayList<>();
        listMonAn.add(new MonAn(R.drawable.pizza));
        listMonAn.add(new MonAn(R.drawable.img));
        listMonAn.add(new MonAn(R.drawable.pizza));
        listMonAn.add(new MonAn(R.drawable.img));

        // Thiết lập adapter và layout
        adapterMonAn = new MonAnAdapter(this, listMonAn);
        lvMonAn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        lvMonAn.setAdapter(adapterMonAn);
        MonAnAdapter adapterMonAn = new MonAnAdapter(this, listMonAn);


        RestaurantDatabaseHelper dbHelper = new RestaurantDatabaseHelper(this);
        int restaurantId = getIntent().getIntExtra("restaurant_id", -1);
        if (restaurantId != -1) {
            // Lấy thông tin chi tiết của nhà hàng từ cơ sở dữ liệu
            Restaurant restaurant = dbHelper.getRestaurantById(restaurantId);

            if (restaurant != null) {
                // Hiển thị thông tin chi tiết nhà hàng
                textRestaurantName.setText(restaurant.getName());
                textDescription.setText(restaurant.getDescription());
                textRating.setText(String.valueOf(restaurant.getRating()));
            }
        }
        addEvents();

        // Khởi tạo các view


        // Nhận Intent và đối tượng Restaurant
        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

        if (restaurant != null) {
            // Gọi phương thức hiển thị thông tin nhà hàng
            displayRestaurantDetails(restaurant);
        } else {
            Log.e("RestaurantDetailActivity", "Không nhận được đối tượng Restaurant!");
        }

        // Điều chỉnh layout để tránh phần hiển thị bị che khuất bởi thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void addEvents() {
        // Gắn sự kiện cho nút Back
        backButton.setOnClickListener(v -> {
            Log.d("RestaurantDetailActivity", "Back button clicked");
            Intent intent = new Intent(RestaurantDetailActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Gắn sự kiện cho nút View All
        viewAllText.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantDetailActivity.this, ViewAllActivity.class);
            startActivity(intent);
        });
    }

    private void displayRestaurantDetails(Restaurant restaurant) {
        // Hiển thị thông tin nhà hàng
        textRestaurantName.setText(restaurant.getName());
        textDistance.setText(restaurant.getDistance());
        textRating.setText(String.format("%.1f Rating", restaurant.getRating()));
        textDescription.setText(restaurant.getDescription());

        // Hiển thị ảnh nhà hàng
        Glide.with(RestaurantDetailActivity.this)
                .load(restaurant.getImageUrl())
                .into(imageRestaurant);

        // Thêm các sản phẩm vào danh sách
        for (Product product : restaurant.getProducts()) {
            addProductView(product);
        }

        // Thêm các bình luận vào danh sách bình luận
        List<Comment> comments = restaurant.getComments();  // Đảm bảo rằng getComments() trả về danh sách Comment
        if (comments != null && !comments.isEmpty()) {
            for (Comment comment : comments) {
                addCommentView(comment);
            }
        }
    }

    private void addProductView(Product product) {
        // Tạo layout cho từng sản phẩm
        LinearLayout productLayout = new LinearLayout(this);
        productLayout.setOrientation(LinearLayout.VERTICAL);
        productLayout.setPadding(8, 8, 8, 8);
        productLayout.setGravity(Gravity.CENTER);

        // ImageView cho sản phẩm
        ImageView productImage = new ImageView(this);
        productImage.setLayoutParams(new LinearLayout.LayoutParams(100, 150));

        // Tải ảnh sản phẩm
        Glide.with(this)
                .load(product.getImageUrl())
                .into(productImage);

        // TextView cho tên sản phẩm
        TextView productName = new TextView(this);
        productName.setText(product.getName());
        productName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        productName.setPadding(0, 4, 0, 4);

        // TextView cho giá sản phẩm
        TextView productPrice = new TextView(this);
        productPrice.setText("$" + product.getPrice());
        productPrice.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        productPrice.setPadding(0, 4, 0, 4);

        // Thêm các view vào layout sản phẩm
        productLayout.addView(productImage);
        productLayout.addView(productName);
        productLayout.addView(productPrice);

        // Thêm layout sản phẩm vào container chính
        popularMenuContainer.addView(productLayout);
    }

    private void addCommentView(Comment comment) {
        // Tạo một layout cho từng bình luận
        LinearLayout commentLayout = new LinearLayout(this);
        commentLayout.setOrientation(LinearLayout.VERTICAL);
        commentLayout.setPadding(8, 8, 8, 8);

        // TextView cho tên người dùng
        TextView userName = new TextView(this);
        userName.setText(comment.getUserName());
        userName.setTextSize(16);

        // TextView cho nội dung bình luận
        TextView commentContent = new TextView(this);
        commentContent.setText(comment.getComment());  // Dùng đúng thuộc tính "content" cho bình luận
        commentContent.setTextSize(14);

        // Thêm các view vào layout bình luận
        commentLayout.addView(userName);
        commentLayout.addView(commentContent);

        // Thêm layout bình luận vào container chính
        commentsContainer.addView(commentLayout);
    }
}
