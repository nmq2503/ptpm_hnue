package com.nmq.foodninjaver2.core.modelBase;

public class RestaurantModel {
    private int restaurantId;
    private String restaurantName;
    private String address;
    private String email;
    private String phoneNumber;
    private float rating;
    private String openingHours;
    private String closingHours;
    private String urlImageRestaurant;
    private int ownerId;

    private String ownerName;

    // Constructor mặc định
    public RestaurantModel() {
    }

    // Constructor đầy đủ
    public RestaurantModel(int restaurantId, String restaurantName, String address, String email,
                           String phoneNumber, float rating, String openingHours,
                           String closingHours, String urlImageRestaurant, int ownerId, String ownerName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.urlImageRestaurant = urlImageRestaurant;
        this.ownerId = ownerId;

        this.ownerName = ownerName;
    }

    // Getter và Setter
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public String getUrlImageRestaurant() {
        return urlImageRestaurant;
    }

    public void setUrlImageRestaurant(String urlImageRestaurant) {
        this.urlImageRestaurant = urlImageRestaurant;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "RestaurantModel{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", rating=" + rating +
                ", openingHours='" + openingHours + '\'' +
                ", closingHours='" + closingHours + '\'' +
                ", urlImageRestaurant='" + urlImageRestaurant + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
