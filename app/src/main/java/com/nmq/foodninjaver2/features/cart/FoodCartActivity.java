package com.nmq.foodninjaver2.features.cart;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nmq.foodninjaver2.R;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodCartActivity extends AppCompatActivity implements FoodCartAdapter.QuantityChangeListener {

    private RecyclerView foodCartItems;
    private TextView subtotalValue, deliveryValue, discountValue, totalValue;
    private ImageView backBtn;
    private ScrollView scrollViewCart;

    private List<FoodCartModel> foodCartList;
    private FoodCartAdapter foodCartAdapter;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcart);

        dbHelper = new DataBaseHelper(this);
        initializeUIComponents();
        setupButtonListeners();

        foodCartList = new ArrayList<>();
        fetchCartItems();
    }

    private void initializeUIComponents() {
        foodCartItems = findViewById(R.id.cardView);
        subtotalValue = findViewById(R.id.textViewSubtotalAmount);
        deliveryValue = findViewById(R.id.textViewDeliveryAmount);
        discountValue = findViewById(R.id.textViewDiscountAmount);
        totalValue = findViewById(R.id.textViewTotalAmount);
        backBtn = findViewById(R.id.backBtn);
        scrollViewCart = findViewById(R.id.scrollviewcart);
    }

    // Khong hieu cau lenh nay la sao
    private void setupButtonListeners() {
        backBtn.setOnClickListener(v -> finish());

        findViewById(R.id.buttonPlaceOrder).setOnClickListener(v -> {
            saveOrder();
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void fetchCartItems() {
        foodCartList.clear(); // Xóa danh sách cũ
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Mở cơ sở dữ liệu ở chế độ chỉ đọc

        // Truy vấn kết hợp bảng ORDER_ITEM và MENU_ITEM
        Cursor cursor = db.rawQuery("SELECT * FROM ORDER_ITEM INNER JOIN MENU_ITEM " +
                "ON ORDER_ITEM.menu_item_id = MENU_ITEM.item_id", null);

        // Kiểm tra cursor
        if (cursor != null) {
            // Duyệt qua các dòng dữ liệu trả về
            while (cursor.moveToNext()) {
                // Lấy dữ liệu từ cursor theo tên cột
                int itemId = cursor.getInt(cursor.getColumnIndexOrThrow("menu_item_id")); // ID của item
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name")); // Tên món
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")); // Giá
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity")); // Số lượng
                String image = cursor.getString(cursor.getColumnIndexOrThrow("url_image_item")); // URL hình ảnh

                // Tạo đối tượng FoodCartModel và thêm vào danh sách
                FoodCartModel model = new FoodCartModel(itemId, name, "", price, quantity, image);
                foodCartList.add(model);
            }
            cursor.close(); // Đóng cursor sau khi sử dụng
        }

        db.close(); // Đóng cơ sở dữ liệu
        displayCartItems(foodCartList); // Hiển thị danh sách
    }



    private void displayCartItems(List<FoodCartModel> foodCartList) {
        foodCartAdapter = new FoodCartAdapter(this, foodCartList, this);
        foodCartItems.setAdapter(foodCartAdapter);
        foodCartItems.setLayoutManager(new GridLayoutManager(this, 1));

        updatePriceDetails(foodCartList);

        if (foodCartList.isEmpty()) {
            scrollViewCart.setVisibility(View.GONE);
            findViewById(R.id.buttonPlaceOrder).setEnabled(false);
        } else {
            scrollViewCart.setVisibility(View.VISIBLE);
            findViewById(R.id.buttonPlaceOrder).setEnabled(true);
        }
    }

    private void updatePriceDetails(List<FoodCartModel> foodCartList) {
        double subtotal = 0.0;
        double deliveryCharge = 5.0; // Giá cố định
        double discount = 0.0; // Giảm giá cố định
        for (FoodCartModel model : foodCartList) {
            subtotal += model.getPrice() * model.getQuantity();
        }

        double total = subtotal + deliveryCharge - discount;

        subtotalValue.setText(String.format("$%.2f", subtotal));
        deliveryValue.setText(String.format("$%.2f", deliveryCharge));
        discountValue.setText(String.format("$%.2f", discount));
        totalValue.setText(String.format("$%.2f", total));
    }

    @Override
    public void onQuantityChanged(FoodCartModel model, int newQuantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Sử dụng phương thức update thay vì execSQL
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", newQuantity);

        String whereClause = "menu_item_id = ?";
        String[] whereArgs = new String[] { String.valueOf(model.getId()) }; // Lấy id là kiểu int

        // Cập nhật số lượng món ăn trong bảng ORDER_ITEM
        db.update("ORDER_ITEM", contentValues, whereClause, whereArgs);
        db.close();

        fetchCartItems();
    }


    private void saveOrder() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Xóa toàn bộ các món trong giỏ hàng
        db.execSQL("DELETE FROM ORDER_ITEM");
        db.close();
        fetchCartItems();
    }

    @Override
    public void onQuantityChanged() {
        // Phương thức này không cần thiết, có thể bỏ qua hoặc loại bỏ
    }

    @Override
    public void onTotalPriceChanged(double totalPrice) {
        // Nếu cần cập nhật tổng giá trị khi thay đổi số lượng, gọi updatePriceDetails()
        updatePriceDetails(foodCartList);
    }
}
