package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HomeServlet extends HttpServlet implements Routable {

    private static final String HOME_JSP = "/WEB-INF/home.jsp";
    private static final String LOGIN_PAGE = "/login";
    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/index.jsp";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            prepareHomePage(request);
            forwardToPage(request, response, HOME_JSP);
        } else {
            redirectToLogin(response);
        }
    }

    private void prepareHomePage(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        UserService userService = UserService.getInstance();
        request.setAttribute("currentUser", userService.findByUsername(username));
        request.setAttribute("users", userService.findAll());
        clearSessionMessages(request);
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String pagePath) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(pagePath);
        rd.include(request, response);
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(LOGIN_PAGE);
    }

    private void clearSessionMessages(HttpServletRequest request) {
        request.getSession().removeAttribute("hasError");
        request.getSession().removeAttribute("message");
    }
}

