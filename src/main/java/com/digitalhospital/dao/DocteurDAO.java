package com.digitalhospital.dao;

import com.digitalhospital.model.Docteur;
import com.digitalhospital.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DocteurDAO extends GenericDAO<Docteur>{
    public DocteurDAO() {
        super(Docteur.class);
    }

    public List<Docteur> findByDepartement(Long depId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Docteur d WHERE d.departement.idDepartement = :id", Docteur.class)
                    .setParameter("id", depId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
