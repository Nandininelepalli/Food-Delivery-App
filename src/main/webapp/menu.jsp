<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.tap.model.Menu" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <link rel="stylesheet" href="menu.css">
</head>
<body>

    <% String userEmail = (String) session.getAttribute("userEmail"); %> 

    <!-- Header Section -->
    <header class="header">
        <div class="logo">
            <h1>QuickSmile Eats</h1>
        </div>
        <nav class="nav-links">
            <a href="Restaurant">Home</a>
            <a href="#">Menu</a>
            <a href="signin.jsp" class="sign-in-btn">Sign In</a>
        </nav>
    </header>

    <h1 style="text-align:center;">Restaurant Menu</h1>

    <div class="restaurant">
        <div class="menu-row">
           <%
               List<Menu> menuList = (List<Menu>) request.getAttribute("menus");
               String error = (String) request.getAttribute("error");
               
               if (error != null) {
           %>
               <p style="color: red; text-align: center;"><%= error %></p>
           <%
               } else if (menuList != null && !menuList.isEmpty()) {
                   for (Menu m : menuList) {
           %>
            <div class="menu-item">
                <img src="<%= m.getImagePath() %>" alt="Biryani">
                <h3><%= m.getItemName() %></h3>
                <div class="rating">★<%= m.getRatings() %></div>
                <p>Price: ₹<%= m.getPrice() %></p>
                <p>Description: <%= m.getDescription() %></p>

                <form action="Cart" method="post">
                    <input type="hidden" name="restaurantId" value="<%= request.getParameter("restaurantId") %>">
                    <input type="hidden" name="itemId" value="<%= m.getMenuId() %>">
                    <input type="hidden" name="quantity" value="1">
                    <input type="hidden" name="action" value="add">
                    <button type="submit" class="add-button">Add to Cart</button>
                </form>
            </div>
           <%
                   }
               } else {
           %>
               <p>No menu items available.</p>
           <%
               }
           %>
        </div>
    </div>
    
    <!-- Footer Section -->
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
