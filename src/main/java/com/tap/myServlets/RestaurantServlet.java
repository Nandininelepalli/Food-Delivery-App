package com.tap.myServlets;

import java.io.IOException;
import java.util.List;

import com.tap.daoimplementation.RestaurantDAOImpl;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Restaurant") // This makes sure the servlet loads first when running the project
public class RestaurantServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Homepage");

        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        List<Restaurant> allrestaurants = restaurantDAO.getAllRestaurants();
        req.setAttribute("restaurants", allrestaurants);

        RequestDispatcher rd = req.getRequestDispatcher("Restaurants.jsp"); 
        rd.forward(req, resp);
    }
}
