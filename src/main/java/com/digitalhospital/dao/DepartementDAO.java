package com.digitalhospital.dao;

import com.digitalhospital.model.Departement;
import com.digitalhospital.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class DepartementDAO extends GenericDAO<Departement> {
    public DepartementDAO() {
        super(Departement.class);
    }

    public Departement findByName(String nom) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }
}

