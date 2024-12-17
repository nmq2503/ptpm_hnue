package com.nmq.foodninjaver2.Model;

import java.io.Serializable;

public class Comment {
    private String userName;
    private String comment;
    private int rating; // Số sao đánh giá
    private String date;
    private int avatarUrl; // URL hoặc resource ID cho avatar

    public Comment(String userName, String comment, int rating, String date, int avatarUrl) {
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
        this.avatarUrl = avatarUrl;
    }

    // Getters and setters

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(int avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
