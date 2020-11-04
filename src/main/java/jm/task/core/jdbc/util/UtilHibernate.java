package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UtilHibernate {
    private Properties properties = new Properties();
    private Configuration config = new Configuration();
    private SessionFactory sessionFactory;

    {
        try (FileInputStream fis = new FileInputStream("src\\main\\java\\jm\\task\\core\\jdbc\\util\\config.properties")) {
            properties.load(fis);
            config.setProperties(properties);
            config.addAnnotatedClass(User.class);
        } catch (IOException e) {
            System.out.println("properties getting/setting error, fix it, dirty animal");
            e.printStackTrace(System.out);
        }
    }

    public SessionFactory getSessionFactory() {
        try {
            StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties);
            sessionFactory = config.buildSessionFactory(serviceRegistry.build());
        } catch (Exception e) {
            System.out.println("no sessionFactory for you, fix it. or not");
        }
        return sessionFactory;
    }
}
