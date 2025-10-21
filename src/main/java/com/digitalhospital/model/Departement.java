package com.digitalhospital.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "departements")
public class Departement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departement")
    private Long idDepartement;


    private String nom;


    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Salle> salles = new ArrayList<>();


    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Docteur> docteurs = new ArrayList<>();


    public Departement() {
    }


    public Departement(String nom) {
        this.nom = nom;
    }


    public Long getIdDepartement() {
        return idDepartement;
    }


    public void setIdDepartement(Long idDepartement) {
        this.idDepartement = idDepartement;
    }


    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public List<Salle> getSalles() {
        return salles;
    }


    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }


    public List<Docteur> getDocteurs() {
        return docteurs;
    }


    public void setDocteurs(List<Docteur> docteurs) {
        this.docteurs = docteurs;
    }
}
