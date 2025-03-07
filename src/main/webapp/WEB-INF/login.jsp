<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            min-height: 100vh;
            /* Background image: adjust path or name as needed */
            background: url("/assets/background.jpg") no-repeat center center fixed;
            background-size: cover;
            font-family: 'Quicksand', sans-serif;
            color: #333;
        }

        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 2rem;
        }

        .login-box {
            background-color: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(2px); /* subtle blur for modern feel (if supported) */
            border-radius: 12px;
            padding: 2rem;
            max-width: 400px;
            width: 100%;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }

        h2 {
            font-weight: 600;
        }

        .btn-primary {
            background-color: #5B8C5A;
            border-color: #5B8C5A;
        }
        .btn-primary:hover {
            background-color: #4c774b;
            border-color: #4c774b;
        }
    </style>
</head>
<body>
<div class="login-container">
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
            <button type="submit" class="btn btn-primary w-100 mt-2">Login</button>
        </form>

        <p class="mt-3 text-center">
            Don't have an account?
            <a href="/register" class="text-decoration-none" style="color: #E0B084;">
                Register here
            </a>
        </p>
    </div>
</div>

<!-- Bootstrap JS (optional for certain components) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>