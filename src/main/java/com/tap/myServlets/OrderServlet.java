package com.tap.myServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.tap.model.Cart;
import com.tap.model.CartItem;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve cart and items
        Cart cart = (Cart) session.getAttribute("cart");
        List<CartItem> orderItems = new ArrayList<>();
        
        if (cart != null) {
            orderItems.addAll(cart.getItems().values()); // Convert Map<Integer, CartItem> to List<CartItem>
        }

        // Store order items in session
        session.setAttribute("orderItems", orderItems);

        // Store total price safely
        double totalPrice = (cart != null) ? cart.getTotalPrice() : 0.0;
        session.setAttribute("totalPrice", totalPrice);

        // Retrieve payment method and delivery address
        String paymentMethod = request.getParameter("payment"); // FIXED: Fetching correct parameter name
        String deliveryAddress = request.getParameter("deliveryAddress");

        // Ensure the selected payment method is stored correctly
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "Not Provided"; // If no payment method is selected
        }

        // Ensure delivery address is not null
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            deliveryAddress = "Not Provided";
        }

        // Store them in session
        session.setAttribute("paymentMethod", paymentMethod);
        session.setAttribute("deliveryAddress", deliveryAddress);

        // Generate order number
        String orderNumber = "ORD" + System.currentTimeMillis();
        session.setAttribute("orderNumber", orderNumber);

        // Clear cart after order
        session.removeAttribute("cart");

        // Redirect to order confirmation page
        response.sendRedirect("orderconfirmed.jsp");
    }
}
