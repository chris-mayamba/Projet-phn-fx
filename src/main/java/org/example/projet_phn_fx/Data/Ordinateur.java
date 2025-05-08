package org.example.Data;



public class Ordinateur extends ProduitElectro {
    private String GPU;
    private String typeOrdinateur;

    public Ordinateur(int ID, String nom, String fabricant, long prix, String ram, String rom, String OS, String CPU, String GPU, String typeOrdinateur) {
        super(ID, nom, fabricant, prix, ram, rom, OS, CPU);
        this.GPU = GPU;
        this.typeOrdinateur = typeOrdinateur;
    }

    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }

    public String getTypeOrdinateur() {
        return typeOrdinateur;
    }

    public void setTypeOrdinateur(String typeOrdinateur) {
        this.typeOrdinateur = typeOrdinateur;
    }
}

