package com.nmq.foodninjaver2.features.cart.Model;

public class Order {
    private String foodId;
    private String foodName;
    private String quantity;
    private String price;
    private String discount;

    // Constructor
    public Order(String foodId, String foodName, String quantity, String price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    // Getter methods
    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }
}
