package com.epam.booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import static com.epam.booking.util.ParameterConstant.*;

@Configuration
public class WebServletConfiguration implements WebApplicationInitializer {

    public void onStartup(ServletContext context) throws ServletException {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfig.class);
        webContext.setServletContext(context);
        ServletRegistration.Dynamic servlet = context.addServlet(DISPATCHER, new DispatcherServlet(webContext));
        servlet.setLoadOnStartup(1);
    }
}
