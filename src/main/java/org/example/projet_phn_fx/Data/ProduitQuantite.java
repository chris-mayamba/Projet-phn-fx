package org.example.Data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ProduitQuantite implements Serializable {
    @Expose private ProduitElectro produit;
    @Expose private int quantite;

    public ProduitQuantite(ProduitElectro produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public ProduitElectro getProduit() {
        return produit;
    }

    public void setProduit(ProduitElectro produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
