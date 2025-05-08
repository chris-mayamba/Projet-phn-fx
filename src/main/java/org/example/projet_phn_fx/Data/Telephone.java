package org.example.Data;



public class Telephone extends ProduitElectro {
    private boolean doubleSim;

    public Telephone(int ID, String nom, String fabricant, long prix, String ram, String rom, String OS, String CPU, boolean doubleSim) {
        super(ID, nom, fabricant, prix, ram, rom, OS, CPU);
        this.doubleSim = doubleSim;
    }

    public Telephone(int ID, String nom, String fabricant, long prix, String ram, String rom, String os, String cpu){
        super(ID, nom, fabricant, prix, ram, rom, os, cpu);
    }


    public boolean isDoubleSim() {
        return doubleSim;
    }
}

