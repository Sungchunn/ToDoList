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

public class EditTodoServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/edit";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            handleEditUserForm(request, response);
        } else {
            redirectToLogin(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            handle(request, response);
        } else {
            redirectToLogin(response);
        }
    }

    private void handleEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringUtils.trim(request.getParameter("username"));
        UserService userService = UserService.getInstance();
        User user = userService.findByUsername(username);

        if (user != null) {
            setUserAttributes(request, user);
        }

        forwardToEditPage(request, response);
        clearSessionMessages(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = StringUtils.trim(request.getParameter("username"));
        String displayName = StringUtils.trim(request.getParameter("displayName"));

        UserService userService = UserService.getInstance();
        User user = userService.findByUsername(username);

        String errorMessage = validateUserUpdate(user, displayName);

        if (errorMessage != null) {
            setErrorAttributes(request, errorMessage);
            setRequestAttributes(request, username, displayName);
            forwardToEditPage(request, response);
        } else {
            try {
                userService.updateUserByUsername(username, displayName);
                setSuccessAttributes(request, username);
                response.sendRedirect("/");
            } catch (Exception e) {
                setErrorAttributes(request, e.getMessage());
                setRequestAttributes(request, username, displayName);
                forwardToEditPage(request, response);
            }
        }

        clearSessionMessages(request);
    }

    private String validateUserUpdate(User user, String displayName) {
        if (user == null) {
            return String.format("User %s does not exist.", displayName);
        }
        if (StringUtils.isBlank(displayName)) {
            return "Display Name cannot be blank.";
        }
        return null;
    }

    private void setUserAttributes(HttpServletRequest request, User user) {
        request.setAttribute("user", user);
        request.setAttribute("username", user.getUsername());
        request.setAttribute("displayName", user.getDisplayName());
    }

    private void setRequestAttributes(HttpServletRequest request, String username, String displayName) {
        request.setAttribute("username", username);
        request.setAttribute("displayName", displayName);
    }

    private void setErrorAttributes(HttpServletRequest request, String message) {
        request.getSession().setAttribute("hasError", true);
        request.getSession().setAttribute("message", message);
    }

    private void setSuccessAttributes(HttpServletRequest request, String username) {
        request.getSession().setAttribute("hasError", false);
        request.getSession().setAttribute("message", String.format("User %s has been updated successfully.", username));
    }

    private void forwardToEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/edit.jsp");
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

