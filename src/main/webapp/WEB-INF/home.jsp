<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
    <title>Users list</title>
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
            <a class="navbar-brand">SSC - Login Webapp</a>
            <a class="btn btn-light pull-right" type="button" href="/logout"><i class="fa fa-sign-out"></i> &nbsp; Logout</a>
        </div>
    </nav>
    <div class="row">
        <div class="col-12">
            <h3 class="my-4">Welcome, ${username}</h3>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
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
        </div>
    </div>
    <div class="row mb-4">
        <div class="col-12">
            <a class="btn btn-success px-4" type="button" href="/user/create">
                <i class="fa fa-user"></i> &nbsp;&nbsp; New User
            </a>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th class="py-3">Id</th>
                    <th class="py-3">Username</th>
                    <th class="py-3">Display Name</th>
                    <th class="py-3">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var = "user" items="${users}">
                    <tr>
                        <td class="py-3">${user.id}</td>
                        <td class="py-3">${user.username}</td>
                        <td class="py-3">${user.displayName}</td>
                        <td class="align-middle">
                            <a class="btn btn-warning btn-sm" type="button" href="/user/edit?username=${user.username}"><i class="fa fa-pencil"></i></a>
                            <a class="btn btn-info btn-sm" type="button" href="/user/password?username=${user.username}"><i class="fa fa-key"></i></a>
                            <c:if test="${currentUser.username != user.username}">
                                <button
                                        class="btn btn-danger btn-sm"
                                        type="button" href="/user/delete?username=${user.username}"
                                        data-bs-toggle="modal"
                                        data-bs-target="#delete-modal-${user.id}">
                                    <i class="fa fa-trash"></i>
                                </button>

                                <div class="modal fade" id="delete-modal-${user.id}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Confirm deleting user</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body my-4">
                                                Do you want to delete user <b>${user.displayName}(${user.username})</b>?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <a class="btn btn-danger" href="/user/delete?username=${user.username}">
                                                    <i class="fa fa-trash"></i> &nbsp; Delete
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>


