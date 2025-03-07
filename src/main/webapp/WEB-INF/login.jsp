

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <style>
        /* Make the background image cover the entire screen */
        body {
            /* Update this path if your background image is located elsewhere */
            background: url("/assets/background.jpg") no-repeat center center fixed;
            background-size: cover;
            height: 100vh; /* ensures body covers full viewport height */
            margin: 0;
            padding: 0;
        }

        /* Center the login form vertically and horizontally */
        .login-container {
            height: 100%;
        }

        /* Semi-transparent background for the login box */
        .login-box {
            background-color: rgba(255, 255, 255, 0.8); /* 0.8 = 80% opaque */
            border-radius: 8px;
            padding: 2rem;
            max-width: 400px;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="container d-flex justify-content-center align-items-center login-container">
    <div class="login-box">
        <h2 class="text-center mb-4">Login</h2>

        <!-- Display error if any -->
        <%
            if (request.getAttribute("error") != null) {
        %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
        <%
            }
        %>

        <!-- Login Form -->
        <form action="/login" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text"
                       id="username"
                       name="username"
                       class="form-control"
                       required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password"
                       id="password"
                       name="password"
                       class="form-control"
                       required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>

        <p class="mt-3 text-center">
            Don't have an account?
            <a href="/register" class="btn btn-success btn-sm">Register</a>
        </p>
    </div>
</div>

<!-- Bootstrap JS (Optional, for certain components) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>