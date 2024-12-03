package com.nmq.foodninjaver2.Model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nmq.foodninjaver2.dataBase.RestaurantDatabaseHelper;

import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {
    private int userId;
    private String userName;
    private String comment;
    private float rating;
    // Loại bỏ List<Comment> nếu không cần bình luận con
    // private List<Comment> comments;

    // Constructor không tham số
    public Comment() {}

    // Constructor đầy đủ tham số
    public Comment(int userId, String userName, String comment, float rating) {
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
    }

    // Getter và Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

}

