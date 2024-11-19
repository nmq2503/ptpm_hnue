package com.nmq.foodninjaver2.Model;

import com.nmq.foodninjaver2.Model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String description;
    private String distance;
    private float rating;
    private List<Product> products;
    private List<com.nmq.foodninjaver2.Model.Comment> comments;
    private String imageUrl;

    // Constructor không tham số
    public Restaurant() {}

    // Constructor đầy đủ tham số
    public Restaurant(int id, String name, String description, List<Product> products, List<com.nmq.foodninjaver2.Model.Comment> comments, String distance, float rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
        this.comments = comments;
        this.distance = distance;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public Restaurant(int id, String name, String description, float rating) {
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<com.nmq.foodninjaver2.Model.Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
