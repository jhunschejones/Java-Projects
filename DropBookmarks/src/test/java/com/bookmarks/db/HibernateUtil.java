package com.bookmarks.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration()
                    .configure()
                    .buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Error in creating SessionFactory object." + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // ANOTHER POTENTIAL SOLUTION:
//    private static SessionFactory sessionFactory;
//
//    private static SessionFactory buildSessionFactory() {
//
//        Configuration configuration = new Configuration();
//        configuration.configure("hibernate.cfg.xml");
//        System.out.println("Hibernate Configuration loaded");
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties()).build();
//        System.out.println("Hibernate Annotation serviceRegistry created");
//
//        SessionFactory sessionFactory
//                = configuration.buildSessionFactory(serviceRegistry);
//
//        return sessionFactory;
//    }
//
//    public static SessionFactory getSessionFactory() {
//        if(sessionFactory == null) sessionFactory = buildSessionFactory();
//        return sessionFactory;
//    }
}
