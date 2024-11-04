package com.nmq.foodninjaver2.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDB.db";
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT)";

    private static final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USERS);
        onCreate(db);
    }

    // Thêm tài khoản người dùng mới vào CSDL
    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // Trả về true nếu thành công, false nếu thất bại
    }

    // Kiểm tra nếu username đã tồn tại
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});

        boolean exists = (cursor.getCount() > 0);
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return exists;
    }

    // Kiểm tra nếu email đã tồn tại
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});

        boolean exists = (cursor.getCount() > 0);
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return exists;
    }

    // Kiểm tra nếu email và password đúng
    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});

        boolean valid = (cursor.getCount() > 0);
        cursor.close(); // Đóng Cursor sau khi sử dụng
        return valid;
    }

    // Lấy thông tin người dùng sau khi đăng nhập
    public Cursor getUserData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
    }

    // Đóng database khi không còn sử dụng
    @Override
    public synchronized void close() {
        super.close();
    }
}