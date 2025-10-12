package com.digitalhospital.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "consultations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"patient_id", "date_consultation", "heure_consultation"})
})
public class Consultation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultation")
    private Long idConsultation;


    @Column(name = "date_consultation")
    private LocalDate date;


    @Column(name = "heure_consultation")
    private LocalTime heure;


    @Enumerated(EnumType.STRING)
    private StatutConsultation statut;



    private String motif;


    @Lob
    @Column(name = "compte_rendu")
    private String compteRendu;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docteur_id")
    private Docteur docteur;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salle_id")
    private Salle salle;


    public Consultation() {
        this.statut = StatutConsultation.RESERVEE; // default when created via reservation
    }


    public Consultation(LocalDate date, LocalTime heure, String motif) {
        this.date = date;
        this.heure = heure;
        this.motif = motif;
        this.statut = StatutConsultation.RESERVEE;
    }

    public Long getIdConsultation() {
        return idConsultation;
    }

    public void setIdConsultation(Long idConsultation) {
        this.idConsultation = idConsultation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public StatutConsultation getStatut() {
        return statut;
    }

    public void setStatut(StatutConsultation statut) {
        this.statut = statut;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getCompteRendu() {
        return compteRendu;
    }

    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Docteur getDocteur() {
        return docteur;
    }

    public void setDocteur(Docteur docteur) {
        this.docteur = docteur;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    // helpers
    public void definirCompteRendu(String cr) {
        this.compteRendu = cr;
        this.statut = StatutConsultation.TERMINEE;
    }


    public void annuler() {
        this.statut = StatutConsultation.ANNULEE;
    }

}
