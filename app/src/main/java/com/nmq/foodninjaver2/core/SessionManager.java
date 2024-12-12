package com.nmq.foodninjaver2.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.nmq.foodninjaver2.core.modelBase.UserModel;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";

    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_ROLE_ID = "roleId";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Constructor
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu trạng thái đăng nhập
    public void createLoginSession(String email, String userId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }

    // Kiểm tra trạng thái đăng nhập
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    // Lấy thông tin người dùng
    public String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    // Lấy thông tin userId
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    // Xóa trạng thái đăng nhập (đăng xuất)
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
