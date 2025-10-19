package com.digitalhospital.dao;

import com.digitalhospital.model.Consultation;
import com.digitalhospital.model.Docteur;
import com.digitalhospital.model.Patient;
import com.digitalhospital.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultationDAO extends GenericDAO<Consultation> {
    public ConsultationDAO() {
        super(Consultation.class);
    }
    EntityManager entityManager = JPAUtil.getEntityManager();



    public List<Consultation> findByPatient(Patient patient) {
        return entityManager.createQuery(
                        "SELECT c FROM Consultation c WHERE c.patient = :p", Consultation.class)
                .setParameter("p", patient)
                .getResultList();
    }

    public List<Consultation> findByDocteurId(Long idDocteur) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Consultation c WHERE c.patient.idPersonne = :id", Consultation.class)
                    .setParameter("id", idDocteur)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean isCreneauOccupe(LocalDate date, LocalTime heure) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(c) FROM Consultation c WHERE c.date = :date AND c.heure = :heure", Long.class)
                    .setParameter("date", date)
                    .setParameter("heure", heure)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public List<Consultation> findByDocteur(Docteur docteur) {
        return entityManager.createQuery(
                        "SELECT c FROM Consultation c WHERE c.docteur = :d", Consultation.class)
                .setParameter("d", docteur)
                .getResultList();
    }

}
