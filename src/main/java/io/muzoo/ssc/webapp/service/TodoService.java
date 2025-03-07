package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoService {

    // Updated queries to include description and due_date
    private static final String INSERT_TODO_SQL =
            "INSERT INTO todos (user_id, title, description, due_date, is_completed) " +
                    "VALUES (?, ?, ?, ?, FALSE);";

    private static final String DELETE_TODO_SQL =
            "DELETE FROM todos WHERE id=? AND user_id=?;";

    private static final String SELECT_TODOS_SQL =
            "SELECT * FROM todos WHERE user_id=?;";

    private static final String UPDATE_TODO_SQL =
            "UPDATE todos SET title = ?, description = ?, due_date = ? " +
                    "WHERE id = ? AND user_id = ?;";

    private static final String TOGGLE_TODO_SQL =
            "UPDATE todos SET is_completed = NOT is_completed " +
                    "WHERE id = ? AND user_id = ?;";

    private DatabaseConnectionService databaseConnectionService;

    public TodoService() {
        this.databaseConnectionService = DatabaseConnectionService.getInstance();
    }

    public void addTodo(int userId, String title, String description, Date dueDate) throws SQLException {
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_TODO_SQL)) {
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setDate(4, dueDate);  // or ps.setNull(4, Types.DATE) if no date
            ps.executeUpdate();
            conn.commit();
        }
    }

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

    public List<Todo> getTodos(int userId) throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_TODOS_SQL)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Todo todo = new Todo(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),      // or "task"
                        rs.getString("description"),
                        rs.getDate("due_date"),
                        rs.getBoolean("is_completed")
                );
                todos.add(todo);
            }
        }
        return todos;
    }

    public void toggleComplete(int userId, int todoId) throws SQLException {
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(TOGGLE_TODO_SQL)) {
            ps.setInt(1, todoId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            conn.commit();
        }
    }

    public void updateTodo(int userId, int todoId, String newTitle, String newDescription, Date newDueDate) throws SQLException {
        try (Connection conn = databaseConnectionService.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_TODO_SQL)) {
            ps.setString(1, newTitle);
            ps.setString(2, newDescription);
            ps.setDate(3, newDueDate);
            ps.setInt(4, todoId);
            ps.setInt(5, userId);
            ps.executeUpdate();
            conn.commit();
        }
    }
}