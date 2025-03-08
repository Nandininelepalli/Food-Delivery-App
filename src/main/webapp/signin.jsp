<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession();
    if (sessionObj.getAttribute("userEmail") != null) {
        response.sendRedirect("restaurants.jsp");
        return;
    }

    // Get the redirect URL from the request parameter or default to restaurants.jsp
    String redirectAfterLogin = request.getParameter("redirect");
    if (redirectAfterLogin != null) {
        sessionObj.setAttribute("redirectAfterLogin", redirectAfterLogin);
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="signIn.css">
</head>
<body>
    <div class="container">
        <div class="signin-box">
            <h1>Sign In</h1>
            <p>Welcome back! Please sign in to continue.</p>

            <%-- Display error message --%>
            <% String errorMessage = request.getParameter("error"); 
                if (errorMessage != null) { %>
                <p style="color: red;"><%= errorMessage %></p>
            <% } %>

            <form action="LoginServlet" method="POST">
                <input type="hidden" name="redirectUrl" value="<%= redirectAfterLogin %>">

                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>
                
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>
                
                <button type="submit">Sign In</button>
                <p class="signup-link">Don't have an account? <a href="signUp.jsp">Sign Up</a></p>
            </form>
        </div>
    </div>
</body>
</html>
