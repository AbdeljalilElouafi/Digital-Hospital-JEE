package com.digitalhospital.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends Personne {

    private Double poids;
    private Double taille;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Consultation> consultations = new ArrayList<>();

    public Patient() {
        super();
    }

    public Patient(String nom, String prenom, String email, String motDePasse, Double poids, Double taille) {
        super(nom, prenom, email, motDePasse);
        this.poids = poids;
        this.taille = taille;
    }

    public Double getPoids() { return poids; }
    public void setPoids(Double poids) { this.poids = poids; }

    public Double getTaille() { return taille; }
    public void setTaille(Double taille) { this.taille = taille; }

    public List<Consultation> getConsultations() { return consultations; }
    public void setConsultations(List<Consultation> consultations) { this.consultations = consultations; }

    public List<Consultation> consulterHistorique() { return consultations; }

    public void reserverConsultation(Consultation c) {
        c.setPatient(this);
        consultations.add(c);
    }
}
