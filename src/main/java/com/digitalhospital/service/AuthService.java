package com.digitalhospital.service;

import com.digitalhospital.dao.PersonneDAO;
import com.digitalhospital.model.*;

public class AuthService {
    private final PersonneDAO personneDAO = new PersonneDAO();

    public Personne login(String email, String motDePasse) throws Exception {
        Personne user = personneDAO.findByEmail(email);
        if (user == null || !user.getMotDePasse().equals(motDePasse)) {
            throw new Exception("Email ou mot de passe incorrect");
        }
        return user;
    }

    public void registerPatient(String nom, String prenom, String email, String motDePasse, Double poids, Double taille) throws Exception {
        if (personneDAO.existsByEmail(email)) {
            throw new Exception("Un compte avec cet email existe déjà");
        }
        Patient p = new Patient(nom, prenom, email, motDePasse, poids, taille);
        personneDAO.save(p);
    }

    public void registerDocteur(String nom, String prenom, String email, String motDePasse, String specialite, Departement d) throws Exception {
        if(personneDAO.existsByEmail(email)) {
            throw new Exception("Un compte avec cet email déja existe!");
        }
        Docteur doc = new Docteur(nom, prenom, email, motDePasse, specialite);
        doc.setDepartement(d);
        personneDAO.save(doc);
    }

}
