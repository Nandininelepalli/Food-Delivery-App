package com.tap.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.MenuDAO;
import com.tap.dao.RestaurantDAO;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {
    private static final String GET_MENU_QUERY = "SELECT * FROM `Menu` WHERE `menuId` = ?";
    private static final String GET_ALL_MENUS_QUERY = "SELECT * FROM `Menu`";
    private static final String INSERT_MENU_QUERY = 
        "INSERT INTO `Menu`(`restaurantId`, `itemName`, `description`, `price`, `ratings`, `isAvailable`, `imagePath`) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_MENU_QUERY = 
        "UPDATE `Menu` SET `restaurantId`=?, `itemName`=?, `description`=?, `price`=?, `ratings`=?, `isAvailable`=?, `imagePath`=? WHERE `menuId`=?";
    private static final String DELETE_MENU_QUERY = "DELETE FROM `Menu` WHERE `menuId`=?";
    private static final String GET_MENU_BY_RESTAURANT_ID = "SELECT * FROM `Menu` WHERE `restaurantId`=?";

    private RestaurantDAO restaurantDAO = new RestaurantDAOImpl(); // ✅ Added RestaurantDAO

    @Override
    public void addMenu(Menu menu) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(INSERT_MENU_QUERY)) {
            
            prepareStatement.setInt(1, menu.getRestaurantId());
            prepareStatement.setString(2, menu.getItemName());
            prepareStatement.setString(3, menu.getDescription());
            prepareStatement.setDouble(4, menu.getPrice());
            prepareStatement.setDouble(5, menu.getRatings());
            prepareStatement.setBoolean(6, menu.isAvailable());
            prepareStatement.setString(7, menu.getImagePath());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Menu getMenu(int menuId) {
        Menu menu = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(GET_MENU_QUERY)) {

            prepareStatement.setInt(1, menuId);
            ResultSet res = prepareStatement.executeQuery();

            if (res.next()) {
                menu = extractMenu(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menu;
    }

    @Override
    public void updateMenu(Menu menu) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_MENU_QUERY)) {

            prepareStatement.setInt(1, menu.getRestaurantId());
            prepareStatement.setString(2, menu.getItemName());
            prepareStatement.setString(3, menu.getDescription());
            prepareStatement.setDouble(4, menu.getPrice());
            prepareStatement.setDouble(5, menu.getRatings());
            prepareStatement.setBoolean(6, menu.isAvailable());
            prepareStatement.setString(7, menu.getImagePath());
            prepareStatement.setInt(8, menu.getMenuId()); // ✅ Fix: Set menuId for WHERE clause

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMenu(int menuId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(DELETE_MENU_QUERY)) {

            prepareStatement.setInt(1, menuId);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public List<Menu> getAllMenusByRestaurant(int restaurantId) {
        List<Menu> menuList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MENU_BY_RESTAURANT_ID)) {
            
            statement.setInt(1, restaurantId);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Menu menu = extractMenu(res);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuList;
    }

    private Menu extractMenu(ResultSet res) throws SQLException {
        int menuId = res.getInt("menuId");
        int restaurantId = res.getInt("restaurantId");
        String itemName = res.getString("itemName");
        String description = res.getString("description");
        double price = res.getDouble("price");
        double ratings = res.getDouble("ratings");
        boolean isAvailable = res.getBoolean("isAvailable");
        String imagePath = res.getString("imagePath");

        // ✅ Fetch restaurant name from `RestaurantDAOImpl`
        String restaurantName = restaurantDAO.getRestaurantNameById(restaurantId);

        return new Menu(menuId, restaurantId, restaurantName, itemName, description, price, ratings, isAvailable, imagePath);
    }

    @Override
    public List<Menu> getMenusByRestaurantId(int restaurantId) {
        List<Menu> menuList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MENU_BY_RESTAURANT_ID)) {

            statement.setInt(1, restaurantId);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Menu menu = extractMenu(res);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuList;
    }
}
