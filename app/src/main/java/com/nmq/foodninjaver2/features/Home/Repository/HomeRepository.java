package com.nmq.foodninjaver2.features.Home.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nmq.foodninjaver2.dataBase.DataBaseHelper;
import com.nmq.foodninjaver2.features.Home.Model.MenuDomain;
import com.nmq.foodninjaver2.features.Home.Model.RestaurantDomain;

import java.util.ArrayList;

public class HomeRepository {
    DataBaseHelper dataBaseHelper;

    public HomeRepository(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }
    // Lấy danh sách tất cả các món ăn
    public ArrayList<MenuDomain> getAllMenuItems() {
        ArrayList<MenuDomain> menuList = new ArrayList<>();
        SQLiteDatabase db = this.dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MENU_ITEM", null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String pic = cursor.getString(cursor.getColumnIndexOrThrow("url_image_item"));
                double fee = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));

                menuList.add(new MenuDomain(title, pic, fee));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return menuList;
    }

    // Lay du lieu Restaurant
    public ArrayList<RestaurantDomain> getAllRestaurants() {
        ArrayList<RestaurantDomain> restaurantList = new ArrayList<>();
        SQLiteDatabase db = this.dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RESTAURANT", null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("restaurant_name"));
                String pic = cursor.getString(cursor.getColumnIndexOrThrow("url_image_restaurant"));

                restaurantList.add(new RestaurantDomain(title, pic));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return restaurantList;
    }
}
