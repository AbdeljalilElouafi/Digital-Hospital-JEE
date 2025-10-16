package com.digitalhospital.dao;

import com.digitalhospital.model.Salle;

import com.digitalhospital.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SalleDAO extends GenericDAO<Salle> {
    public SalleDAO() {
        super(Salle.class);
    }

    public List<Salle> findByDepartementId(Long idDepartement) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Salle s WHERE s.departement.idDepartement = :id", Salle.class)
                    .setParameter("id", idDepartement)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
