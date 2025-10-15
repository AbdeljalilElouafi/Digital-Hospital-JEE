package com.digitalhospital.util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


/**
 * Simple JPA util for obtaining EntityManager in a Tomcat (Servlet container) environment.
 * Usage: EntityManager em = JPAUtil.getEntityManager();
 * Remember to close the EntityManager after use.
 */
public class JPAUtil {


    private static final String PERSISTENCE_UNIT_NAME = "cliniquePU";
    private static EntityManagerFactory emf;


    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }


    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}