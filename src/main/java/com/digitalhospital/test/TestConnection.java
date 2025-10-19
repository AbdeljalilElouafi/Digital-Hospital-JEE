package com.digitalhospital.test;


import com.digitalhospital.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConnection {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            User user = new User("khaoula");
            em.persist(user);
            em.getTransaction().commit();

            em.close();
            emf.close();

            System.out.println("Connection successful! User inserted into DB.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed!");
        }
    }
}