package com.digitalhospital.model;


import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Personne {

    public Admin() {
        super();
    }

    public Admin(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }


}

