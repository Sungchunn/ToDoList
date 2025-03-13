package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class CreateTodoServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/create";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            forwardToCreatePage(request, response);
            clearSessionMessages(request);
        } else {
            redirectToLogin(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            handleUserCreation(request, response);
        } else {
            redirectToLogin(response);
        }
    }

    private void handleUserCreation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringUtils.trim(request.getParameter("username"));
        String displayName = StringUtils.trim(request.getParameter("displayName"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("cpassword");

        String errorMessage = validateUserCreation(username, displayName, password, confirmPassword);

        if (errorMessage != null) {
            setErrorAttributes(request, errorMessage);
            setRequestAttributes(request, username, displayName, password, confirmPassword);
            forwardToCreatePage(request, response);
        } else {
            createUserAndRedirect(request, response, username, displayName, password);
        }

        clearSessionMessages(request);
    }

    private String validateUserCreation(String username, String displayName, String password, String confirmPassword) {
        UserService userService = UserService.getInstance();

        if (userService.findByUsername(username) != null) {
            return String.format("Username %s has already been taken.", username);
        }
        if (StringUtils.isBlank(displayName)) {
            return "Display Name cannot be blank.";
        }
        if (StringUtils.isBlank(password)) {
            return "Password cannot be blank.";
        }
        if (!StringUtils.equals(password, confirmPassword)) {
            return "Confirm Password mismatches.";
        }
        return null;
    }

    private void createUserAndRedirect(HttpServletRequest request, HttpServletResponse response, String username, String displayName, String password) throws IOException, ServletException {
        try {
            UserService userService = UserService.getInstance();
            userService.createUser(username, password, displayName);
            setSuccessAttributes(request, username);
            response.sendRedirect("/");
        } catch (Exception e) {
            setErrorAttributes(request, e.getMessage());
            setRequestAttributes(request, username, displayName, password, null);
            forwardToCreatePage(request, response);
        }
    }

    private void setRequestAttributes(HttpServletRequest request, String username, String displayName, String password, String confirmPassword) {
        request.setAttribute("username", username);
        request.setAttribute("displayName", displayName);
        request.setAttribute("password", password);
        request.setAttribute("cpassword", confirmPassword);
    }

    private void setErrorAttributes(HttpServletRequest request, String message) {
        request.getSession().setAttribute("hasError", true);
        request.getSession().setAttribute("message", message);
    }

    private void setSuccessAttributes(HttpServletRequest request, String username) {
        request.getSession().setAttribute("hasError", false);
        request.getSession().setAttribute("message", String.format("User %s has been created successfully.", username));
    }

    private void forwardToCreatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/create.jsp");
        rd.include(request, response);
    }

    private void clearSessionMessages(HttpServletRequest request) {
        request.getSession().removeAttribute("hasError");
        request.getSession().removeAttribute("message");
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }
}
