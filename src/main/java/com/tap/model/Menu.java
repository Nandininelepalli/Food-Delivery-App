package com.tap.model;

public class Menu {
    private int menuId;
    private int restaurantId;
    private String restaurantName; // ✅ Added field
    private String itemName;
    private String description;
    private double price;
    private double ratings;
    private boolean isAvailable;
    private String imagePath;

    public Menu() {
    }

    public Menu(int menuId, int restaurantId, String restaurantName, String itemName, String description, 
                double price, double ratings, boolean isAvailable, String imagePath) {
        this.menuId = menuId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName; // ✅ Initialize restaurant name
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.ratings = ratings;
        this.isAvailable = isAvailable;
        this.imagePath = imagePath;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() { // ✅ Getter for restaurant name
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) { // ✅ Setter for restaurant name
        this.restaurantName = restaurantName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
