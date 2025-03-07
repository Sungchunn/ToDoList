package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.service.SecurityService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class LoginServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forwardToLogin(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (isNotBlank(username) && isNotBlank(password)) {
            processAuthentication(username, password, request, response);
        } else {
            setErrorAttributeAndForwardToLogin("Please fill out all fields", request, response);
        }
    }

    private void processAuthentication(String username, String password, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (securityService.authenticate(username, password, request)) {
            response.sendRedirect("/todo");  // Redirect to todo page
        } else {
            setErrorAttributeAndForwardToLogin("Wrong username or password.", request, response);
        }
    }

    private void setErrorAttributeAndForwardToLogin(String error, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        forwardToLogin(request, response);
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
        rd.include(request, response);
    }

    private boolean isNotBlank(String input) {
        return !StringUtils.isBlank(input);
    }

    @Override
    public String getMapping() {
        return "/login";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

