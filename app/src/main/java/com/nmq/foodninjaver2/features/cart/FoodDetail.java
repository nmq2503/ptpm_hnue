package com.nmq.foodninjaver2.features.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.features.cart.Model.Food;
import com.nmq.foodninjaver2.features.cart.Model.Order;
// import com.squareup.picasso.Picasso;


public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    EditText numberButton;
    Button btn_add_to_cart;
    String mFoodId = "";
    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Liên kết các View với ID trong layout
        numberButton = findViewById(R.id.number_button);
        food_name = findViewById(R.id.food_name);
        food_description = findViewById(R.id.food_description);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.food_image);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);

        // Nhận ID món ăn từ Intent
        if (getIntent() != null) {
            mFoodId = getIntent().getStringExtra("FoodId");
        }

        if (!mFoodId.isEmpty()) {
            getFoodDetails();
        }

        // Xử lý sự kiện nút "Thêm vào giỏ hàng"
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Thêm món ăn vào giỏ hàng trong SQLite
                    int quantity = Integer.parseInt(numberButton.getText().toString().trim());
                    if (quantity <= 0) {
                        Toast.makeText(FoodDetail.this, "Số lượng không hợp lệ!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Database database = new Database(getBaseContext());
                    database.addToCart(new Order(
                            mFoodId,
                            currentFood.getName(),
                            String.valueOf(quantity),
                            currentFood.getPrice()
                    ));
                    Toast.makeText(FoodDetail.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(FoodDetail.this, "Vui lòng nhập số lượng hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getFoodDetails() {
        // Lấy thông tin món ăn từ SQLite Database
        Database database = new Database(this);
        currentFood = database.getFoodById(mFoodId);

        if (currentFood != null) {
            // Cập nhật giao diện với thông tin món ăn
            food_name.setText(currentFood.getName());
            food_price.setText("Giá: " + currentFood.getPrice() + " VND");
            food_description.setText(currentFood.getDescription());

            // Xử lý hình ảnh
            String imagePath = currentFood.getImage();
            if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
                // Tải hình ảnh từ URL
                // Picasso.get().load(imagePath).into(food_image);
            } else {
                // Tải hình ảnh từ drawable
                int imageResId = getResources().getIdentifier(imagePath, "drawable", getPackageName());
                if (imageResId != 0) {
                    food_image.setImageResource(imageResId);
                } else {
                    // Hình ảnh không tìm thấy, sử dụng placeholder
                    // Kiểm tra xem hình ảnh placeholder đã tồn tại trong thư mục drawable chưa
                    int placeholderResId = getResources().getIdentifier("placeholder", "drawable", getPackageName());

                    // Nếu placeholder tồn tại, hiển thị nó
                    if (placeholderResId != 0) {
                        food_image.setImageResource(placeholderResId);
                    } else {
                        // Nếu không có placeholder, sử dụng hình ảnh mặc định (có thể là màu nền)
                        food_image.setImageResource(R.color.placeholder_color);  // Bạn có thể tạo placeholder bằng màu sắc nếu chưa có hình ảnh
                    }
                }

            }
        } else {
            Toast.makeText(this, "Món ăn không tồn tại!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
