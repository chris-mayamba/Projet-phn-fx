package org.example.database;

import org.example.Data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager implements IConnectionProvider{
    private IDatabaseConfig config;

    public DatabaseManager(IDatabaseConfig config){
        this.config = config;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
        );
    }

    /*
    private static final String URL = "jdbc:mysql://localhost/produitstock";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Ajouter un enregistrement de stock
    public void ajouterStock(int produitId, int quantite) {
        String sql = "INSERT INTO stock (id, produit_id, quantite) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, produitId); // id = même que produit
            stmt.setInt(2, produitId); // produit_id = id produit
            stmt.setInt(3, quantite);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du stock", e);
        }
    }

    // Mettre à jour la quantité du stock
    public void mettreAJourStock(int produitId, int nouvelleQuantite) {
        String sql = "UPDATE stock SET quantite = ? WHERE produit_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, nouvelleQuantite);
            stmt.setInt(2, produitId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du stock", e);
        }
    }

    // Supprimer un stock
    public void supprimerStock(int produitId) {
        String sql = "DELETE FROM stock WHERE produit_id = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, produitId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du stock", e);
        }
    }


    // Ajouter un produit avec son stock
    public void ajouterProduit(ProduitElectro produit, int quantite) {
        String sqlProduit = "INSERT INTO produit (nom, fabricant, prix, ram, rom, os, cpu, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlStock = "INSERT INTO stock (produit_id, quantite) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmtProduit = con.prepareStatement(sqlProduit, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtStock = con.prepareStatement(sqlStock)) {

            // Insertion du produit
            stmtProduit.setString(1, produit.getNom());
            stmtProduit.setString(2, produit.getFabricant());
            stmtProduit.setDouble(3, produit.getPrix());
            stmtProduit.setString(4, produit.getRam());
            stmtProduit.setString(5, produit.getRom());
            stmtProduit.setString(6, produit.getOS());
            stmtProduit.setString(7, produit.getCPU());
            stmtProduit.setString(8, produit.getType());

            stmtProduit.executeUpdate();

            // Récupérer l'ID auto-généré pour le produit
            try (ResultSet rs = stmtProduit.getGeneratedKeys()) {
                if (rs.next()) {
                    int produitId = rs.getInt(1); // ID généré pour le produit
                    // Insertion du stock
                    stmtStock.setInt(1, produitId);
                    stmtStock.setInt(2, quantite);
                    stmtStock.executeUpdate();
                } else {
                    throw new RuntimeException("Échec de la récupération de l'ID généré du produit.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du produit", e);
        }
    }




    // Supprimer un produit
    public void supprimerProduit(int id) {
        String sql = "DELETE FROM produit WHERE id = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du produit", e);
        }
    }

    // Mettre à jour un produit
    public void mettreAJourProduit(ProduitElectro produit) {
        String sql = "UPDATE produit SET nom = ?, fabricant = ?, prix = ?, ram = ?, rom = ?, os = ?, cpu = ?, type = ? WHERE id = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, produit.getNom());
            stmt.setString(2, produit.getFabricant());
            stmt.setDouble(3, produit.getPrix());
            stmt.setString(4, produit.getRam());
            stmt.setString(5, produit.getRom());
            stmt.setString(6, produit.getOS());
            stmt.setString(7, produit.getCPU());
            stmt.setString(8, produit.getType());
            stmt.setInt(9, produit.getID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du produit", e);
        }
    }

    // Lire tous les produits
    public List<ProduitElectro> lireTousProduits() {
        List<ProduitElectro> produits = new ArrayList<>();
        String sql = "SELECT * FROM produit";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProduitElectro produit = new ProduitElectro(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("fabricant"),
                        (long) rs.getDouble("prix"),
                        rs.getString("ram"),
                        rs.getString("rom"),
                        rs.getString("os"),
                        rs.getString("cpu")
                );

                produits.add(produit);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture des produits", e);
        }

        return produits;
    }

    public List<ProduitQuantite> lireStockComplet() {
        List<ProduitQuantite> liste = new ArrayList<>();

        String sql = "SELECT p.*, s.quantite FROM produit p JOIN stock s ON p.id = s.produit_id";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String fabricant = rs.getString("fabricant");
                long prix = rs.getLong("prix");
                String ram = rs.getString("ram");
                String rom = rs.getString("rom");
                String os = rs.getString("os");
                String cpu = rs.getString("cpu");
                String type = rs.getString("type");
                int quantite = rs.getInt("quantite");

                if (type == null){
                    type = "";
                }

                ProduitElectro produit = null;
                switch (type.toLowerCase()) {
                    case "telephone":
                        produit = new Telephone(id, nom, fabricant, prix, ram, rom, os, cpu);
                        break;
                    case "ordinateur":
                        String gpu = rs.getString("gpu");
                        String typeOrdinateur = rs.getString("type"); // Récupère la colonne 'type'
                        if (typeOrdinateur == null) typeOrdinateur = "";

                        produit = new Ordinateur(id, nom, fabricant, prix, ram, rom, os, cpu, gpu, typeOrdinateur);
                        break;
                    case "accessoires":
                        String typeAccessoires = rs.getString("type");
                        if (typeAccessoires == null) typeAccessoires = "";

                        produit = new Accessoires(id, nom, fabricant, prix, ram, rom, os, cpu, typeAccessoires);
                        break;
                }

                if (produit != null) {
                    liste.add(new ProduitQuantite(produit, quantite));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la lecture du stock depuis la base de données", e);
        }

        return liste;
    } */

}

