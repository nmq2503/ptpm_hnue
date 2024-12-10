package com.nmq.foodninjaver2.features.cart;

import java.io.Serializable;

public class FoodCartModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;  // Sử dụng int thay vì String cho id
    private String name;
    private String ingredient;
    private int quantity;
    private double price;
    private String imageUrl;

    // Constructor mặc định
    public FoodCartModel() {}

    public FoodCartModel(int id, String name, String ingredient, double price, int quantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.ingredient = ingredient;
        this.price = Math.max(0, price);
        this.quantity = Math.max(0, quantity);
        this.imageUrl = imageUrl;
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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Math.max(0, price);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Override toString() để dễ dàng in ra thông tin đối tượng
    @Override
    public String toString() {
        return "FoodCartModel{" +
                "id=" + id + // sửa id sang int
                ", name='" + name + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
