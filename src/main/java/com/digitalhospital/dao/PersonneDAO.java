package com.digitalhospital.dao;

import com.digitalhospital.model.Personne;
import jakarta.persistence.EntityManager;
import com.digitalhospital.util.JPAUtil;

public class PersonneDAO extends GenericDAO<Personne> {
    public PersonneDAO() {
        super(Personne.class);
    }

    public Personne findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Personne p WHERE p.email = :email", Personne.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public boolean existsByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(p) FROM Personne p WHERE p.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
