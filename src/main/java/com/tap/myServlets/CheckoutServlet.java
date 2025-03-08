package com.tap.myServlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.tap.model.Cart;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // If the cart is empty, redirect to the menu page
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("menu.jsp"); // Redirect to menu
        } else {
            // If cart has items, redirect to checkout
        	response.sendRedirect(request.getContextPath() + "/checkout.jsp");

        }
    }
}

