<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.ArrayList, com.tap.model.Cart, com.tap.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Page</title>
    <link rel="stylesheet" href="checkout.css">
    <style>
        .highlight-home { border: 2px solid #578E7E; background-color: #f5fdfb; }
        .highlight-work { border: 2px solid #578E7E; background-color: #eef9ff; }
        .hidden { display: none; }
    </style>
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

    <div class="containers">
        <div class="headers">Checkout</div>

        <% 
            // Fetch cart details from session
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null || cart.getItems().isEmpty()) { 
        %>
            <p class="yourcart">Your cart is empty.</p>
            <div class="add-more-items">
                <a href="menu.jsp" class="btn">Add Items</a>
            </div>
        <% 
            } else { 
                // Get addresses list from session
                List<String> addresses = (List<String>) session.getAttribute("addresses");
                if (addresses == null) {
                    addresses = new ArrayList<>();
                    session.setAttribute("addresses", addresses);
                }

                // Fetch new address from request if submitted
                String newAddress = request.getParameter("newAddress");
                String addressType = request.getParameter("addressType");

                if (newAddress != null && !newAddress.trim().isEmpty() && addressType != null) {
                    String formattedAddress = "<strong>" + addressType + "</strong><br>" + newAddress;
                    addresses.add(formattedAddress);
                    session.setAttribute("addresses", addresses);
                    response.sendRedirect("checkout.jsp"); // Refresh to hide form after saving
                }
        %>

        <!-- Delivery Address Section -->
        <div class="section">
            <h2>Delivery Address</h2>

            <% if (!addresses.isEmpty()) { %>
                <% for (String address : addresses) { %>
                    <div class="address-box <%= address.contains("Home") ? "highlight-home" : "highlight-work" %>">
                        <%= address %>
                    </div>
                <% } %>
            <% } %>

            <!-- Add New Address Button (Visible by Default) -->
            <div class="add-new" id="addNewAddressBtn" onclick="toggleAddressForm()">+ Add New Address</div>

            <!-- Address Input Form (Initially Hidden) -->
            <form method="post" id="addressForm" class="hidden">
                <input type="text" name="newAddress" placeholder="Enter new address" required>
                <select name="addressType">
                    <option value="Home">Home</option>
                    <option value="Work">Work</option>
                </select>
                <button type="submit">Save Address</button>
            </form>
        </div>

        <!-- Order Summary Section -->
        <div class="section">
            <h2>Order Summary</h2>
            <div class="order-summary">
                <% for (CartItem item : cart.getItems().values()) { %>
                    <div class="order-item">
                        <img src="<%= item.getImagePath() %>" alt="<%= item.getName() %>">
                        <div><%= item.getName() %><br>Quantity: <%= item.getQuantity() %></div>
                        <div>₹<%= item.getPrice() * item.getQuantity() %></div>
                    </div>
                <% } %>
                
                <hr>
                <div class="order-item">
                    <div>Subtotal:</div>
                    <div>₹<%= cart.getTotalPrice() %></div>
                </div>
                <div class="order-item">
                    <div>Delivery Fee:</div>
                    <div>₹50</div>
                </div>
                <div class="order-item">
                    <div>Taxes:</div>
                    <div>₹42</div>
                </div>
                <div class="total">Total: ₹<%= cart.getTotalPrice() + 50 + 42 %></div>
            </div>
        </div>

        <!-- Payment Method Section -->
        <div class="section">
            <h2>Payment Method</h2>
            <form action="OrderServlet" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="deliveryAddress" id="selectedAddress">

                <div class="payment-method">
                    <label>
                        <input type="radio" name="payment" value="net-banking" required> Net Banking
                    </label>
                    <label>
                        <input type="radio" name="payment" value="wallets" required> Wallets
                    </label>
                    <label>
                        <input type="radio" name="payment" value="upi" required> UPI
                    </label>
                    <label>
                        <input type="radio" name="payment" value="cod" required> Cash on Delivery
                    </label>
                </div>

                <button class="button" type="submit">Place Order</button>
            </form>
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

    <script>
        function toggleAddressForm() {
            let form = document.getElementById('addressForm');
            let addButton = document.getElementById('addNewAddressBtn');

            form.classList.toggle('hidden');

            if (!form.classList.contains('hidden')) {
                addButton.style.display = 'none'; // Hide "+ Add New Address" when form is visible
            } else {
                addButton.style.display = 'block'; // Show button when form is hidden
            }
        }

        function validateForm() {
            let addressBox = document.querySelector(".address-box");
            if (!addressBox) {
                alert("Please add a delivery address.");
                return false;
            }
            document.getElementById("selectedAddress").value = addressBox.innerHTML;
            return true;
        }
    </script>

</body>
</html>
