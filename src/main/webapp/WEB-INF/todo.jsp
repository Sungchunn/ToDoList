<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="io.muzoo.ssc.webapp.model.Todo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Todo List</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        /* Full-page background */
        body {
            margin: 0;
            padding: 0;
            min-height: 100vh; /* ensures the body covers full viewport height */
            background: url("/assets/ToDoListPage.jpg") no-repeat center center fixed;
            background-size: cover;
        }

        /* Flex container to center or position the main box */
        .todo-container {
            display: flex;
            justify-content: center;
            align-items: flex-start; /* change to 'center' if you want vertical centering */
            min-height: 100vh;
            padding: 2rem;
        }

        /* Semi-transparent box for the todo content */
        .todo-box {
            background-color: rgba(255, 255, 255, 0.85); /* 85% opaque white */
            border-radius: 10px;
            max-width: 1200px;
            width: 100%;
            padding: 2rem;
        }
    </style>
</head>
<body>
<div class="todo-container">
    <div class="todo-box shadow">
        <!-- NAVBAR -->
        <nav class="navbar bg-body-tertiary mb-4">
            <div class="container-fluid">
                <span class="navbar-brand">SSC - Login Webapp</span>
                <a class="btn btn-light" type="button" href="/logout">
                    <i class="fa fa-sign-out"></i> Logout
                </a>
            </div>
        </nav>

        <!-- PAGE HEADING -->
        <div class="row">
            <div class="col">
                <h2 class="mb-4">
                    Welcome, <%= session.getAttribute("username") %>
                </h2>
            </div>
        </div>

        <!-- "ADD TASK" BUTTON -->
        <div class="row mb-4">
            <div class="col">
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addTaskModal">
                    Add Task
                </button>
            </div>
        </div>

        <!-- DISPLAY ANY MESSAGES (OPTIONAL) -->
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

        <!-- TASKS TABLE -->
        <%
            List<Todo> todos = (List<Todo>) request.getAttribute("todos");
        %>
        <div class="row">
            <div class="col">
                <table class="table table-striped table-bordered align-middle">
                    <thead>
                    <tr>
                        <th>Task</th>
                        <th>Description</th>
                        <th>Due Date</th>
                        <th>Status</th>
                        <th style="width: 200px;">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${todos != null && !todos.isEmpty()}">
                        <c:forEach var="todo" items="${todos}">
                            <tr>
                                <td>${todo.task}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty todo.description}">
                                            <em>No description</em>
                                        </c:when>
                                        <c:otherwise>
                                            ${todo.description}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${todo.dueDate != null}">
                                            ${todo.dueDate}
                                        </c:when>
                                        <c:otherwise>
                                            <em>Not set</em>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${todo.completed}">
                                        <span class="badge bg-success">Completed</span>
                                    </c:if>
                                    <c:if test="${!todo.completed}">
                                        <span class="badge bg-warning">Pending</span>
                                    </c:if>
                                </td>
                                <td>
                                    <!-- TOGGLE COMPLETION -->
                                    <form action="/todo" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="toggle">
                                        <input type="hidden" name="id" value="${todo.id}">
                                        <button type="submit" class="btn btn-sm
                                            <c:if test='${todo.completed}'>btn-secondary</c:if>
                                            <c:if test='${!todo.completed}'>btn-success</c:if>">
                                            <c:if test="${todo.completed}">
                                                Unmark
                                            </c:if>
                                            <c:if test="${!todo.completed}">
                                                Mark
                                            </c:if>
                                        </button>
                                    </form>

                                    <!-- EDIT BUTTON (opens modal) -->
                                    <button
                                            type="button"
                                            class="btn btn-warning btn-sm d-inline"
                                            data-bs-toggle="modal"
                                            data-bs-target="#editTaskModal"
                                            onclick="setEditTask('${todo.id}', '${todo.task}',
                                                    '${todo.description != null ? todo.description : ""}',
                                                    '${todo.dueDate != null ? todo.dueDate : ""}')">
                                        Edit
                                    </button>

                                    <!-- DELETE BUTTON -->
                                    <form action="/todo" method="post" class="d-inline">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${todo.id}">
                                        <button type="submit" class="btn btn-danger btn-sm">
                                            Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${todos == null || todos.isEmpty()}">
                        <tr>
                            <td colspan="5" class="text-center">
                                No tasks available.
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div> <!-- .todo-box -->
</div> <!-- .todo-container -->

<!-- ADD TASK MODAL -->
<div class="modal fade" id="addTaskModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Task</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="/todo" method="post">
                    <input type="hidden" name="action" value="add">

                    <!-- Task Title -->
                    <div class="mb-3">
                        <label class="form-label">Task</label>
                        <input type="text" class="form-control" name="task" required>
                    </div>

                    <!-- Description -->
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea class="form-control" name="description" rows="3"></textarea>
                    </div>

                    <!-- Due Date (HTML5 date picker) -->
                    <div class="mb-3">
                        <label class="form-label">Due Date</label>
                        <input type="date" class="form-control" name="dueDate">
                    </div>

                    <button type="submit" class="btn btn-primary">Add Task</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- EDIT TASK MODAL -->
<div class="modal fade" id="editTaskModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Task</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="/todo" method="post">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" id="editTaskId" name="id">

                    <!-- Task Title -->
                    <div class="mb-3">
                        <label class="form-label">Task</label>
                        <input type="text" class="form-control" id="editTaskInput" name="task" required>
                    </div>

                    <!-- Description -->
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea class="form-control" id="editDescriptionInput" name="description" rows="3"></textarea>
                    </div>

                    <!-- Due Date -->
                    <div class="mb-3">
                        <label class="form-label">Due Date</label>
                        <input type="date" class="form-control" id="editDueDateInput" name="dueDate">
                    </div>

                    <button type="submit" class="btn btn-warning">Save Changes</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Pre-fill the edit modal with existing data
    function setEditTask(id, task, description, dueDate) {
        document.getElementById('editTaskId').value = id;
        document.getElementById('editTaskInput').value = task;
        document.getElementById('editDescriptionInput').value = description;
        document.getElementById('editDueDateInput').value = dueDate;
    }
</script>
</body>
</html>