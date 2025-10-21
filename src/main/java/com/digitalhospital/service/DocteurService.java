package com.digitalhospital.service;

import com.digitalhospital.dao.DocteurDAO;
import com.digitalhospital.model.Docteur;
import java.util.List;

public class DocteurService {
    private final DocteurDAO docteurDAO = new DocteurDAO();

    public Docteur findById(Long id) {
        return docteurDAO.findById(id);
    }

    public List<Docteur> findAll() {
        return docteurDAO.findAll();
    }

    public void save(Docteur docteur) {
        docteurDAO.save(docteur);
    }

    public void update(Docteur docteur) {
        docteurDAO.update(docteur);
    }

    public void delete(Long id) {
        Docteur d = docteurDAO.findById(id);
        if (d != null) docteurDAO.delete(d.getIdPersonne());
    }

    public List<Docteur> findByDepartement(Long depId) {
        return docteurDAO.findByDepartement(depId);
    }

}
