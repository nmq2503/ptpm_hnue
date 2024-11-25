package com.nmq.foodninjaver2.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmq.foodninjaver2.Model.Restaurant;

import java.util.ArrayList;
import java.util.List;


    public class RestaurantDatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "restaurant_database.db";
        private static final int DATABASE_VERSION = 1;
        public static final String TABLE_RESTAURANTS = "Restaurants";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_OPEN_HOUR = "open_hour";
        public static final String COLUMN_CLOSE_HOUR = "close_hour";
        public static final String TABLE_COMMENTS = "Comments";
        public static final String COLUMN_COMMENT_ID = "comment_id";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_COMMENT = "comment";

        private static final String CREATE_TABLE_RESTAURANTS =
                "CREATE TABLE " + TABLE_RESTAURANTS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_RATING + " REAL, " +
                        COLUMN_OPEN_HOUR + " TEXT, " +
                        COLUMN_CLOSE_HOUR + " TEXT)";
        private static final String CREATE_TABLE_COMMENTS =
                "CREATE TABLE " + TABLE_COMMENTS + " (" +
                        COLUMN_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RESTAURANT_ID + " INTEGER, " +
                        COLUMN_USER + " TEXT, " +
                        COLUMN_COMMENT + " TEXT, " +
                        "FOREIGN KEY(" + COLUMN_RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANTS + "(" + COLUMN_ID + "))";

        public RestaurantDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Tạo bảng Restaurants và Comments khi cơ sở dữ liệu được khởi tạo
            db.execSQL(CREATE_TABLE_RESTAURANTS);
            db.execSQL(CREATE_TABLE_COMMENTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Xóa bảng cũ nếu cần nâng cấp
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
            onCreate(db);
        }

        // Thêm nhà hàng vào cơ sở dữ liệu
        public void insertRestaurant(String name, String description, float rating) {
            SQLiteDatabase db = this.getWritableDatabase(); // Thay dbHelper bằng this
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_RATING, rating);

            db.insert(TABLE_RESTAURANTS, null, values);
            db.close();
        }

        // Thêm bình luận vào cơ sở dữ liệu
        public void insertComment(int restaurantId, String user, String comment) {
            SQLiteDatabase db = this.getWritableDatabase(); // Thay dbHelper bằng this
            ContentValues values = new ContentValues();
            values.put(COLUMN_RESTAURANT_ID, restaurantId);
            values.put(COLUMN_USER, user);
            values.put(COLUMN_COMMENT, comment);

            db.insert(TABLE_COMMENTS, null, values);
            db.close();
        }

        // Lấy tất cả các nhà hàng
        public List<Restaurant> getAllRestaurants() {
            List<Restaurant> restaurantList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase(); // Thay dbHelper bằng this
            String query = "SELECT * FROM " + TABLE_RESTAURANTS;

            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));

                Restaurant restaurant = new Restaurant(id, name, description, rating);
                restaurantList.add(restaurant);
            }
            cursor.close();
            db.close();
            return restaurantList;
        }

        // Lấy bình luận của nhà hàng theo restaurantId
        public List<String> getCommentsByRestaurantId(int restaurantId) {
            List<String> comments = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase(); // Thay dbHelper bằng this
            String query = "SELECT * FROM " + TABLE_COMMENTS +
                    " WHERE " + COLUMN_RESTAURANT_ID + " = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(restaurantId)});
            while (cursor.moveToNext()) {
                String user = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER));
                String comment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT));
                comments.add(user + ": " + comment);
            }
            cursor.close();
            db.close();
            return comments;
        }
        public Restaurant getRestaurantById(int restaurantId) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_RESTAURANTS + " WHERE " + COLUMN_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(restaurantId)});

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));

                Restaurant restaurant = new Restaurant(restaurantId, name, description, rating);
                cursor.close();
                db.close();
                return restaurant;
            } else {
                cursor.close();
                db.close();
                return null;  // Trả về null nếu không tìm thấy nhà hàng
            }
        }

    }


