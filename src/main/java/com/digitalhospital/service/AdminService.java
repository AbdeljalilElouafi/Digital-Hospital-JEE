package com.digitalhospital.service;

import com.digitalhospital.model.Departement;
import com.digitalhospital.model.Docteur;
import com.digitalhospital.model.Salle;

import java.util.List;

public class AdminService {
    private final DepartementService departementService = new DepartementService();
    private final SalleService salleService = new SalleService();
    private final AuthService authService = new AuthService();


    public void ajouterDepartement(String nom)throws Exception{
        departementService.ajouterDepartement(nom);
    }

    public void ajouterDocteur(String nom, String prenom, String email, String motDePasse,String specialite, Departement d)throws Exception{
        authService.registerDocteur(nom, prenom, email, motDePasse, specialite, d);
    }

    public List<Salle> listerLesSalles(){
        return salleService.listerToutesLesSalles();
    }

}
