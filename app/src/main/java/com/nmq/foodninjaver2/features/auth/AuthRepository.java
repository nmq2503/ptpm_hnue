package com.nmq.foodninjaver2.features.auth;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.nmq.foodninjaver2.core.modelBase.UserModel;
import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.dataBase.DataBaseSchema;

public class AuthRepository {
    private final DataBaseHelper dbHelper;

    public AuthRepository(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public UserModel loginUser(String email) {
        UserModel user = null;

        // Câu truy vấn để lấy dữ liệu từ bảng USER và ROLE dựa trên email
        String query = "SELECT u.user_id, u.user_name, u.first_name, u.last_name, u.email, u.password, " +
                "u.phone_number, u.address, u.date_of_birth, u.url_image_profile, u.create_at, u.update_at, " +
                "r.role_id, r.role_name " +
                "FROM USER u " +
                "JOIN ROLE r ON u.role_id = r.role_id " +
                "WHERE u.email = ?";

        Cursor cursor = dbHelper.executeQuery(query, new String[]{email});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                user = new UserModel();

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
            } else {
                Log.d("Database", "No user found with the given email.");
            }
            cursor.close();
        } else {
            Log.e("Database", "Cursor is null. Query execution failed.");
        }

        return user;
    }

    public int getUserIdByEmail(String email) {
        int userId = 0; // Giá trị mặc định nếu không tìm thấy
        String query = "SELECT user_id FROM USER WHERE email = ?";

        Cursor cursor = dbHelper.executeQuery(query, new String[]{email});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                userId = cursor.getInt(0); // Lấy giá trị cột user_id (chỉ số 0)
            } else {
                Log.d("Database", "No user found with the given email.");
            }
            cursor.close();
        } else {
            Log.e("Database", "Cursor is null. Query execution failed.");
        }

        return userId;
    }


    // Thêm tài khoản người dùng mới vào CSDL
    public boolean insertUser(String userName, String firstName, String lastName, String email,
                              String password, String phoneNumber, String urlImageProfile) {

        // Kiểm tra các trường bắt buộc
        if (userName == null || userName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            return false; // Thất bại nếu thiếu thông tin bắt buộc
        }

        // Câu lệnh SQL với các tham số dạng placeholder (?)
        String sql = "INSERT INTO USER (user_name, first_name, last_name, email, " +
                "password, phone_number, url_image_profile, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, 3)";

        // Danh sách các giá trị bind
        Object[] bindArgs = new Object[] {
                userName,
                firstName,
                lastName,
                email,
                password,
                phoneNumber,
                urlImageProfile
        };

        // Gọi hàm executeNonQuery để thực thi câu lệnh
        return dbHelper.executeNonQuery(sql, bindArgs);
    }

    // Kiểm tra nếu email đã tồn tại
    public boolean checkEmail(String email) {
        // Câu lệnh SQL truy vấn
        String query = "SELECT 1 FROM " + DataBaseHelper.TABLE_USER + " WHERE " + DataBaseSchema.UserTable.COLUMN_EMAIL + " = ?";

        // Thực hiện truy vấn bằng executeQuery
        Cursor cursor = dbHelper.executeQuery(query, new String[]{email});
        try {
            // Kiểm tra nếu Cursor trả về dữ liệu
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close(); // Đóng Cursor sau khi sử dụng
            }
        }
    }

    // Kiểm tra nếu email và password đúng
    public boolean checkLogin(String email, String password) {
        String query = "SELECT 1 FROM " + DataBaseHelper.TABLE_USER + " WHERE " + DataBaseSchema.UserTable.COLUMN_EMAIL + " = ? AND " + DataBaseSchema.UserTable.COLUMN_PASSWORD + " = ?";
        Cursor cursor = dbHelper.executeQuery(query, new String[]{email, password});

        boolean valid = (cursor.getCount() > 0);
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return valid;
    }
}
