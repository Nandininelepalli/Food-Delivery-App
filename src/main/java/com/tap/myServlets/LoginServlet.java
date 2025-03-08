package com.tap.myServlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.tap.dao.UserDAO;
import com.tap.daoimplementation.UserDAOImpl;
import com.tap.model.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Get redirect URL if any
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");

        // Create an instance of UserDAOImpl
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = userDAO.authenticateUser(email, password);

        if (user != null) {
            session.setAttribute("userEmail", user.getEmail());

            // Redirect to stored page or restaurants.jsp
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                session.removeAttribute("redirectAfterLogin"); // Clear after use
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("restaurants.jsp");
            }
        } else {
            response.sendRedirect("signin.jsp?error=Invalid credentials");
        }
    }
}
