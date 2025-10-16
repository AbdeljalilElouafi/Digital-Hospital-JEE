package com.digitalhospital.service;

import com.digitalhospital.dao.DepartementDAO;
import com.digitalhospital.model.Departement;

import java.util.List;

public class DepartementService {
    private final DepartementDAO departementDAO = new DepartementDAO();

    public void ajouterDepartement(String nom) throws Exception {
        if(departementDAO.findByName(nom) != null){
            throw new Exception("Un ddepartement avec ce nom déja existe");
        }

        Departement d = new Departement();
        d.setNom(nom);
        departementDAO.save(d);

    }

    public void modifierDepartement(Long id, String nouveauNom) throws Exception {
        Departement d = new Departement();
        if(d == null) {
            throw new Exception("Département introuvaaable!");
        }

        d.setNom(nouveauNom);
        departementDAO.save(d);

    }

    public void supprimerDepartement(Long id){
        departementDAO.delete(id);
    }

    public List<Departement> listerDepartements(){
        return departementDAO.findAll();
    }


}
