<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Restaurant, jakarta.servlet.http.HttpSession" %>

<%
    // Check if the user is logged in
    HttpSession sessionObj = request.getSession(false);
    String userEmail = (sessionObj != null) ? (String) sessionObj.getAttribute("userEmail") : null;
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Hub</title>
    <link rel="stylesheet" href="restaurant.css">
</head>
<body>

<header class="header">
    <div class="logo">
        <h1>QuickSmile Eats</h1>
    </div>
    <nav class="nav-links">
        <a href="Restaurant">Home</a>
        <a href="#">Menu</a>

        <% if (userEmail != null) { %>
            <a href="LogoutServlet" class="logout-btn">Logout</a>
            <span class="user-email">Welcome, <%= userEmail %>!</span>
        <% } else { %>
            <a href="signin.jsp" class="sign-in-btn">Sign In</a>
        <% } %>
    </nav>
</header>

<section class="hero-section">
    <div class="hero-content">
        <h2>Discover Amazing Restaurants</h2>
        <div class="search-bar-container">
            <input type="text" class="search-bar" placeholder="Search for a restaurant...">
            <button class="search-button">Search</button>
        </div>
    </div>
</section>

<section class="restaurants-heading">
    <h2>Restaurants Near You</h2>
</section>

<div class="restaurant-grid">
    <%
        List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
        if (restaurants != null) {
            for (Restaurant r : restaurants) {
    %>
        <div class="restaurant-card">
            <%
                if (userEmail == null) { 
                    // If not logged in, redirect to sign-in page with restaurantId
            %>
                    <a href="signin.jsp?redirect=menu?restaurantId=<%= r.getRestaurantId() %>" style="text-decoration:none;color:inherit">
            <%
                } else { 
                    // If logged in, directly go to the menu page
            %>
                    <a href="menu?restaurantId=<%= r.getRestaurantId() %>" style="text-decoration:none;color:inherit">
            <%
                }
            %>
                <div class="card-image-container">
                    <img src="<%= r.getImagePath() %>" alt="<%= r.getName() %>">
                </div>
                <div class="card-details">
                    <h2><%= r.getName() %></h2>
                    <div class="card-info">
                        <span><%= r.getCuisineType() %> Cuisine</span>
                    </div>
                    <div class="card-rating">
                        <span>★ <%= r.getRating() %></span>
                        <span>• 20-<%= r.getEta() %> min</span>
                    </div>
                    <p><%= r.getAddress() %></p>
                </div>
            </a>
        </div>
    <%
            }
        } else {
    %>
        <p>No restaurants available.</p>
    <%
        }
    %>
</div>

<footer class="footer">
    <div class="footer-content">
        <div class="footer-section">
            <h3>About QuickSmile Eats</h3>
            <p>Discover the best restaurants in your area with QuickSmile Eats.</p>
        </div>
        <div class="footer-section">
            <h3>Quick Links</h3>
            <ul>
                <li><a href="#">About Us</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="#">Privacy Policy</a></li>
                <li><a href="#">Terms of Service</a></li>
            </ul>
        </div>
        <div class="footer-section">
            <h3>Connect With Us</h3>
            <div class="social-links">
                <a href="#" class="social-link">
                    <img src="https://cdn-icons-png.flaticon.com/128/733/733547.png" alt="Facebook">
                    Facebook
                </a>
                <a href="#" class="social-link">
                    <img src="https://cdn-icons-png.flaticon.com/128/733/733579.png" alt="Twitter">
                    Twitter
                </a>
                <a href="#" class="social-link">
                    <img src="https://cdn-icons-png.flaticon.com/128/2111/2111463.png" alt="Instagram">
                    Instagram
                </a>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <p>&copy; 2024 QuickSmile Eats. All rights reserved.</p>
    </div>
</footer>

</body>
</html>
