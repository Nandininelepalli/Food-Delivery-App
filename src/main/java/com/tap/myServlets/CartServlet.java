package com.tap.myServlets;

import java.io.IOException;
import com.tap.daoimplementation.MenuDAOImpl;
import com.tap.daoimplementation.RestaurantDAOImpl;
import com.tap.dao.MenuDAO;
import com.tap.dao.RestaurantDAO;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Menu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CartServlet doPost method called");

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String action = req.getParameter("action");

        String itemIdParam = req.getParameter("itemId");
        String restaurantIdParam = req.getParameter("restaurantId");

        if (itemIdParam == null || restaurantIdParam == null || action == null) {
            System.err.println("Error: Missing parameters in request.");
            resp.sendRedirect("error.jsp");
            return;
        }

        int newRestaurantId, itemId;
        try {
            newRestaurantId = Integer.parseInt(restaurantIdParam);
            itemId = Integer.parseInt(itemIdParam);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for restaurantId or itemId.");
            resp.sendRedirect("error.jsp");
            return;
        }

        Integer currentRestaurantId = (Integer) session.getAttribute("restaurantId");

        // If cart is null or switching restaurants, reset cart
        if (cart == null || currentRestaurantId == null || newRestaurantId != currentRestaurantId) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            session.setAttribute("restaurantId", newRestaurantId);
            System.out.println("Cart reset for new restaurant: " + newRestaurantId);
        }

        try {
            switch (action) {
                case "add":
                    addItemToCart(req, cart, newRestaurantId);
                    break;
                case "update":
                    updateCartItem(req, cart);
                    break;
                case "remove":
                    removeItemFromCart(req, cart);
                    break;
                default:
                    System.err.println("Invalid action: " + action);
                    resp.sendRedirect("error.jsp");
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ensure cart is updated in the session before redirecting
        session.setAttribute("cart", cart);
        System.out.println("Cart updated successfully, redirecting to cart.jsp.");
        resp.sendRedirect("cart.jsp");
    }

    private void addItemToCart(HttpServletRequest request, Cart cart, int restaurantId) {
        String itemIdParam = request.getParameter("itemId");
        String quantityParam = request.getParameter("quantity");

        int itemId, quantity = 1;
        try {
            itemId = Integer.parseInt(itemIdParam);
            if (quantityParam != null && !quantityParam.isEmpty()) {
                quantity = Integer.parseInt(quantityParam);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for itemId or quantity.");
            return;
        }

        MenuDAO menuDAO = new MenuDAOImpl();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();

        Menu menuItem = menuDAO.getMenu(itemId);
        if (menuItem == null) {
            System.err.println("Error: Menu item not found for ID: " + itemId);
            return;
        }

        String restaurantName = restaurantDAO.getRestaurantNameById(menuItem.getRestaurantId());

        CartItem cartItem = new CartItem(itemId, menuItem.getRestaurantId(), menuItem.getItemName(), quantity, menuItem.getPrice());
        cartItem.setImagePath(menuItem.getImagePath());
        cartItem.setRestaurantName(restaurantName);

        cart.addItem(cartItem);
        System.out.println("Added item to cart: " + cartItem.getName() + " | Quantity: " + cartItem.getQuantity());
    }

    private void updateCartItem(HttpServletRequest request, Cart cart) {
        String itemIdParam = request.getParameter("itemId");
        String quantityParam = request.getParameter("quantity");

        if (itemIdParam == null || quantityParam == null) {
            System.err.println("Invalid itemId or quantity received.");
            return;
        }

        int itemId, quantity;
        try {
            itemId = Integer.parseInt(itemIdParam);
            quantity = Integer.parseInt(quantityParam);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for itemId or quantity.");
            return;
        }

        cart.updateItem(itemId, quantity);
        System.out.println("Updated cart item: " + itemId + " | New Quantity: " + quantity);
    }

    private void removeItemFromCart(HttpServletRequest request, Cart cart) {
        String itemIdParam = request.getParameter("itemId");

        if (itemIdParam == null) {
            System.err.println("Invalid itemId received.");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdParam);
        } catch (NumberFormatException e) {
            System.err.println("Invalid itemId format.");
            return;
        }

        cart.removeItem(itemId);
        System.out.println("Removed item from cart: " + itemId);
    }
}
