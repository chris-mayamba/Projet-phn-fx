package org.example.projet_phn_fx.Data;

public class Ventes {
    private String NomCl;
    private String NomProd;
    private int Quantite;
    private int PrixU;
    private ProduitElectro produit;

    public Ventes(String nomCl, String nomProd, int quantite, int prixU, Stock stock) {
        this.NomCl = nomCl;
        this.NomProd = nomProd;
        this.Quantite = quantite;
        this.PrixU = prixU;

        ProduitQuantite produitQuantite = null;

        // Recherche du produit par son nom dans le stock
        for (ProduitQuantite pq : stock.getProduits()) {
            if (pq.getProduit().getNom().equalsIgnoreCase(nomProd)) {
                produitQuantite = pq;
                break;
            }
        }

        if (produitQuantite != null) {
            this.produit = produitQuantite.getProduit();

            // Vérifie si la quantité est suffisante
            if (produitQuantite.getQuantite() >= quantite) {
                produitQuantite.setQuantite(produitQuantite.getQuantite() - quantite);
                System.out.println("Produit " + produit.getNom() + " vendu à " + NomCl + " (" + quantite + " unités).");
            } else {
                System.out.println("Quantité insuffisante en stock pour " + produit.getNom() + ".");
            }
        } else {
            System.out.println("Produit " + nomProd + " non trouvé dans le stock.");
        }
    }

    public String getNomCl() {
        return NomCl;
    }

    public String getNomProd() {
        return NomProd;
    }

    public int getQuantite() {
        return Quantite;
    }

    public int getPrixU() {
        return PrixU;
    }

    public ProduitElectro getProduit() {
        return produit;
    }

    @Override
    public String toString() {
        return "Ventes{" +
                "NomCl='" + NomCl + '\'' +
                ", NomProd='" + NomProd + '\'' +
                ", Quantite=" + Quantite +
                ", PrixU=" + PrixU +
                '}';
    }
}
