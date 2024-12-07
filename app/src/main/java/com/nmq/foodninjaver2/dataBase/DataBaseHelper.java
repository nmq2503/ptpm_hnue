package com.nmq.foodninjaver2.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    // Tên và phiên bản cơ sở dữ liệu
    private static final String DATABASE_NAME = "FoodNinja.db";
    private static final int DATABASE_VERSION = 2;

    // Tên bảng
    public static final String TABLE_ROLE = "ROLE";
    public static final String TABLE_USER = "USER";
    public static final String TABLE_RESTAURANT = "RESTAURANT";
    public static final String TABLE_MENU_ITEM = "MENU_ITEM";
    public static final String TABLE_ORDERS = "ORDERS";
    public static final String TABLE_ORDER_ITEM = "ORDER_ITEM";
    public static final String TABLE_FAVORITE = "FAVORITE";

    // Bảng ROLE: Lưu thông tin vai trò
    private static final String CREATE_ROLE_TABLE = "CREATE TABLE ROLE (" +
            "role_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "role_name TEXT NOT NULL UNIQUE," + // Tên vai trò (e.g., Admin, Chủ nhà hàng, Khách hàng)
            "description TEXT" +                // Mô tả vai trò
            ");";

    // Câu lệnh tạo bảng USER
    private static final String CREATE_USER_TABLE = "CREATE TABLE USER (" +
            "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_name TEXT," +
            "first_name TEXT," +
            "last_name TEXT," +
            "email TEXT," +
            "password TEXT," +
            "phone_number TEXT," +
            "address TEXT," +
            "date_of_birth DATETIME," +
            "url_image_profile TEXT," +
            "create_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "update_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "role_id INTEGER NOT NULL," +
            "FOREIGN KEY (role_id) REFERENCES ROLE(role_id)" +
            ");";

    // Câu lệnh tạo bảng RESTAURANT
    private static final String CREATE_RESTAURANT_TABLE = "CREATE TABLE RESTAURANT (" +
            "restaurant_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "restaurant_name TEXT NOT NULL," +
            "address TEXT," +
            "email TEXT," +
            "phone_number TEXT," +
            "rating REAL," +
            "opening_hours TEXT," +
            "closing_hours TEXT," +
            "url_image_restaurant TEXT," +
            "owner_id INTEGER NOT NULL," +
            "FOREIGN KEY (owner_id) REFERENCES USER(user_id) ON DELETE CASCADE" +
            ");";

    // Câu lệnh tạo bảng MENU_ITEM
    private static final String CREATE_MENU_ITEM_TABLE = "CREATE TABLE MENU_ITEM (" +
            "item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "restaurant_id INTEGER NOT NULL," +
            "name TEXT NOT NULL," +
            "description TEXT," +
            "price REAL NOT NULL," +
            "category TEXT," +
            "available BOOLEAN DEFAULT 1," +
            "url_image_item TEXT," +
            "FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT(restaurant_id) ON DELETE CASCADE" +
            ");";

    // Câu lệnh tạo bảng ORDERS
    private static final String CREATE_ORDERS_TABLE = "CREATE TABLE ORDERS (" +
            "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "restaurant_id INTEGER NOT NULL," +
            "order_time DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "status TEXT," +
            "total_amount REAL," +
            "delivery_address TEXT," +
            "payment_method TEXT," +
            "FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE," +
            "FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT(restaurant_id) ON DELETE CASCADE" +
            ");";

    // Câu lệnh tạo bảng ORDER_ITEM
    private static final String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE ORDER_ITEM (" +
            "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "order_id INTEGER NOT NULL," +
            "menu_item_id INTEGER NOT NULL," +
            "quantity INTEGER NOT NULL," +
            "item_price REAL NOT NULL," +
            "FOREIGN KEY (order_id) REFERENCES ORDERS(order_id) ON DELETE CASCADE," +
            "FOREIGN KEY (menu_item_id) REFERENCES MENU_ITEM(item_id) ON DELETE CASCADE" +
            ");";

    // Câu lệnh tạo bảng FAVORITE
    private static final String CREATE_FAVORITE_TABLE = "CREATE TABLE FAVORITE (" +
            "favorite_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user_id INTEGER NOT NULL," +
            "menu_item_id INTEGER NOT NULL," +
            "added_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE," +
            "FOREIGN KEY (menu_item_id) REFERENCES MENU_ITEM(item_id) ON DELETE CASCADE" +
            ");";

    // Constructor
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo các bảng
        db.execSQL(CREATE_ROLE_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_MENU_ITEM_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_ORDER_ITEM_TABLE);
        db.execSQL(CREATE_FAVORITE_TABLE);

        db.execSQL("PRAGMA foreign_keys = ON;");

        // Thêm dữ liệu giả vào các bảng
        insertFakeData(db);
    }

    private void insertFakeData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO ROLE (role_name, description) VALUES " +
                "('Admin', 'Quản trị hệ thống')," +
                "('Chủ nhà hàng', 'Người sở hữu nhà hàng')," +
                "('Khách hàng', 'Người sử dụng dịch vụ');");

        // Dữ liệu giả cho bảng USER
        db.execSQL("INSERT INTO USER (user_name, first_name, last_name, email, password, phone_number, address, date_of_birth, url_image_profile, role_id) VALUES " +
                "('admin', 'Nguyen', 'Quang', 'quanggg2503@gmail.com', '12345678', '123456789', '123 Main St', '1990-01-01', 'url_to_image1', 1)," +
                "('owner1', 'Nguyen', 'Quang', 'owner@gmail.com', '12345678', '123456789', '123 Main St', '1990-01-01', 'url_to_image1', 2)," +
                "('user1', 'John', 'Doe', 'john.doe@example.com', 'password123', '123456789', '123 Main St', '1990-01-01', 'url_to_image1', 3)," +
                "('user2', 'Jane', 'Smith', 'jane.smith@example.com', 'password456', '987654321', '456 Elm St', '1992-02-02', 'url_to_image2', 3);");

        // Dữ liệu giả cho bảng RESTAURANT
        db.execSQL("INSERT INTO RESTAURANT (restaurant_name, address, email, phone_number, rating, opening_hours, closing_hours, url_image_restaurant, owner_id) VALUES " +
                "('Pizza Palace', '789 Oak St', 'contact@pizzapalace.com', '111222333', 4.5, '10:00', '22:00', 'url_to_restaurant1', 2)," +
                "('Burger Barn', '101 Maple St', 'contact@burgerbarn.com', '444555666', 4.2, '11:00', '23:00', 'url_to_restaurant2', 2);");

        // Dữ liệu giả cho bảng MENU_ITEM
        db.execSQL("INSERT INTO MENU_ITEM (restaurant_id, name, description, price, category, available, url_image_item) VALUES " +
                "(1, 'Margherita Pizza', 'Classic cheese and tomato pizza', 8.99, 'Pizza', 1, 'url_to_item1')," +
                "(1, 'Pepperoni Pizza', 'Cheese pizza with pepperoni slices', 10.99, 'Pizza', 1, 'url_to_item2')," +
                "(2, 'Cheeseburger', 'Beef burger with cheese', 6.99, 'Burger', 1, 'url_to_item3');");

        // Dữ liệu giả cho bảng ORDERS
        db.execSQL("INSERT INTO ORDERS (user_id, restaurant_id, status, total_amount, delivery_address, payment_method) VALUES " +
                "(1, 1, 'Pending', 18.99, '123 Main St', 'Credit Card')," +
                "(2, 2, 'Completed', 6.99, '456 Elm St', 'Cash');");

        // Dữ liệu giả cho bảng ORDER_ITEM
        db.execSQL("INSERT INTO ORDER_ITEM (order_id, menu_item_id, quantity, item_price) VALUES " +
                "(1, 1, 1, 8.99)," +
                "(1, 2, 1, 10.99)," +
                "(2, 3, 1, 6.99);");

        // Dữ liệu giả cho bảng FAVORITE
        db.execSQL("INSERT INTO FAVORITE (user_id, menu_item_id) VALUES " +
                "(1, 1)," +
                "(2, 3);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu cơ sở dữ liệu được nâng cấp
        if (oldVersion < 2) { // Nếu phiên bản thấp hơn 2, thực hiện đổi tên bảng
            db.execSQL("ALTER TABLE USER RENAME TO NEW_USER;");
        }

        // Nếu phiên bản cao hơn, làm các thay đổi khác
        db.execSQL("DROP TABLE IF EXISTS RESTAURANT");
        db.execSQL("DROP TABLE IF EXISTS MENU_ITEM");
        db.execSQL("DROP TABLE IF EXISTS ORDERS");
        db.execSQL("DROP TABLE IF EXISTS ORDER_ITEM");
        db.execSQL("DROP TABLE IF EXISTS FAVORITE");

        // Tạo lại các bảng
        onCreate(db);
    }

    // Truy vấn không trả về dữ liệu (như INSERT, UPDATE, DELETE)
    public boolean executeNonQuery(String sql, Object[] bindArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL(sql, bindArgs);
            return true; // Thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Lỗi
        } finally {
            db.close();
        }
    }

    // Truy vấn trả về dữ liệu (SELECT)
    public Cursor executeQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, selectionArgs);
        } catch (SQLException e) {
            e.printStackTrace();
            // Nếu có lỗi, trả về null
            return null;
        }
        return cursor;
    }

    public Cursor getCartItems(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT ORDER_ITEM.order_item_id, MENU_ITEM.name, MENU_ITEM.price, " +
                    "ORDER_ITEM.quantity, (MENU_ITEM.price * ORDER_ITEM.quantity) AS total_price, " +
                    "MENU_ITEM.url_image_item " +
                    "FROM ORDER_ITEM " +
                    "INNER JOIN MENU_ITEM ON ORDER_ITEM.menu_item_id = MENU_ITEM.item_id " +
                    "WHERE ORDER_ITEM.order_id = ?;";

            // Truyền `orderId` để lấy các món thuộc đơn hàng này
            cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursor;
    }

}