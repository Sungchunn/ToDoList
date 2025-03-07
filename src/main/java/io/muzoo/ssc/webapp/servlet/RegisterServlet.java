package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.service.UserService;
import io.muzoo.ssc.webapp.service.UserServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegisterServlet extends HttpServlet implements Routable {

    private UserService userService = UserService.getInstance();

    @Override
    public String getMapping() {
        return "/register"; // Maps to /register URL
    }

    @Override
    public void setSecurityService(io.muzoo.ssc.webapp.service.SecurityService securityService) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String displayName = req.getParameter("display_name"); // ✅ Get display name input
        String password = req.getParameter("password");

        if (username == null || password == null || displayName == null ||
                username.trim().isEmpty() || password.trim().isEmpty() || displayName.trim().isEmpty()) {
            req.setAttribute("error", "All fields are required.");
            req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
            return;
        }

        try {
            userService.createUser(username, password, displayName);
            resp.sendRedirect("/login"); // Redirect to login after successful registration
        } catch (UserServiceException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
