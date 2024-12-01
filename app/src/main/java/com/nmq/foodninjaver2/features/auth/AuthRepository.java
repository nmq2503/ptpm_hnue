package com.nmq.foodninjaver2.features.auth;

import android.content.Context;
import android.database.Cursor;

import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.dataBase.DataBaseSchema;

public class AuthRepository {
    private final DataBaseHelper dbHelper;

    public AuthRepository(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    // Thêm tài khoản người dùng mới vào CSDL
    public boolean insertUser(String userName, String email, String password) {

        // Kiểm tra các trường bắt buộc
        if (userName == null || userName.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            return false; // Thất bại nếu thiếu thông tin bắt buộc
        }

        // Câu lệnh SQL với các tham số dạng placeholder (?)
        String sql = "INSERT INTO USER (user_name, email, password, role_id) VALUES (?, ?, ?, 3)";

        // Danh sách các giá trị bind
        Object[] bindArgs = new Object[] { userName, email, password };

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
