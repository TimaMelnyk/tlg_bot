package com.configs;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by yaoun on 16.03.2018.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
