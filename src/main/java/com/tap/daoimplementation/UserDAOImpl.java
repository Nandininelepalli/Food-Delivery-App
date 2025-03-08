package com.tap.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class UserDAOImpl implements UserDAO {

    private static final String GET_USER_QUERY = "SELECT * FROM `user` WHERE `UserId`=?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM `user`";
    private static final String UPDATE_USER_QUERY = "UPDATE `user` SET `name`=?, `password`=?, `phone`=?, `address`=?, `role`=? WHERE `UserId`=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM `user` WHERE `UserId`=?";
    private static final String INSERT_USER_QUERY = "INSERT INTO `user` (`name`, `username`, `password`, `email`, `phone`, `address`, `role`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void addUser(User user) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(INSERT_USER_QUERY)) {

            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getUsername());
            prepareStatement.setString(3, user.getPassword());
            prepareStatement.setString(4, user.getEmail());

            // If phone is null or empty, set NULL in the database
            if (user.getPhone() == null || user.getPhone().isEmpty()) {
                prepareStatement.setNull(5, java.sql.Types.VARCHAR);
            } else {
                prepareStatement.setString(5, user.getPhone());
            }

            // If address is null or empty, set NULL
            if (user.getAddress() == null || user.getAddress().isEmpty()) {
                prepareStatement.setNull(6, java.sql.Types.VARCHAR);
            } else {
                prepareStatement.setString(6, user.getAddress());
            }

            // If role is null or empty, set NULL
            if (user.getRole() == null || user.getRole().isEmpty()) {
                prepareStatement.setNull(7, java.sql.Types.VARCHAR);
            } else {
                prepareStatement.setString(7, user.getRole());
            }

            int res = prepareStatement.executeUpdate();
            if (res > 0) {
                System.out.println("User added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User getUser(int userId) {
        User user = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(GET_USER_QUERY)) {

            prepareStatement.setInt(1, userId);
            ResultSet res = prepareStatement.executeQuery();

            if (res.next()) {
                user = extractUser(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {

            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setString(3, user.getPhone());
            prepareStatement.setString(4, user.getAddress());
            prepareStatement.setString(5, user.getRole());
            prepareStatement.setInt(6, user.getUserId());  // Added user ID for WHERE condition

            int res = prepareStatement.executeUpdate();
            if (res > 0) {
                System.out.println("User updated successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(DELETE_USER_QUERY)) {

            prepareStatement.setInt(1, userId);
            int res = prepareStatement.executeUpdate();
            if (res > 0) {
                System.out.println("User deleted successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> usersList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet res = statement.executeQuery(GET_ALL_USERS_QUERY)) {

            while (res.next()) {
                User user = extractUser(res);
                usersList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }
    
    private static final String AUTHENTICATE_USER_QUERY = "SELECT * FROM `user` WHERE `email`=? AND `password`=?";

    
    
    public User authenticateUser(String email, String password) {
        User user = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATE_USER_QUERY)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = extractUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User extractUser(ResultSet res) throws SQLException {
        int userId = res.getInt("UserId");
        String name = res.getString("name");
        String username = res.getString("username");
        String password = res.getString("password");
        String email = res.getString("email");
        String phone = res.getString("phone");
        String address = res.getString("address");
        String role = res.getString("role");

        return new User(userId, name, username, password, email, phone, address, role, null, null);
    }

}
