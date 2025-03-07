<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Register</title>--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">--%>
<%--</head>--%>
<%--<body class="container mt-5">--%>

<%--<h2>Register</h2>--%>

<%--<% if (request.getAttribute("error") != null) { %>--%>
<%--<div class="alert alert-danger"><%= request.getAttribute("error") %></div>--%>
<%--<% } %>--%>

<%--<form action="/register" method="post">--%>
<%--    <div class="mb-3">--%>
<%--        <label for="username" class="form-label">Username</label>--%>
<%--        <input type="text" id="username" name="username" class="form-control" required>--%>
<%--    </div>--%>
<%--    <div class="mb-3">--%>
<%--        <label for="display_name" class="form-label">Display Name</label>--%>
<%--        <input type="text" id="display_name" name="display_name" class="form-control" required>--%>
<%--    </div>--%>
<%--    <div class="mb-3">--%>
<%--        <label for="password" class="form-label">Password</label>--%>
<%--        <input type="password" id="password" name="password" class="form-control" required>--%>
<%--    </div>--%>
<%--    <button type="submit" class="btn btn-primary">Register</button>--%>
<%--</form>--%>

<%--<p class="mt-3"><a href="/login">Already have an account? Login here</a></p>--%>

<%--</body>--%>
<%--</html>--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        /* Full-page background */
        body {
            margin: 0;
            padding: 0;
            height: 100vh; /* Ensures full viewport height */
            background: url("/assets/login_image.jpg") no-repeat center center fixed;
            background-size: cover;
        }

        /* Center the form container */
        .register-container {
            height: 100%;
        }

        /* Semi-transparent box for the form */
        .register-box {
            background-color: rgba(255, 255, 255, 0.85); /* 85% opaque white */
            border-radius: 10px;
            padding: 2rem;
            max-width: 450px;
            width: 100%;
        }
    </style>
</head>
<body>
<div class="container d-flex justify-content-center align-items-center register-container">
    <div class="register-box shadow">
        <h2 class="text-center mb-4">Register</h2>

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

        <!-- Registration Form -->
        <form action="/register" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text"
                       id="username"
                       name="username"
                       class="form-control"
                       required>
            </div>
            <div class="mb-3">
                <label for="display_name" class="form-label">Display Name</label>
                <input type="text"
                       id="display_name"
                       name="display_name"
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
            <button type="submit" class="btn btn-primary w-100">
                Register
            </button>
        </form>

        <p class="mt-3 text-center">
            <a href="/login" class="text-decoration-none">
                Already have an account? Login here
            </a>
        </p>
    </div>
</div>

<!-- Bootstrap JS (optional if you need certain interactive components) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>