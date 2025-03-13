package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.model.User;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class DeleteTodoServlet extends HttpServlet implements Routable {

    private static final String HOME_PAGE = "/";
    private static final String LOGIN_PAGE = "/login";
    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/delete";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            deleteUser(request);
            response.sendRedirect(HOME_PAGE);
        } else {
            response.sendRedirect(LOGIN_PAGE);
        }
    }

    private void deleteUser(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        UserService userService = UserService.getInstance();
        String deletingUsername = request.getParameter("username");

        try {
            User currentUser = userService.findByUsername(username);
            User deletingUser = userService.findByUsername(deletingUsername);

            if (StringUtils.equals(currentUser.getUsername(), deletingUser.getUsername())) {
                setSessionMessage(request, true, "You cannot delete your own account!");
            } else {
                boolean isDeleted = userService.deleteUserByUsername(deletingUsername);
                setDeletionMessage(request, isDeleted, deletingUsername);
            }
        } catch (Exception e) {
            setSessionMessage(request, true, String.format("Unable to delete user %s.", deletingUsername));
        }
    }

    private void setDeletionMessage(HttpServletRequest request, boolean isDeleted, String username) {
        if (isDeleted) {
            setSessionMessage(request, false, String.format("User %s is deleted successfully.", username));
        } else {
            setSessionMessage(request, true, String.format("Unable to delete user %s.", username));
        }
    }

    private void setSessionMessage(HttpServletRequest request, boolean hasError, String message) {
        request.getSession().setAttribute("hasError", hasError);
        request.getSession().setAttribute("message", message);
    }
}
