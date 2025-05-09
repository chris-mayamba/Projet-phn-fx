package org.example.projet_phn_fx.dao;


import org.example.projet_phn_fx.Data.ProduitElectro;
import org.example.projet_phn_fx.database.IConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitsDAO implements IProduitDAO {
    public IConnectionProvider conn;

    public ProduitsDAO(IConnectionProvider conn) {
        this.conn = conn;
    }

    @Override
    public void addProduits(ProduitElectro produitElectro) {
        String sqlProduit = "INSERT INTO produit (NOM, FABRICANT, prix, RAM, ROM, OS, CPU, TYPE, GPU) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlStock = "INSERT INTO stock (PRODUIT_ID, QUANTITE) VALUES (?, ?)";

        try (Connection connection = this.conn.getConnection();
             PreparedStatement stmtProduit = connection.prepareStatement(sqlProduit, Statement.RETURN_GENERATED_KEYS)) {

            // Ajouter dans la table produit
            stmtProduit.setString(1, produitElectro.getNom());
            stmtProduit.setString(2, produitElectro.getFabricant());
            stmtProduit.setDouble(3, produitElectro.getPrix());
            stmtProduit.setString(4, produitElectro.getRam());
            stmtProduit.setString(5, produitElectro.getRom());
            stmtProduit.setString(6, produitElectro.getOS());
            stmtProduit.setString(7, produitElectro.getCPU());
            stmtProduit.setString(8, produitElectro.getType());
            stmtProduit.setString(9, produitElectro.getGPU());

            int affectedRows = stmtProduit.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Erreur lors de l'insertion du produit !");
            }

            try (ResultSet generatedKeys = stmtProduit.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int produitId = generatedKeys.getInt(1);

                    // Maintenant ajouter dans la table stock avec la quantité
                    try (PreparedStatement stmtStock = connection.prepareStatement(sqlStock)) {
                        stmtStock.setInt(1, produitId);
                        stmtStock.setInt(2, produitElectro.getQuantite());
                        stmtStock.executeUpdate();
                    }
                } else {
                    throw new SQLException("Erreur lors de la récupération de l'ID du produit !");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteProduits(ProduitElectro produitElectro) {
        // On supprime d'abord dans stock (car dépendance foreign key)
        String sqlDeleteStock = "DELETE FROM stock WHERE PRODUIT_ID = ?";
        String sqlDeleteProduit = "DELETE FROM produit WHERE ID = ?";

        try (Connection connection = this.conn.getConnection();
             PreparedStatement stmtStock = connection.prepareStatement(sqlDeleteStock);
             PreparedStatement stmtProduit = connection.prepareStatement(sqlDeleteProduit)) {

            stmtStock.setInt(1, produitElectro.getID());
            stmtStock.executeUpdate();

            stmtProduit.setInt(1, produitElectro.getID());
            stmtProduit.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateProduits(ProduitElectro produitElectro) {
        String sqlProduit = "UPDATE produit SET NOM = ?, FABRICANT = ?, prix = ?, RAM = ?, ROM = ?, OS = ?, CPU = ?, TYPE = ?, GPU = ? WHERE ID = ?";
        String sqlStock = "UPDATE stock SET QUANTITE = ? WHERE PRODUIT_ID = ?";

        try (Connection connection = this.conn.getConnection();
             PreparedStatement stmtProduit = connection.prepareStatement(sqlProduit);
             PreparedStatement stmtStock = connection.prepareStatement(sqlStock)) {

            // Mise à jour produit
            stmtProduit.setString(1, produitElectro.getNom());
            stmtProduit.setString(2, produitElectro.getFabricant());
            stmtProduit.setDouble(3, produitElectro.getPrix());
            stmtProduit.setString(4, produitElectro.getRam());
            stmtProduit.setString(5, produitElectro.getRom());
            stmtProduit.setString(6, produitElectro.getOS());
            stmtProduit.setString(7, produitElectro.getCPU());
            stmtProduit.setString(8, produitElectro.getType());
            stmtProduit.setString(9, produitElectro.getGPU());
            stmtProduit.setInt(10, produitElectro.getID());

            stmtProduit.executeUpdate();

            // Mise à jour stock
            stmtStock.setInt(1, produitElectro.getQuantite());
            stmtStock.setInt(2, produitElectro.getID());
            stmtStock.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ProduitElectro> getProduits() {
        List<ProduitElectro> produits = new ArrayList<>();
        String sql = "SELECT p.*, s.QUANTITE FROM produit p LEFT JOIN stock s ON p.ID = s.PRODUIT_ID";

        try (Connection connection = this.conn.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProduitElectro produit = new ProduitElectro();
                produit.setID(rs.getInt("ID"));
                produit.setNom(rs.getString("NOM"));
                produit.setFabricant(rs.getString("FABRICANT"));
                produit.setPrix(rs.getDouble("prix"));
                produit.setRam(rs.getString("RAM"));
                produit.setRom(rs.getString("ROM"));
                produit.setOS(rs.getString("OS"));
                produit.setCPU(rs.getString("CPU"));
                produit.setType(rs.getString("TYPE"));
                produit.setGPU(rs.getString("GPU"));
                produit.setQuantite(rs.getInt("QUANTITE")); // <--- récupère la quantité

                produits.add(produit);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produits;
    }
}
