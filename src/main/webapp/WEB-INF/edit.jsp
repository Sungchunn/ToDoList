<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
    <title>Login Webapp</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container my-4">
    <nav class="navbar bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">SSC - Login Webapp</a>
            <a class="btn btn-light pull-right" type="button" href="/logout"><i class="fa fa-sign-out"></i> &nbsp; Logout</a>
        </div>
    </nav>


    <c:if test="${not empty message}">
        <c:choose>
            <c:when test="${hasError}">
                <div class="alert alert-danger" role="alert">
                        ${message}
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-success" role="alert">
                        ${message}
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <div class="row justify-content-md-center">
        <div class="col-sm-12 col-md-6 col-lg-4 mt-5">
            <h3 class="text-center mb-4">Edit User (${username})</h3>
            <p>${error}</p>
            <form action="/user/edit?username=${username}" method="post" autocomplete="off">
                <div class="input-group mb-3 input-group-md">
                    <span class="input-group-text" id="displayName" style="width: 38px"><i class="fa fa-user"></i></span>
                    <input type="text" class="form-control" name="displayName" placeholder="Display Name" aria-label="displayName" aria-describedby="displayName" autocomplete="off" value="${displayName}">
                </div>
                <div class="d-grid gap-2">
                    <button class="btn btn-success" type="submit"><i class="fa fa-save"></i> &nbsp; Save</button>
                </div>
            </form>
        </div>
    </div>

</div>

</body>
</html>
