package com.nmq.foodninjaver2.admin.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmq.foodninjaver2.core.modelBase.RestaurantModel;
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

        // Câu truy vấn để lấy tất cả dữ liệu từ bảng USER và ROLE
        String query = "SELECT u.user_id, u.user_name, u.first_name, u.last_name, u.email, u.password, " +
                "u.phone_number, u.address, u.date_of_birth, u.url_image_profile, u.create_at, u.update_at, " +
                "r.role_id, r.role_name " +
                "FROM USER u " +
                "JOIN ROLE r ON u.role_id = r.role_id";

        Cursor cursor = dbHelper.executeQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    UserModel user = new UserModel();

                    // Lấy tất cả các cột từ bảng USER và ROLE
                    user.setUserId(cursor.getInt(0));
                    user.setUserName(cursor.getString(1));
                    user.setFirstName(cursor.getString(2));
                    user.setLastName(cursor.getString(3));
                    user.setEmail(cursor.getString(4));
                    user.setPassword(cursor.getString(5));
                    user.setPhoneNumber(cursor.getString(6));
                    user.setAddress(cursor.getString(7));
                    user.setDateOfBirth(cursor.getString(8));
                    user.setUrlImageProfile(cursor.getString(9));
                    user.setCreateAt(cursor.getString(10));
                    user.setUpdateAt(cursor.getString(11));
                    user.setRoleId(cursor.getInt(12));
                    user.setRoleName(cursor.getString(13));

                    // Thêm user vào danh sách
                    userList.add(user);
                } while (cursor.moveToNext());
            } else {
                Log.d("Database", "No data found in query.");
            }
            cursor.close();
        } else {
            Log.e("Database", "Cursor is null. Query execution failed.");
        }

        return userList;
    }

    public boolean isEmailExist(String email) {
        // Câu lệnh SQL kiểm tra email
        String query = "SELECT COUNT(*) FROM USER WHERE email = ?";
        Cursor cursor = null;

        try {
            // Sử dụng executeQuery để lấy kết quả
            cursor = dbHelper.executeQuery(query, new String[]{email});
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                return count > 0; // Trả về true nếu email đã tồn tại
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public boolean isEmailExist(String email, int excludeUserId) {
        // Kiểm tra email, loại trừ user hiện tại
        String query = "SELECT COUNT(*) FROM USER WHERE email = ? AND user_id != ?";
        Cursor cursor = null;

        try {
            cursor = dbHelper.executeQuery(query, new String[]{email, String.valueOf(excludeUserId)});
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                return count > 0; // Trả về true nếu email đã tồn tại
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public boolean saveUserToDatabase(String userName, String email, String password, String selectedImagePath, int roleId) {
        // Chuẩn bị câu lệnh SQL INSERT
        String sql = "INSERT INTO USER (user_name, email, password, url_image_profile, role_id) VALUES (?, ?, ?, ?, ?)";

        // Lấy giá trị từ tham số phương thức
        Object[] bindArgs = new Object[] {
                userName,
                email,
                password,
                selectedImagePath, // Lưu đường dẫn ảnh vào cơ sở dữ liệu
                roleId,
        };

        try {
            // Sử dụng phương thức executeNonQuery để thực thi câu lệnh SQL
            boolean isSuccess = dbHelper.executeNonQuery(sql, bindArgs);
            return isSuccess;
        } catch (Exception e) {
            // Ghi log lỗi (nếu có) để dễ dàng debug
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserInDatabase(int userId, String userName, String email, String password, String selectedImagePath, int roleId) {
        // Chuẩn bị câu lệnh SQL UPDATE
        String sql = "UPDATE USER SET user_name = ?, email = ?, password = ?, url_image_profile = ?, role_id = ? WHERE user_id = ?";

        // Lấy giá trị từ tham số phương thức
        Object[] bindArgs = new Object[] {
                userName,            // Tên người dùng
                email,               // Email người dùng
                password,            // Mật khẩu người dùng
                selectedImagePath,   // Đường dẫn ảnh hồ sơ
                roleId,
                userId,              // ID người dùng cần cập nhật
        };

        try {
            // Sử dụng phương thức executeNonQuery để thực thi câu lệnh SQL
            boolean isSuccess = dbHelper.executeNonQuery(sql, bindArgs);
            return isSuccess;  // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            // Ghi log lỗi (nếu có) để dễ dàng debug
            e.printStackTrace();
            return false;  // Trả về false nếu có lỗi xảy ra
        }
    }

    public boolean deleteUserById(int userId) {
        // Câu lệnh SQL xóa dữ liệu theo user_id
        String sql = "DELETE FROM " + DataBaseHelper.TABLE_USER + " WHERE user_id = ?";
        Object[] bindArgs = new Object[]{userId};

        // Gọi phương thức executeNonQuery để thực thi câu lệnh SQL
        // boolean isSuccess = dbHelper.executeNonQuery(sql, bindArgs);

        // Gọi phương thức executeNonQuery để thực thi câu lệnh SQL
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows = db.delete(DataBaseHelper.TABLE_USER, "user_id = ?", new String[]{String.valueOf(userId)});


        // Kiểm tra nếu câu lệnh không gặp lỗi, trả về true
        if (affectedRows > 0) {
            // Nếu không có lỗi trong quá trình xóa, bạn có thể kiểm tra thêm nếu cần (như số lượng dòng bị xóa)
            return true;
        } else {
            // Nếu có lỗi trong quá trình xóa
            return false;
        }
    }

    public List<RestaurantModel> getAllRestaurantsWithOwners() {
        List<RestaurantModel> restaurantList = new ArrayList<>();

        // Câu truy vấn để lấy tất cả dữ liệu từ bảng RESTAURANT và USER
        String query = "SELECT r.restaurant_id, r.restaurant_name, r.address, r.email, r.phone_number, " +
                "r.rating, r.opening_hours, r.closing_hours, r.url_image_restaurant, r.owner_id, " +
                "u.user_name, u.first_name, u.last_name, u.email AS owner_email " +
                "FROM RESTAURANT r " +
                "JOIN USER u ON r.owner_id = u.user_id";

        Cursor cursor = dbHelper.executeQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    RestaurantModel restaurant = new RestaurantModel();

                    // Lấy tất cả các cột từ bảng RESTAURANT
                    restaurant.setRestaurantId(cursor.getInt(0));
                    restaurant.setRestaurantName(cursor.getString(1));
                    restaurant.setAddress(cursor.getString(2));
                    restaurant.setEmail(cursor.getString(3));
                    restaurant.setPhoneNumber(cursor.getString(4));
                    restaurant.setRating(cursor.getFloat(5));
                    restaurant.setOpeningHours(cursor.getString(6));
                    restaurant.setClosingHours(cursor.getString(7));
                    restaurant.setUrlImageRestaurant(cursor.getString(8));
                    restaurant.setOwnerId(cursor.getInt(9));
                    restaurant.setOwnerName(cursor.getString(10));

                    // Thêm restaurant vào danh sách
                    restaurantList.add(restaurant);
                } while (cursor.moveToNext());
            } else {
                Log.d("Database", "No data found in query.");
            }
            cursor.close();
        } else {
            Log.e("Database", "Cursor is null. Query execution failed.");
        }

        return restaurantList;
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
