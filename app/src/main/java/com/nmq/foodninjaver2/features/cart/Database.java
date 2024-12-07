package com.nmq.foodninjaver2.features.cart;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.features.cart.Model.Food;
import com.nmq.foodninjaver2.features.cart.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private SQLiteDatabase db;

    public Database(Context context) {
        SQLiteOpenHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm đơn hàng vào giỏ hàng
    public void addToCart(Order order) {
        ContentValues values = new ContentValues();
        values.put("foodId", order.getFoodId());
        values.put("foodName", order.getFoodName());
        values.put("quantity", order.getQuantity());
        values.put("price", order.getPrice());
        values.put("discount", order.getDiscount());
        db.insert("Cart", null, values);
    }
    // Phương thức để lấy món ăn theo ID
    public Food getFoodById(String foodId) {
        Cursor cursor = db.query("Foods", null, "Id=?", new String[]{foodId}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Food food = new Food(
                    cursor.getString(cursor.getColumnIndexOrThrow("Id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Discount")),
                    cursor.getString(cursor.getColumnIndexOrThrow("Image")) // Lưu tên file ảnh
            );
            cursor.close();
            return food;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null; // Nếu không tìm thấy món ăn
    }
    // Lấy danh sách đơn hàng từ cơ sở dữ liệu
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM orders", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("product_name"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
                @SuppressLint("Range") String foodId = cursor.getString(cursor.getColumnIndex("food_id"));

                orders.add(new Order(foodId, productName, quantity, price));
            }
            cursor.close();
        }
        return orders;
    }
}
