package io.muzoo.ssc.webapp.service;

import io.muzoo.ssc.webapp.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String INSERT_USER_SQL = "INSERT INTO tbl_user (username, password, display_name) VALUES (?, ?, ?);";
    private static final String SELECT_USER_SQL = "SELECT * FROM tbl_user WHERE username=?;";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM tbl_user;";
    private static final String DELETE_USER_SQL = "DELETE FROM tbl_user WHERE username=?;";
    private static final String UPDATE_USER_SQL = "UPDATE tbl_user SET display_name = ? WHERE username = ?;";
    private static final String UPDATE_USER_PASSWORD_SQL = "UPDATE tbl_user SET password = ? WHERE username = ?;";


    private static UserService service;

    private DatabaseConnectionService databaseConnectionService;

    private UserService() {
    }

    public static UserService getInstance() {
        if (service == null) {
            service = new UserService();
            service.setDatabaseConnectionService(DatabaseConnectionService.getInstance());
        }
        return service;
    }

    public void setDatabaseConnectionService(DatabaseConnectionService databaseConnectionService) {
        this.databaseConnectionService = databaseConnectionService;
    }

    public void createUser(String username, String password, String displayName) throws UserServiceException {
        try (
                Connection conn = databaseConnectionService.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL);
        ) {
            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, displayName);
            ps.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            // Unique violation in PostgreSQL
            if ("23505".equals(e.getSQLState())) {
                throw new UserServiceException(String.format("Username %s already exists", username));
            } else {
                throw new UserServiceException(e.getMessage());
            }
        }
    }

    public User findByUsername(String username) {
        try (
                Connection conn = databaseConnectionService.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL);
        ) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new User(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("display_name")
            );
        } catch (SQLException throwables) {
            return null;
        }
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection conn = databaseConnectionService.getConnection();
                PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS_SQL);
        ) {
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                users.add(
                        new User(
                                resultSet.getLong("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("display_name"))
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public boolean deleteUserByUsername(String username) throws UserServiceException {
        try (
                Connection conn = databaseConnectionService.getConnection();
                PreparedStatement ps = conn.prepareStatement(DELETE_USER_SQL);
        ) {
            ps.setString(1, username);
            int deleteCount = ps.executeUpdate();
            conn.commit();
            return deleteCount > 0;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public void updateUserByUsername(String username, String displayName) throws UserServiceException {
        try (
                Connection conn = databaseConnectionService.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL);
        ) {
            ps.setString(1, displayName);
            ps.setString(2, username);

            ps.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public void changePassword(String username, String newPassword) throws UserServiceException {
        try (
                Connection conn = databaseConnectionService.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_USER_PASSWORD_SQL);
        ) {
            ps.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            ps.setString(2, username);

            ps.executeUpdate();
            conn.commit();
        } catch (SQLException throwables) {
            throw new UserServiceException(throwables.getMessage());
        }
    }

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        try {
            userService.createUser("admin10", "1234","Admin");
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }
}
