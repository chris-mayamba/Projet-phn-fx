package org.example.Data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stock implements Serializable {
    @Expose private List<ProduitQuantite> produits;

    public Stock() {
        produits = new ArrayList<>();
    }

    public void ajouterProduit(ProduitElectro produit, int quantite) {
        produit.setStock(this); // Lien bidirectionnel

        for (ProduitQuantite pq : produits) {
            if (pq.getProduit().equals(produit)) {
                pq.setQuantite(pq.getQuantite() + quantite);
                System.out.println(quantite + " unités de " + produit.getNom() + " ajoutées au stock.");
                return;
            }
        }

        produits.add(new ProduitQuantite(produit, quantite));
        System.out.println(quantite + " unités de " + produit.getNom() + " ajoutées au stock.");
    }

    public void retirerProduit(ProduitElectro produit) {
        for (ProduitQuantite pq : produits) {
            if (pq.getProduit().equals(produit)) {
                if (pq.getQuantite() > 0) {
                    pq.setQuantite(0);
                    System.out.println("Produit retiré du stock : " + produit.getNom());
                } else {
                    System.out.println("Aucun stock disponible pour ce produit.");
                }
                return;
            }
        }

        System.out.println("Produit non trouvé dans le stock.");
    }

    public void afficherStock() {
        System.out.println("=== Stock actuel ===");
        if (produits.isEmpty()) {
            System.out.println("Aucun produit en stock.");
            return;
        }

        for (ProduitQuantite pq : produits) {
            System.out.println(pq.getProduit().getNom() + " - " + pq.getQuantite() + " unités");
        }
    }

    public Stock prepareForSerialization() {
        Stock copy = new Stock();
        for (ProduitQuantite pq : this.produits) {
            ProduitElectro p = pq.getProduit();
            p.setStock(null); // Rompre la référence circulaire
            copy.ajouterProduit(p, pq.getQuantite());
        }
        return copy;
    }

    public int getQuantiteTotale() {
        return produits.stream().mapToInt(ProduitQuantite::getQuantite).sum();
    }

    public List<ProduitQuantite> getProduits() {
        return produits;
    }
}
