package com.nmq.foodninjaver2.dataBase;

public class DataBaseSchema {
    public static class RoleTable {
        public static final String TABLE_NAME = "ROLE";
        public static final String COLUMN_FAVORITE_ID = "role_id";
        public static final String COLUMN_USER_ID = "role_name";
        public static final String COLUMN_MENU_ITEM_ID = "description";
    }

    // Lớp chứa tên bảng
    public static class UserTable {
        public static final String TABLE_NAME = "USER";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_URL_IMAGE_PROFILE = "url_image_profile";
        public static final String COLUMN_CREATE_AT = "create_at";
        public static final String COLUMN_UPDATE_AT = "update_at";
    }

    public static class RestaurantTable {
        public static final String TABLE_NAME = "RESTAURANT";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_OPENING_HOURS = "opening_hours";
        public static final String COLUMN_CLOSING_HOURS = "closing_hours";
        public static final String COLUMN_URL_IMAGE_RESTAURANT = "url_image_restaurant";
    }

    public static class MenuItemTable {
        public static final String TABLE_NAME = "MENU_ITEM";
        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_AVAILABLE = "available";
        public static final String COLUMN_URL_IMAGE_ITEM = "url_image_item";
    }

    public static class OrdersTable {
        public static final String TABLE_NAME = "ORDERS";
        public static final String COLUMN_ORDER_ID = "order_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_ORDER_TIME = "order_time";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TOTAL_AMOUNT = "total_amount";
        public static final String COLUMN_DELIVERY_ADDRESS = "delivery_address";
        public static final String COLUMN_PAYMENT_METHOD = "payment_method";
    }

    public static class OrderItemTable {
        public static final String TABLE_NAME = "ORDER_ITEM";
        public static final String COLUMN_ORDER_ITEM_ID = "order_item_id";
        public static final String COLUMN_ORDER_ID = "order_id";
        public static final String COLUMN_MENU_ITEM_ID = "menu_item_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_ITEM_PRICE = "item_price";
    }

    public static class FavoriteTable {
        public static final String TABLE_NAME = "FAVORITE";
        public static final String COLUMN_FAVORITE_ID = "favorite_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_MENU_ITEM_ID = "menu_item_id";
        public static final String COLUMN_ADDED_AT = "added_at";
    }
}
