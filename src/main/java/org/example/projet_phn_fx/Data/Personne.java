package org.example.projet_phn_fx.Data;


public class Personne {
    private String Nom;
    private String PostNom;
    private String Prenom;
    private String NumTel;
    private String Email;

    public Personne() {
    }

    public Personne(String nom, String postNom, String prenom, String numTel, String email) {
        this.Nom = nom;
        this.PostNom = postNom;
        this.Prenom = prenom;
        this.NumTel = numTel;
        this.Email = email;
    }

    public Personne(String nom) {
        this.Nom = nom;
    }


    public void setNom(String nom) {
        this.Nom = nom;
    }

    public void setPostNom(String postNom) {
        this.PostNom = postNom;
    }

    public void setPrenom(String prenom) {
        this.Prenom = prenom;
    }

    public void setNumTel(String numTel) {
        this.NumTel = numTel;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getNom() {
        return Nom;
    }

    public String getPostNom() {
        return PostNom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public String getNumTel() {
        return NumTel;
    }

    public String getEmail() {
        return Email;
    }

    @Override
    public String toString() {
        return "data.Personne{" +
                "Nom='" + Nom + '\'' +
                ", PostNom='" + PostNom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", NumTel='" + NumTel + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}

