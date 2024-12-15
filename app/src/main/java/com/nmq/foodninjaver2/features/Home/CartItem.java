package com.nmq.foodninjaver2.features.Home;

public class CartItem {
    private String name;
    private int quantity;
    private double price;
    private int imageResId;  // Resource ID for the image

    public CartItem(String name, int quantity, double price, int imageResId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
