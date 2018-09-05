package com.epam.booking.config;

import com.epam.booking.entity.Rental;
import com.epam.booking.entity.Room;
import com.epam.booking.entity.User;
import com.epam.booking.entity.UserSocial;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import java.util.Properties;

import static com.epam.booking.util.ParameterConstant.*;

@PropertySource("classpath:db-test.properties")
@ComponentScan( basePackages = "com.epam.booking")
@Configuration
public class TestConfig {
    private final Environment env;

    @Autowired
    public TestConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());
        sessionFactory.setAnnotatedClasses(Room.class, User.class, Rental.class, UserSocial.class);
        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName(env.getProperty(DRIVER));
        source.setUrl(env.getProperty(URL));
        source.setUsername(env.getProperty(USERNAME));
        source.setPassword(env.getProperty(PASSWORD));
        return source;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
        properties.setProperty(HIBERNATE_SHOW_SQL, Boolean.TRUE.toString());
        return properties;
    }

    /**
     * Beans for DBUnit
     */

    @Bean(name = "dbUnitDatabaseConfig")
    public DatabaseConfigBean getDatabaseConfig() {
        DatabaseConfigBean dbConfig = new DatabaseConfigBean();
        dbConfig.setSkipOracleRecyclebinTables(Boolean.TRUE);
        dbConfig.setAllowEmptyFields(Boolean.TRUE);
        return dbConfig;
    }

    @Bean(name = "dbUnitDatabaseConnection")
    public DatabaseDataSourceConnectionFactoryBean getConnectionFactoryBean(){
        DatabaseDataSourceConnectionFactoryBean connectionFactoryBean =
                new DatabaseDataSourceConnectionFactoryBean();
        connectionFactoryBean.setDataSource(getDataSource());
        connectionFactoryBean.setDatabaseConfig(getDatabaseConfig());
        connectionFactoryBean.setSchema(env.getProperty(SCHEMA));
        return connectionFactoryBean;
    }

}

