<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <link rel="stylesheet" href="signIn.css">
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= request.getAttribute("email") %>!</h1>
        <p>You have successfully signed in.</p>
        <a href="index.html">Go to Home</a>
    </div>
</body>
</html>
