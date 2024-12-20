package org.hello;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;
@EzySingleton
public class DatabaseUtil {
    private static DatabaseUtil instance;
    private SessionFactory sessionFactory;
    private HikariDataSource dataSource;

    public DatabaseUtil(EzyBeanContext context) {
        try {
            // Load properties từ application.properties
            Properties properties =context.getProperties();

            // Cấu hình HikariCP
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
            hikariConfig.setUsername(properties.getProperty("db.username"));
            hikariConfig.setPassword(properties.getProperty("db.password"));
            hikariConfig.setDriverClassName(properties.getProperty("db.driver"));
            hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("hikari.maximumPoolSize", "10")));
            hikariConfig.setMinimumIdle(Integer.parseInt(properties.getProperty("hikari.minimumIdle", "2")));
            hikariConfig.setConnectionTimeout(Long.parseLong(properties.getProperty("hikari.connectionTimeout", "30000")));
            hikariConfig.setIdleTimeout(Long.parseLong(properties.getProperty("hikari.idleTimeout", "20000")));

            // Tạo HikariDataSource
            this.dataSource = new HikariDataSource(hikariConfig);

            // Cấu hình Hibernate
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"));
            configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"));
            configuration.setProperty("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
            configuration.setProperty("hibernate.format_sql", properties.getProperty("hibernate.format_sql"));
            configuration.getProperties().put(Environment.DATASOURCE, dataSource);

            // Thêm các lớp entity
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(DatabaseAppenderEntity.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.err.println("Fail load application.properties: " + e.getMessage());
        } catch (Throwable ex) {
            System.err.println(" SessionFactory : " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}