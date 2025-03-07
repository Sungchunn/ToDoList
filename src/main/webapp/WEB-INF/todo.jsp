<%@ page import="java.util.List" %>
<%@ page import="io.muzoo.ssc.webapp.model.Todo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Todo List</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
        .todo-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center mb-4">Welcome, <%= session.getAttribute("username") %></h1>

    <button class="btn btn-primary mb-4" data-bs-toggle="modal" data-bs-target="#addTaskModal">Add Task</button>

    <ul class="list-group">
        <%
            List<Todo> todos = (List<Todo>) request.getAttribute("todos");
            if (todos != null && !todos.isEmpty()) {
                for (Todo todo : todos) {
        %>
        <li class="todo-item" style="background-color: <%= todo.isCompleted() ? "#d4edda" : "#f8d7da" %>;">
            <div>
                <strong><%= todo.getTask() %></strong><br>
                <span class="badge bg-<%= todo.isCompleted() ? "success" : "secondary" %>">
                    <%= todo.isCompleted() ? "Completed" : "Pending" %>
                </span>
            </div>
            <div>
                <form action="todo" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input type="hidden" name="action" value="toggle">
                    <button type="submit" class="btn <%= todo.isCompleted() ? "btn-secondary" : "btn-success" %> btn-sm">
                        <%= todo.isCompleted() ? "Unmark as Completed" : "Mark as Completed" %>
                    </button>
                </form>
                <form action="todo" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
                <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editTaskModal"
                        onclick="setEditTask('<%= todo.getId() %>', '<%= todo.getTask() %>')">Edit</button>
            </div>
        </li>
        <%
            }
        } else {
        %>
        <li class="list-group-item text-center">No tasks available.</li>
        <%
            }
        %>
    </ul>
</div>

<!-- Add Task Modal -->
<div class="modal fade" id="addTaskModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Task</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="todo" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label class="form-label">Task</label>
                        <input type="text" class="form-control" name="task" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Task</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Edit Task Modal -->
<div class="modal fade" id="editTaskModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Task</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="todo" method="post">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" id="editTaskId" name="id">
                    <div class="mb-3">
                        <label class="form-label">Task</label>
                        <input type="text" class="form-control" id="editTaskInput" name="task" required>
                    </div>
                    <button type="submit" class="btn btn-warning">Save Changes</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function setEditTask(id, task) {
        document.getElementById('editTaskId').value = id;
        document.getElementById('editTaskInput').value = task;
    }
</script>
</body>
</html>
