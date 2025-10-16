package com.digitalhospital.service;

import com.digitalhospital.dao.SalleDAO;
import com.digitalhospital.model.Departement;
import com.digitalhospital.model.Salle;
import java.util.List;

public class SalleService {
    private final SalleDAO salleDAO = new SalleDAO();

    public void ajouterSalle(String nomSalle, Integer capacite, Departement departement) throws Exception {
        Salle s = new Salle();
        s.setNomSalle(nomSalle);
        s.setCapacite(capacite);
        s.setDepartement(departement);
        salleDAO.save(s);
    }

    public void modifierSalle(Long id, String nom, Integer capacite) throws Exception {
        Salle s = salleDAO.findById(id);
        s.setNomSalle(nom);
        s.setCapacite(capacite);
        salleDAO.update(s);
    }

    public void supprimerSalle(Long id) {
        salleDAO.delete(id);
    }

    public List<Salle> listerSallesParDepartement(Long idDepartement) {
        return salleDAO.findByDepartementId(idDepartement);
    }

    public List<Salle> listerToutesLesSalles() {
        return salleDAO.findAll();
    }
}
