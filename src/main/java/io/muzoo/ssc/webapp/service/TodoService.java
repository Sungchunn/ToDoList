package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.Todo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoService {

    // Match your existing columns: "title" and "is_completed" instead of "task" and "completed"
    private static final String INSERT_TODO_SQL =
            "INSERT INTO todos (user_id, title, is_completed) VALUES (?, ?, FALSE);";
    private static final String DELETE_TODO_SQL =
            "DELETE FROM todos WHERE id=? AND user_id=?;";
    private static final String SELECT_TODOS_SQL =
            "SELECT * FROM todos WHERE user_id=?;";

    private DatabaseConnectionService databaseConnectionService;

    public TodoService() {
        this.databaseConnectionService = DatabaseConnectionService.getInstance();
    }

    /**
     * Adds a new Todo item for the given user.
     * @param userId the user who owns this todo
     * @param title the title (task name) of the todo
     */
    public void addTodo(int userId, String title) throws SQLException {
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_TODO_SQL)) {
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.executeUpdate();
            conn.commit();
        }
    }

    /**
     * Deletes a Todo item by its ID and the user who owns it.
     * @param userId the ID of the user
     * @param todoId the ID of the todo
     * @return true if deleted, false if not found
     */
    public boolean deleteTodo(int userId, int todoId) throws SQLException {
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_TODO_SQL)) {
            ps.setInt(1, todoId);
            ps.setInt(2, userId);
            int rows = ps.executeUpdate();
            conn.commit();
            return rows > 0;
        }
    }

    /**
     * Retrieves all Todo items for the given user.
     * @param userId the ID of the user
     * @return a list of Todo objects
     */
    public List<Todo> getTodos(int userId) throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_TODOS_SQL)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                todos.add(new Todo(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getBoolean("is_completed")
                ));
            }
        }
        return todos;
    }

    /**
     * Toggles the "is_completed" status of a Todo item.
     * @param userId the ID of the user
     * @param todoId the ID of the todo
     */
    public void toggleComplete(int userId, int todoId) throws SQLException {
        String sql = "UPDATE todos SET is_completed = NOT is_completed WHERE id = ? AND user_id = ?";
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, todoId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            conn.commit();
        }
    }

    /**
     * Updates the title (task name) of a Todo item.
     * @param userId the ID of the user
     * @param todoId the ID of the todo
     * @param newTitle the new title for the todo
     */
    public void updateTodo(int userId, int todoId, String newTitle) throws SQLException {
        String sql = "UPDATE todos SET title = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newTitle);
            ps.setInt(2, todoId);
            ps.setInt(3, userId);
            ps.executeUpdate();
            conn.commit();
        }
    }
}