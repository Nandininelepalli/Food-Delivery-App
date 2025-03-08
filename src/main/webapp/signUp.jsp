<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="stylesheet" href="signUp.css">
</head>
<body>
    <div class="container">
        <div class="signup-box">
            <h1>Sign Up</h1>
            <p>Create an account to explore amazing menus and offers!</p>

            <%-- Display error message if user already exists --%>
            <% String errorMessage = request.getParameter("error"); 
                if (errorMessage != null) { %>
                <p style="color: red;"><%= errorMessage %></p>
            <% } %>

            <form action="SignUpServlet" method="post">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" placeholder="Enter your full name" required>

                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="Choose a username" required>

                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Enter your email" required>

                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter your password" required>

                <label for="confirm-password">Confirm Password</label>
                <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your password" required>

                <button type="submit">Sign Up</button>
            </form>

            <p class="signup-link">Already have an account? <a href="signin.jsp">Sign In</a></p>
        </div>
    </div>
</body>
</html>
