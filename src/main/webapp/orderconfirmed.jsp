<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.tap.model.CartItem" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="orderconfirmed.css">
</head>
<body>
    <div class="container">
        <div class="header">Order Confirmed!</div>

        <div class="tick-circle">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
            </svg>
        </div>

        <div class="thank-you">Thank you for your order!</div>

        <%
            // Retrieve session attributes safely
            String orderNumber = (String) session.getAttribute("orderNumber");
            String paymentMethod = (String) session.getAttribute("paymentMethod");
            String deliveryAddress = (String) session.getAttribute("deliveryAddress");

            // Retrieve order items safely
            Object orderItemsObj = session.getAttribute("orderItems");
            List<CartItem> orderItems = (orderItemsObj instanceof List) ? (List<CartItem>) orderItemsObj : null;

            // Retrieve and validate total price
            Double totalPriceObj = (Double) session.getAttribute("totalPrice");
            double totalPrice = (totalPriceObj != null) ? totalPriceObj : 0.0;

            // Fix: Convert payment method to readable format
            String displayPaymentMethod;
            switch (paymentMethod) {
                case "net-banking": displayPaymentMethod = "Net Banking"; break;
                case "wallets": displayPaymentMethod = "Wallets"; break;
                case "upi": displayPaymentMethod = "UPI"; break;
                case "cod": displayPaymentMethod = "Cash on Delivery"; break;
                default: displayPaymentMethod = "Payment Method Not Selected";
            }
        %>

        <div class="order-number">Order #<%= orderNumber != null ? orderNumber : "Not Found" %></div>
        <div class="delivery-time">Estimated delivery time: 35-40 minutes</div>

        <div class="info-container">
            <div class="info-section">
                <div class="section-title">Delivery Address</div>
                <div class="address-details">
                    <%= deliveryAddress != null ? deliveryAddress : "No address provided." %>
                </div>
            </div>

            <div class="info-section">
                <div class="section-title">Payment Method</div>
                <div class="payment-details">
                    <strong><%= displayPaymentMethod %></strong>
                    <div class="payment-success">✓ Payment Successful</div>
                </div>
            </div>
        </div>

        <!-- Order Summary Section -->
        <div class="order-summary-title">Order Summary</div>
        <div class="order-summary">
            <% if (orderItems != null && !orderItems.isEmpty()) { %>
                <% for (CartItem item : orderItems) { %>
                    <div class="order-item">
                        <img src="<%= item.getImagePath() != null ? item.getImagePath() : "default.jpg" %>" alt="<%= item.getName() %>">
                        <div class="item-details">
                            <div class="item-name"><%= item.getName() %></div>
                            <div class="item-quantity">Quantity: <%= item.getQuantity() %></div>
                        </div>
                        <div class="item-price">₹<%= item.getPrice() * item.getQuantity() %></div>
                    </div>
                <% } %>
                <div class="summary-row"><div>Subtotal:</div><div>₹<%= totalPrice %></div></div>
                <div class="summary-row"><div>Delivery Fee:</div><div>₹40</div></div>
                <div class="summary-row"><div>Taxes:</div><div>₹72</div></div>
                <div class="total"><div>Total:</div><div>₹<%= totalPrice + 40 + 72 %></div></div>
            <% } else { %>
                <p>No items in your order.</p>
            <% } %>
        </div>

        <div class="buttons">
            <a href="menu?restaurantId=<%= session.getAttribute("restaurantId") %>" class="button secondary-button">Return to Menu</a>
        </div>

    </div>
</body>
</html>
