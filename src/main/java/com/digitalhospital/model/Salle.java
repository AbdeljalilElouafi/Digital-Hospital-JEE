package com.digitalhospital.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "salles")
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salle")
    private Long idSalle;

    private String nomSalle;

    private Integer capacite;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "salle_creneaux", joinColumns = @JoinColumn(name = "salle_id"))
    @Column(name = "creneau", columnDefinition = "TIMESTAMP")
    private List<LocalDateTime> creneauxOccupes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    public Salle(){

    }

    public Salle(String nomSalle, Integer capacite) {
        this.nomSalle = nomSalle;
        this.capacite = capacite;
    }

    public Long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public List<LocalDateTime> getCreaneauxOccupes() {
        return creneauxOccupes;
    }

    public void setCreaneauxOccupes(List<LocalDateTime> creaneauxOccupes) {
        this.creneauxOccupes = creaneauxOccupes;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public boolean verifierDisponibilite(java.time.LocalDateTime dateTime) {
        if (dateTime == null) return false;
        return !this.creneauxOccupes.contains(dateTime);
    }


    public void reserverCreneau(java.time.LocalDateTime dateTime) {
        if (dateTime == null) throw new IllegalArgumentException("dateTime ne peut pas être null");
        if (!verifierDisponibilite(dateTime)) throw new IllegalStateException("Créneau déjà occupé");
        this.creneauxOccupes.add(dateTime);
    }


    public void libererCreneau(java.time.LocalDateTime dateTime) {
        this.creneauxOccupes.remove(dateTime);
    }

}
