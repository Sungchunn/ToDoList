package io.muzoo.ssc.webapp;

import io.muzoo.ssc.assignment.tracker.SscAssignment;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;

public class Webapp extends SscAssignment {

    public static void main(String[] args) throws Exception{
        TomcatEnvironment.init();
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(TomcatEnvironment.getWorkDir().getAbsolutePath());
        tomcat.setPort(8082);
        tomcat.getConnector();

        SecurityService securityService = new SecurityService();
        securityService.setUserService(UserService.getInstance());

        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        Context ctx;
        try {
            ctx = tomcat.addWebapp("", TomcatEnvironment.getDocBase().getAbsolutePath());
            servletRouter.init(ctx);

            ErrorPage error404Page = new ErrorPage();
            error404Page.setErrorCode(404);
            error404Page.setLocation("/WEB-INF/error404.jsp");
            ctx.addErrorPage(error404Page);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
        }
    }
}
