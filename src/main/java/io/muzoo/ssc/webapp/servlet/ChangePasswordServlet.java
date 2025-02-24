package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.model.User;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/password";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            handlePasswordChangeForm(request, response);
        } else {
            redirectToLogin(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            handleChangePassword(request, response);
        } else {
            redirectToLogin(response);
        }
    }

    private void handlePasswordChangeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringUtils.trim(request.getParameter("username"));
        UserService userService = UserService.getInstance();
        User user = userService.findByUsername(username);

        if (user != null) {
            setRequestAttributes(request, user);
        }

        forwardToPasswordPage(request, response);
        clearSessionMessages(request);
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringUtils.trim(request.getParameter("username"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("cpassword");

        UserService userService = UserService.getInstance();
        User user = userService.findByUsername(username);

        String errorMessage = validatePasswordChange(user, password, confirmPassword);

        if (errorMessage != null) {
            setErrorAttributes(request, errorMessage);
            setRequestAttributes(request, user);
            forwardToPasswordPage(request, response);
        } else {
            changeUserPassword(request, response, username, password);
        }

        clearSessionMessages(request);
    }

    private String validatePasswordChange(User user, String password, String confirmPassword) {
        if (user == null) {
            return String.format("User %s does not exist.", user.getUsername());
        }
        if (StringUtils.isBlank(password)) {
            return "Password cannot be blank.";
        }
        if (!StringUtils.equals(password, confirmPassword)) {
            return "Confirm Password mismatches.";
        }
        return null;
    }

    private void changeUserPassword(HttpServletRequest request, HttpServletResponse response, String username, String password) throws IOException, ServletException {
        try {
            UserService userService = UserService.getInstance();
            userService.changePassword(username, password);
            setSuccessAttributes(request, username);
            response.sendRedirect("/");
        } catch (Exception e) {
            setErrorAttributes(request, e.getMessage());
            forwardToPasswordPage(request, response);
        }
    }

    private void setRequestAttributes(HttpServletRequest request, User user) {
        request.setAttribute("username", user.getUsername());
    }

    private void setErrorAttributes(HttpServletRequest request, String message) {
        request.getSession().setAttribute("hasError", true);
        request.getSession().setAttribute("message", message);
    }

    private void setSuccessAttributes(HttpServletRequest request, String username) {
        request.getSession().setAttribute("hasError", false);
        request.getSession().setAttribute("message", String.format("Password for user %s has been changed successfully.", username));
    }

    private void forwardToPasswordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/password.jsp");
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

