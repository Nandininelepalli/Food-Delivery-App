<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.tap.model.Cart, com.tap.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart</title>
    <link rel="stylesheet" href="cart.css">
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
    
    <div class="container">
        <div class="cart-header">
           <%
			    Cart cart = (Cart) session.getAttribute("cart");
			    Integer restaurantId = (Integer) session.getAttribute("restaurantId");

			    if (restaurantId == null && cart != null && !cart.getItems().isEmpty()) {
			        CartItem firstItem = cart.getItems().values().stream().findFirst().orElse(null);
			        if (firstItem != null) {
			            restaurantId = firstItem.getRestaurantId();
			            session.setAttribute("restaurantId", restaurantId);
			        }
			    }
			%>

            <a href="menu?restaurantId=<%= restaurantId != null ? restaurantId : "" %>">←</a>
            <h1>Your Cart</h1>
        </div>

        <% if (cart != null && !cart.getItems().isEmpty()) { %>
            <% for (CartItem item : cart.getItems().values()) { %>
            <div class="cart-item">
                <img src="<%= item.getImagePath() %>" alt="<%= item.getName() %>">
                <div class="item-details">
                    <h3><%= item.getName() %></h3>
                    <div class="restaurant-name"><%= item.getRestaurantName() %></div>
                </div>

                <div class="action-section">
                    <div class="quantity-controls">
                        <form action="Cart" method="post" style="display:inline;">
                            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                            <input type="hidden" name="restaurantId" value="<%= restaurantId %>">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                            <button class="quantity-btn" <% if (item.getQuantity() == 1) { %> disabled <% } %>>-</button>
                        </form>

                        <span class="quantity"><%= item.getQuantity() %></span>

                        <form action="Cart" method="post" style="display:inline;">
                            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                            <input type="hidden" name="restaurantId" value="<%= restaurantId %>">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                            <button class="quantity-btn">+</button>
                        </form>
                    </div>

                    <div class="price">₹<%= item.getPrice() * item.getQuantity() %></div>

                    <form action="Cart" method="post">
                        <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                        <input type="hidden" name="restaurantId" value="<%= restaurantId %>">
                        <input type="hidden" name="action" value="remove">
                        <button class="remove-btn">🗑️ Remove</button>
                    </form>
                </div>
            </div>
            <% } %>

            <div class="cart-summary">
                <div class="summary-row">
                    <span>Subtotal</span>
                    <span>₹<%= cart.getTotalPrice() %></span>
                </div>
                <div class="summary-row">
                    <span>Delivery Fee</span>
                    <span>₹50</span>
                </div>
                <div class="summary-row">
                    <span>Taxes</span>
                    <span>₹42</span>
                </div>
                <div class="summary-row total-row">
                    <span>Total</span>
                    <span>₹<%= cart.getTotalPrice() + 50 + 42 %></span>
                </div>

                <div class="add-more-items">
                    <% if (restaurantId != null) { %>
                        <a href="menu?restaurantId=<%= restaurantId %>" class="btn">Add More Items</a>
                    <% } else { %>
                        <a href="restaurants.jsp" class="btn add-more-items-btn">Choose a Restaurant</a>
                    <% } %>
                </div>

                <form action="CheckoutServlet" method="post">
    				<button class="checkout-btn">Proceed to Checkout</button>
				</form>

            </div>
        <% } else { %>
            <p class="yourcart">Your cart is empty.</p>
            <div class="add-more-items">
                <% if (restaurantId != null) { %>
                    <a href="menu?restaurantId=<%= restaurantId %>">Add Items</a>
                <% } else { %>
                    <a href="restaurants.jsp">Choose a Restaurant</a>
                <% } %>
            </div>
        <% } %>
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
