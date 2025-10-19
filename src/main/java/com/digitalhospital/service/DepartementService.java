package com.digitalhospital.service;

import com.digitalhospital.dao.DepartementDAO;
import com.digitalhospital.model.Departement;
import java.util.List;

public class DepartementService {
    private final DepartementDAO departementDAO = new DepartementDAO();

    public List<Departement> findAll() {
        return departementDAO.findAll();
    }

    public Departement findById(Long id) {
        return departementDAO.findById(id);
    }

    public void ajouterDepartement(String nom) throws Exception {
        if (departementDAO.findByName(nom) != null) {
            throw new Exception("Un département avec ce nom existe déjà");
        }
        Departement d = new Departement();
        d.setNom(nom);
        departementDAO.save(d);
    }

    public void modifierDepartement(Long id, String nouveauNom) throws Exception {
        Departement d = departementDAO.findById(id);
        if (d == null) throw new Exception("Département introuvable");
        d.setNom(nouveauNom);
        departementDAO.update(d);
    }

    public void supprimerDepartement(Long id) {
        Departement d = departementDAO.findById(id);
        if (d != null) departementDAO.delete(d.getIdDepartement());
    }
}
