package com.tap.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {
    private static final String GET_RESTAURANT_QUERY = "SELECT * FROM `Restaurant` WHERE `restaurantId` = ?";
    private static final String GET_ALL_RESTAURANTS_QUERY = "SELECT * FROM `Restaurant`";
    private static final String INSERT_RESTAURANT_QUERY = 
        "INSERT INTO `Restaurant`(`name`, `address`, `phone`, `rating`, `cuisineType`, `isActive`, `eta`, `adminUserId`, `imagePath`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_RESTAURANT_QUERY = 
        "UPDATE `Restaurant` SET `name`=?, `address`=?, `phone`=?, `rating`=?, `cuisineType`=?, `isActive`=?, `eta`=?, `imagePath`=? WHERE `restaurantId`=?";
    private static final String DELETE_RESTAURANT_QUERY = "DELETE FROM `Restaurant` WHERE `restaurantId`=?";
    private static final String GET_RESTAURANT_NAME_BY_ID_QUERY = "SELECT `name` FROM `Restaurant` WHERE `restaurantId` = ?";

    @Override
    public void addRestaurant(Restaurant restaurant) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(INSERT_RESTAURANT_QUERY)) {

            prepareStatement.setString(1, restaurant.getName());
            prepareStatement.setString(2, restaurant.getAddress());
            prepareStatement.setString(3, restaurant.getPhone());
            prepareStatement.setDouble(4, restaurant.getRating());
            prepareStatement.setString(5, restaurant.getCuisineType());
            prepareStatement.setBoolean(6, restaurant.isActive());
            prepareStatement.setInt(7, restaurant.getEta());
            prepareStatement.setInt(8, restaurant.getAdminUserId());
            prepareStatement.setString(9, restaurant.getImagePath());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Restaurant getRestaurant(int restaurantId) {
        Restaurant restaurant = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(GET_RESTAURANT_QUERY)) {

            prepareStatement.setInt(1, restaurantId);
            ResultSet res = prepareStatement.executeQuery();

            if (res.next()) { // ✅ Fix: Move cursor before accessing data
                restaurant = extractRestaurant(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurant;
    }

    @Override
    public void updateRestaurant(Restaurant restaurant) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_RESTAURANT_QUERY)) {

            prepareStatement.setString(1, restaurant.getName());
            prepareStatement.setString(2, restaurant.getAddress());
            prepareStatement.setString(3, restaurant.getPhone());
            prepareStatement.setDouble(4, restaurant.getRating());
            prepareStatement.setString(5, restaurant.getCuisineType());
            prepareStatement.setBoolean(6, restaurant.isActive());
            prepareStatement.setInt(7, restaurant.getEta());
            prepareStatement.setString(8, restaurant.getImagePath());
            prepareStatement.setInt(9, restaurant.getRestaurantId()); // ✅ Fix: Set restaurantId for WHERE clause

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRestaurant(int restaurantId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(DELETE_RESTAURANT_QUERY)) {

            prepareStatement.setInt(1, restaurantId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet res = statement.executeQuery(GET_ALL_RESTAURANTS_QUERY)) {

            while (res.next()) {
                Restaurant restaurant = extractRestaurant(res);
                restaurantList.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    private Restaurant extractRestaurant(ResultSet res) throws SQLException {
        int restaurantId = res.getInt("restaurantId");
        String name = res.getString("name");
        String address = res.getString("address");
        String phone = res.getString("phone");
        double rating = res.getDouble("rating");
        String cuisineType = res.getString("cuisineType");
        boolean isActive = res.getBoolean("isActive");
        int eta = res.getInt("eta");
        int adminUserId = res.getInt("adminUserId");
        String imagePath = res.getString("imagePath");

        return new Restaurant(restaurantId, name, address, phone, rating, cuisineType, isActive, eta, adminUserId, imagePath);
    }

    @Override
    public String getRestaurantNameById(int restaurantId) {
        String restaurantName = null;
        String query = "SELECT name FROM Restaurant WHERE restaurantId = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, restaurantId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                restaurantName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return restaurantName;
    }

    
}
