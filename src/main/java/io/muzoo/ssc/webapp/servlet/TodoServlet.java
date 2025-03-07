package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.model.Todo;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.TodoService;
import io.muzoo.ssc.webapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet implements Routable {
    @Override
    public String getMapping() {
        return "/todo";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {

    }

    private TodoService todoService = new TodoService();
    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userIdObj = req.getSession().getAttribute("userId");
        Integer userId = null; // Use Integer (nullable), not int

        if (userIdObj instanceof Long) {
            userId = ((Long) userIdObj).intValue();
        } else if (userIdObj instanceof Integer) {
            userId = (Integer) userIdObj;
        }

        if (userId == null) { // Now this check is valid
            resp.sendRedirect("/login");
            return;
        }

        try {
            List<Todo> todos = todoService.getTodos(userId);
            req.setAttribute("todos", todos);
            req.getRequestDispatcher("/WEB-INF/todo.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userIdObj = req.getSession().getAttribute("userId");
        Integer userId = (userIdObj instanceof Long) ? ((Long) userIdObj).intValue() : (Integer) userIdObj;
        if (userId == null) {
            resp.sendRedirect("/login");
            return;
        }

        String action = req.getParameter("action");
        String idStr = req.getParameter("id");

        try {
            if ("add".equals(action)) {
                // Get new fields
                String task = req.getParameter("task");
                String description = req.getParameter("description");
                String dueDateStr = req.getParameter("dueDate");

                // Convert dueDateStr to java.sql.Date (or null if empty)
                java.sql.Date dueDate = null;
                if (dueDateStr != null && !dueDateStr.isEmpty()) {
                    dueDate = java.sql.Date.valueOf(dueDateStr);
                }

                todoService.addTodo(userId, task, description, dueDate);

            } else if ("delete".equals(action)) {
                int todoId = Integer.parseInt(idStr);
                todoService.deleteTodo(userId, todoId);

            } else if ("toggle".equals(action)) {
                int todoId = Integer.parseInt(idStr);
                todoService.toggleComplete(userId, todoId);

            } else if ("edit".equals(action)) {
                int todoId = Integer.parseInt(idStr);
                String updatedTask = req.getParameter("task");
                String updatedDescription = req.getParameter("description");
                String updatedDueDateStr = req.getParameter("dueDate");

                java.sql.Date updatedDueDate = null;
                if (updatedDueDateStr != null && !updatedDueDateStr.isEmpty()) {
                    updatedDueDate = java.sql.Date.valueOf(updatedDueDateStr);
                }

                todoService.updateTodo(userId, todoId, updatedTask, updatedDescription, updatedDueDate);
            }
            resp.sendRedirect("/todo");

        } catch (NumberFormatException e) {
            throw new ServletException("Invalid id format: " + idStr, e);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}
