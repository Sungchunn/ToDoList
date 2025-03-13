<<<<<<< HEAD
=======
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


>>>>>>> 12d3b58 (update to use postgreSQL to deploy on Heroku)
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<<<<<<< HEAD
    <!-- Google Font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Quicksand:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            min-height: 100vh;
            background: url("/assets/login_image.jpg") no-repeat center center fixed;
            background-size: cover;
            font-family: 'Quicksand', sans-serif;
            color: #333;
        }

        .register-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 2rem;
        }

        .register-box {
            background-color: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(2px);
            border-radius: 12px;
            padding: 2rem;
            max-width: 450px;
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
=======
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
>>>>>>> 12d3b58 (update to use postgreSQL to deploy on Heroku)
        }
    </style>
</head>
<body>
<<<<<<< HEAD
<div class="register-container">
    <div class="register-box">
=======
<div class="container d-flex justify-content-center align-items-center register-container">
    <div class="register-box shadow">
>>>>>>> 12d3b58 (update to use postgreSQL to deploy on Heroku)
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
<<<<<<< HEAD
            <button type="submit" class="btn btn-primary w-100 mt-2">
=======
            <button type="submit" class="btn btn-primary w-100">
>>>>>>> 12d3b58 (update to use postgreSQL to deploy on Heroku)
                Register
            </button>
        </form>

        <p class="mt-3 text-center">
<<<<<<< HEAD
            <a href="/login" class="text-decoration-none" style="color: #E0B084;">
=======
            <a href="/login" class="text-decoration-none">
>>>>>>> 12d3b58 (update to use postgreSQL to deploy on Heroku)
                Already have an account? Login here
            </a>
        </p>
    </div>
</div>

<<<<<<< HEAD
<!-- Bootstrap JS -->
=======
<!-- Bootstrap JS (optional if you need certain interactive components) -->
>>>>>>> 12d3b58 (update to use postgreSQL to deploy on Heroku)
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>