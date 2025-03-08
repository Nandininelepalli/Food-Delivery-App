package com.tap.myServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tap.utility.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String username = request.getParameter("username"); // Get username
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String role = "user"; // Default role for new users

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("signUp.jsp?error=Passwords do not match!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check if email or username already exists
            String checkUserQuery = "SELECT * FROM user WHERE email=? OR username=?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {
                checkStmt.setString(1, email);
                checkStmt.setString(2, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    response.sendRedirect("signUp.jsp?error=User already exists!");
                    return;
                }
            }

            // Insert new user with username and role
            String insertQuery = "INSERT INTO user (name, username, email, password, role) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, username);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, role);
                stmt.executeUpdate();
            }

            response.sendRedirect("signin.jsp?message=Account created successfully. Please log in.");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("signUp.jsp?error=Something went wrong!");
        }
    }
}
