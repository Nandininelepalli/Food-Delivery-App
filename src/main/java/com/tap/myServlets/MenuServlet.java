package com.tap.myServlets;
import java.io.IOException;
import java.util.List;

import com.tap.daoimplementation.MenuDAOImpl;
import com.tap.model.Menu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("🔹 MenuServlet called");

        HttpSession session = req.getSession();
        String ridParam = req.getParameter("restaurantId");
        Integer restaurantId = null;

        // ✅ Get restaurantId from request or session
        if (ridParam != null && !ridParam.isEmpty()) {
            try {
                restaurantId = Integer.parseInt(ridParam);
                session.setAttribute("restaurantId", restaurantId); // Store in session
            } catch (NumberFormatException e) {
                System.err.println("❌ Invalid restaurantId: " + ridParam);
                resp.sendRedirect("error.jsp");
                return;
            }
        } else {
            restaurantId = (Integer) session.getAttribute("restaurantId"); // Retrieve from session
            if (restaurantId == null) {
                System.err.println("❌ No restaurantId found in request or session.");
                resp.sendRedirect("error.jsp");
                return;
            }
        }

        // ✅ Fetch menu items from database
        MenuDAOImpl daoImpl = new MenuDAOImpl();
        List<Menu> menuList = daoImpl.getMenusByRestaurantId(restaurantId);

        // Debugging logs
        System.out.println("🔹 Fetched " + (menuList != null ? menuList.size() : "0") + " menu items for restaurantId: " + restaurantId);

        if (menuList == null || menuList.isEmpty()) {
            req.setAttribute("error", "No menu items available for this restaurant.");
        } else {
            req.setAttribute("menus", menuList);
        }

        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
    }
}
