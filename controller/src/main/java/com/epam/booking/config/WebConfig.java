package com.epam.booking.config;

import com.epam.booking.entity.Rental;
import com.epam.booking.entity.Room;
import com.epam.booking.entity.User;
import com.epam.booking.entity.UserSocial;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

import static com.epam.booking.util.ParameterConstant.*;

@EnableWebMvc
@PropertySource("classpath:db.properties")
@ComponentScan( basePackages = "com.epam.booking")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private final Environment env;

    @Autowired
    public WebConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());
        sessionFactory.setAnnotatedClasses(Room.class, User.class, Rental.class, UserSocial.class);
        return sessionFactory;
    }

    @Bean
    public DriverManagerDataSource getDataSource(){
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(env.getProperty(DRIVER));
        source.setUrl(env.getProperty(URL));
        source.setUsername(env.getProperty(USERNAME));
        source.setPassword(env.getProperty(PASSWORD));
        return source;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    private Properties getHibernateProperties() {
        return new Properties() {
            {
                setProperty(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
            }
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/rooms").allowedOrigins("http://localhost:4200");
            }
        };
    }

}
