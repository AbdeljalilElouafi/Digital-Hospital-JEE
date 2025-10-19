package com.digitalhospital.service;

import com.digitalhospital.dao.ConsultationDAO;
import com.digitalhospital.dao.PatientDAO;
import com.digitalhospital.dao.DocteurDAO;
import com.digitalhospital.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultationService {
    private final ConsultationDAO consultationDAO = new ConsultationDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final DocteurDAO docteurDAO = new DocteurDAO();

    public List<Consultation> findByPatient(Patient patient) {
        return consultationDAO.findByPatient(patient);
    }

    public Consultation findById(Long id) {
        return consultationDAO.findById(id);
    }

    public void reserverConsultation(Long patientId, Long docteurId, LocalDate date, LocalTime heure) throws Exception {
        Patient patient = patientDAO.findById(patientId);
        Docteur docteur = docteurDAO.findById(docteurId);

        if (patient == null) throw new Exception("Patient introuvable !");
        if (docteur == null) throw new Exception("Docteur introuvable !");

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setDocteur(docteur);
        consultation.setDate(date);
        consultation.setHeure(heure);
        consultation.setStatut(StatutConsultation.RESERVEE);

        consultationDAO.save(consultation);
    }

    public void annulerConsultation(Long id) {
        Consultation c = consultationDAO.findById(id);
        if (c != null) {
            c.setStatut(StatutConsultation.ANNULEE);
            consultationDAO.update(c);
        }
    }

    public List<Consultation> findAll() {
        return consultationDAO.findAll();
    }

    public List<Consultation> findByDocteur(Docteur docteur) {
        return consultationDAO.findByDocteur(docteur);
    }

    public void validerConsultation(Long id) {
        Consultation c = consultationDAO.findById(id);
        if (c != null && c.getStatut() == StatutConsultation.RESERVEE) {
            c.setStatut(StatutConsultation.VALIDEE);
            consultationDAO.update(c);
        }
    }

    public void refuserConsultation(Long id) {
        Consultation c = consultationDAO.findById(id);
        if (c != null && c.getStatut() == StatutConsultation.RESERVEE) {
            c.setStatut(StatutConsultation.ANNULEE);
            consultationDAO.update(c);
        }
    }

    public void ajouterCompteRendu(Long id, String cr) {
        Consultation c = consultationDAO.findById(id);
        if (c != null && c.getStatut() == StatutConsultation.VALIDEE) {
            c.setCompteRendu(cr);
            c.setStatut(StatutConsultation.TERMINEE);
            consultationDAO.update(c);
        }
    }

}
