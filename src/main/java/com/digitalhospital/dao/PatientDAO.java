package com.digitalhospital.dao;

import com.digitalhospital.model.Patient;

public class PatientDAO extends GenericDAO<Patient>{
    public PatientDAO() {
        super(Patient.class);
    }
}
