package org.example.projet_phn_fx.Data;


import com.google.gson.annotations.Expose;
import javafx.beans.property.*;

import java.io.Serializable;

public class ProduitElectro implements Serializable {
    @Expose
    private int ID;
    @Expose
    private String Nom;
    @Expose
    private String Fabricant;
    @Expose
    private double Prix;
    @Expose
    private String Ram;
    @Expose
    private String Rom;
    @Expose
    private String OS;
    @Expose
    private String CPU;
    @Expose
    private String type;
    @Expose
    private String GPU;
    private Stock stock;

    private int quantite;

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ProduitElectro() {
    }

    public ProduitElectro(int ID, String nom, String fabricant, long prix, String ram, String rom, String OS, String CPU) {
        this.ID = ID;
        this.Nom = nom;
        this.Fabricant = fabricant;
        this.Prix = prix;
        this.Ram = ram;
        this.Rom = rom;
        this.OS = OS;
        this.CPU = CPU;
    }

    public ProduitElectro(int ID, String nom, String fabricant, long prix, String ram, String rom, String OS, String CPU, String type) {
        this.ID = ID;
        this.Nom = nom;
        this.Fabricant = fabricant;
        this.Prix = prix;
        this.Ram = ram;
        this.Rom = rom;
        this.OS = OS;
        this.CPU = CPU;
        this.type = type;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public String getFabricant() {
        return Fabricant;
    }

    public void setFabricant(String fabricant) {
        this.Fabricant = fabricant;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        this.Prix = prix;
    }

    public String getRam() {
        return Ram;
    }

    public void setRam(String ram) {
        this.Ram = ram;
    }

    public String getRom() {
        return Rom;
    }

    public void setRom(String rom) {
        this.Rom = rom;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    @Override
    public String toString() {
        return "ProduitElectro{" +
                "ID=" + ID +
                ", Nom='" + Nom + '\'' +
                ", Fabricant='" + Fabricant + '\'' +
                ", Prix=" + Prix +
                ", Ram='" + Ram + '\'' +
                ", Rom='" + Rom + '\'' +
                ", OS='" + OS + '\'' +
                ", GPU='" + GPU + '\'' +
                ", CPU='" + CPU + '\'' +
                ", stock=" + stock +
                '}';
    }

    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }
}


