<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Address</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 600px;
            margin: auto;
        }
        .heading {
            text-align: center;
            color: #5b837e;
        }
        .address-box {
            border: 1px solid #5b837e;
            border-radius: 8px;
            padding: 10px;
            margin-bottom: 10px;
            background: #f8f8f8;
        }
        .form-group {
            margin-bottom: 10px;
        }
        .input-box {
            width: 100%;
            padding: 8px;
            border: 1px solid #5b837e;
            border-radius: 5px;
            font-size: 16px;
        }
        .button {
            width: 100%;
            padding: 10px;
            background: #5b837e;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="heading">Checkout</h2>
    
    <h3 style="color: #5b837e;">Delivery Address</h3>

    <%
        // Retrieve or initialize the address list in session
        List<String> addresses = (List<String>) session.getAttribute("addresses");
        if (addresses == null) {
            addresses = new ArrayList<>();
            session.setAttribute("addresses", addresses);
        }

        // Handling form submission
        String newAddress = request.getParameter("newAddress");
        if (newAddress != null && !newAddress.trim().isEmpty()) {
            addresses.add("<strong>Other</strong><br>" + newAddress);
            session.setAttribute("addresses", addresses); // Update session with new address
            response.sendRedirect("address.jsp"); // Refresh page to display updated list
            return;
        }
    %>

    <!-- Display Addresses -->
    <%
        if (!addresses.isEmpty()) {
            for (String address : addresses) {
    %>
        <div class="address-box">
            <%= address %>
        </div>
    <%
            }
        } else {
    %>
        <p>No addresses added yet.</p>
    <%
        }
    %>

    <!-- Add New Address Form -->
    <form method="post">
        <div class="form-group">
            <textarea name="newAddress" class="input-box" placeholder="Enter new address..."></textarea>
        </div>
        <button type="submit" class="button">Save Address</button>
    </form>

</div>

</body>
</html>
