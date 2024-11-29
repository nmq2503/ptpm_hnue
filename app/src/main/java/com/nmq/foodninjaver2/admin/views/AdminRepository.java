package com.nmq.foodninjaver2.admin.views;

import android.content.Context;
import android.database.Cursor;

import com.nmq.foodninjaver2.core.modelBase.UserModel;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminRepository {
    private final DataBaseHelper dbHelper;

    // Constructor
    public AdminRepository(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    // Truy vấn số lượng user
    public int getUserCount() {
        String query = "SELECT COUNT(*) FROM " + DataBaseHelper.TABLE_USER;
        Cursor cursor = dbHelper.executeQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    // Truy vấn số lượng nhà hàng
    public int getRestaurantCount() {
        String query = "SELECT COUNT(*) FROM " + DataBaseHelper.TABLE_RESTAURANT;
        Cursor cursor = dbHelper.executeQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    public List<UserModel> getAllUsersWithRoles() {
        List<UserModel> userList = new ArrayList<>();

        // Câu truy vấn để lấy dữ liệu
        String query = "SELECT u.user_name, u.email, r.role_name " +
                "FROM USER u " +
                "JOIN ROLE r ON u.role_id = r.role_id";

        // Gọi executeQuery để thực thi truy vấn
        Cursor cursor = dbHelper.executeQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setUserName(cursor.getString(0)); // user_name
                user.setEmail(cursor.getString(1));    // email
                user.setRole(cursor.getString(2));     // role_name
                userList.add(user);
            } while (cursor.moveToNext());

            cursor.close(); // Đóng Cursor sau khi sử dụng
        }

        return userList;
    }


    // Truy vấn số lượng món ăn
    public int getMenuItemCount() {
        String query = "SELECT COUNT(*) FROM " + DataBaseHelper.TABLE_MENU_ITEM;
        Cursor cursor = dbHelper.executeQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    // Truy vấn số lượng đơn hàng
    public int getOrderCount() {
        String query = "SELECT COUNT(*) FROM " + DataBaseHelper.TABLE_ORDERS;
        Cursor cursor = dbHelper.executeQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }

    // Thêm nhà hàng mới (INSERT)
    public boolean addRestaurant(String name, String address) {
        String sql = "INSERT INTO " + DataBaseHelper.TABLE_RESTAURANT + " (name, address) VALUES (?, ?)";
        Object[] bindArgs = new Object[]{name, address};
        return dbHelper.executeNonQuery(sql, bindArgs);
    }

    // Cập nhật thông tin nhà hàng (UPDATE)
    public boolean updateRestaurant(int restaurantId, String newName, String newAddress) {
        String sql = "UPDATE " + DataBaseHelper.TABLE_RESTAURANT + " SET name = ?, address = ? WHERE id = ?";
        Object[] bindArgs = new Object[]{newName, newAddress, restaurantId};
        return dbHelper.executeNonQuery(sql, bindArgs);
    }

    // Xóa nhà hàng (DELETE)
    public boolean deleteRestaurant(int restaurantId) {
        String sql = "DELETE FROM " + DataBaseHelper.TABLE_RESTAURANT + " WHERE id = ?";
        Object[] bindArgs = new Object[]{restaurantId};
        return dbHelper.executeNonQuery(sql, bindArgs);
    }
}
