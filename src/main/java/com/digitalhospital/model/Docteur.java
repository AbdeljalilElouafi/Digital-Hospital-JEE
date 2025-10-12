package com.digitalhospital.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("DOCTEUR")
public class Docteur extends Personne {

    private String specialite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @OneToMany(mappedBy = "docteur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consultation> planning = new ArrayList<>();

    public Docteur() {
        super();
    }

    public Docteur(String nom, String prenom, String email, String motDePasse, String specialite) {
        super(nom, prenom, email, motDePasse);
        this.specialite = specialite;
    }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }

    public List<Consultation> getPlanning() { return planning; }
    public void setPlanning(List<Consultation> planning) { this.planning = planning; }

    public void validerReservation(Consultation c) {
        if (c != null) c.setStatut(StatutConsultation.VALIDEE);
    }

    public void refuserReservation(Consultation c) {
        if (c != null) c.setStatut(StatutConsultation.ANNULEE);
    }
}
