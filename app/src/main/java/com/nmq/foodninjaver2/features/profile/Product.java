package com.nmq.foodninjaver2.features.profile;


public class Product {
    private String name;         // Tên sản phẩm
    private String price;        // Giá sản phẩm
    private String description;  // Mô tả sản phẩm
    private int imageResId;      // ID của hình ảnh (Drawable resource)

    // Constructor để khởi tạo đối tượng Product
    public Product(String name, String price, String description, int imageResId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResId = imageResId;
    }

    // Getter và Setter cho các thuộc tính

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

}
