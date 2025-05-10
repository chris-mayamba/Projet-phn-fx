package org.example.projet_phn_fx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.projet_phn_fx.Data.ProduitElectro;
import org.example.projet_phn_fx.dao.IProduitDAO;
import org.example.projet_phn_fx.dao.ProduitsDAO;
import org.example.projet_phn_fx.database.DatabaseManager;
import org.example.projet_phn_fx.database.MYSQLConfig;

import java.util.List;

public class GestionProduitController {
    @FXML private TableView<ProduitElectro> produitsTable;
    @FXML private TableColumn<ProduitElectro, Integer> idCol;
    @FXML private TableColumn<ProduitElectro, String> nomCol;
    @FXML private TableColumn<ProduitElectro, String> typeCol;
    @FXML private TableColumn<ProduitElectro, String> fabricantCol;
    @FXML private TableColumn<ProduitElectro, Double> prixCol;
    @FXML private TableColumn<ProduitElectro, Integer> quantiteCol;
    @FXML private TableColumn<ProduitElectro, String> cpuCol;
    @FXML private TableColumn<ProduitElectro, String> ramCol;
    @FXML private TableColumn<ProduitElectro, String> romCol;
    @FXML private TableColumn<ProduitElectro, String> osCol;

    @FXML private ChoiceBox<String> typeProduit;
    @FXML private TextField nomProduit;
    @FXML private TextField fabricant;
    @FXML private TextField prix;
    @FXML private TextField cpu;
    @FXML private TextField ram;
    @FXML private TextField rom;
    @FXML private TextField os;
    @FXML private TextField quantite;
    @FXML private TextField typeAccessoire;
    @FXML private Label labelTypeAccessoire;
    @FXML
    private Button addButton, deleteButton, updateButton, clearButton;


    private ObservableList<ProduitElectro> produitsData = FXCollections.observableArrayList();
    private IProduitDAO produitDAO;

    @FXML
    public void initialize() {
        try {

            // Initialisation DAO
            DatabaseManager dbManager = new DatabaseManager(new MYSQLConfig());
            this.produitDAO = new ProduitsDAO(dbManager);

            // Configuration des colonnes
            configureTableColumns();

            // Initialisation ChoiceBox
            typeProduit.getItems().addAll("Téléphone", "Ordinateur", "Accessoires");
            typeProduit.getSelectionModel().selectFirst();

            // Gestion visibilité champs accessoires
            setupTypeProduitListener();

            // Chargement données initiales
            chargerProduits();

            // Gestion sélection tableau
            setupSelectionListener();

        } catch (Exception e) {
            showAlert("Erreur Initialisation", "Erreur critique",
                    "Impossible d'initialiser le contrôleur: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        fabricantCol.setCellValueFactory(new PropertyValueFactory<>("fabricant"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        cpuCol.setCellValueFactory(new PropertyValueFactory<>("CPU"));
        ramCol.setCellValueFactory(new PropertyValueFactory<>("ram"));
        romCol.setCellValueFactory(new PropertyValueFactory<>("rom"));
        osCol.setCellValueFactory(new PropertyValueFactory<>("OS"));
    }

    private void setupTypeProduitListener() {
        typeProduit.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean isAccessoire = "Accessoires".equals(newValue);
                    labelTypeAccessoire.setVisible(isAccessoire);
                    typeAccessoire.setVisible(isAccessoire);
                });
    }

    private void setupSelectionListener() {
        produitsTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> afficherDetailsProduit(newValue));
    }

    private void chargerProduits() {
        try {
            produitsData.clear();
            List<ProduitElectro> produits = produitDAO.getProduits();

            System.out.println("Nombre de produits chargés : " + produits.size()); // Debug

            produitsData.addAll(produits);
            produitsTable.setItems(produitsData);

            // Force le rafraîchissement visuel
            Platform.runLater(() -> {
                produitsTable.refresh();
                produitsTable.setStyle("-fx-font-size: 14px;");
            });

        } catch (Exception e) {
            System.err.println("Erreur chargement produits :");
            e.printStackTrace();
        }
    }

    private void afficherDetailsProduit(ProduitElectro produit) {
        if (produit != null) {
            nomProduit.setText(produit.getNom());
            fabricant.setText(produit.getFabricant());
            prix.setText(String.valueOf(produit.getPrix()));
            quantite.setText(String.valueOf(produit.getQuantite()));
            typeProduit.setValue(produit.getType());

            // Champs optionnels
            cpu.setText(produit.getCPU() != null ? produit.getCPU() : "");
            ram.setText(produit.getRam() != null ? produit.getRam() : "");
            rom.setText(produit.getRom() != null ? produit.getRom() : "");
            os.setText(produit.getOS() != null ? produit.getOS() : "");

            if ("Accessoires".equals(produit.getType())) {
                typeAccessoire.setText(produit.getGPU() != null ? produit.getGPU() : "");
            }
        }
    }

    @FXML
    private void handleAddProduct() {
        System.out.println("Méthode handleAddProduct appelée !");
        try {
            // Validation des champs obligatoires
            if (nomProduit.getText().isEmpty() || fabricant.getText().isEmpty() ||
                    prix.getText().isEmpty() || quantite.getText().isEmpty() ||
                    typeProduit.getValue() == null) {
                showAlert("Champs manquants", "Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }

            // Création du produit
            ProduitElectro nouveauProduit = new ProduitElectro();
            nouveauProduit.setNom(nomProduit.getText());
            nouveauProduit.setFabricant(fabricant.getText());
            nouveauProduit.setPrix(Double.parseDouble(prix.getText()));
            nouveauProduit.setQuantite(Integer.parseInt(quantite.getText()));
            nouveauProduit.setType(typeProduit.getValue());

            // Champs optionnels
            if (!cpu.getText().isEmpty()) nouveauProduit.setCPU(cpu.getText());
            if (!ram.getText().isEmpty()) nouveauProduit.setRam(ram.getText());
            if (!rom.getText().isEmpty()) nouveauProduit.setRom(rom.getText());
            if (!os.getText().isEmpty()) nouveauProduit.setOS(os.getText());

            // Cas particulier des accessoires
            if ("Accessoires".equals(typeProduit.getValue())) {
                nouveauProduit.setGPU(typeAccessoire.getText());
            }

            // Ajout dans la base
            produitDAO.addProduits(nouveauProduit);

            // Rafraîchissement
            chargerProduits();
            clearForm();

            showAlert("Succès", "Produit ajouté", "Le produit a été ajouté avec succès !");

        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Valeur incorrecte",
                    "Veuillez entrer des nombres valides pour le prix et la quantité");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur d'ajout",
                    "Erreur lors de l'ajout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProduct() {
        ProduitElectro selected = produitsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Aucune sélection", "Erreur", "Veuillez sélectionner un produit à supprimer");
            return;
        }

        try {
            // Confirmation
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation suppression");
            confirm.setHeaderText("Supprimer le produit");
            confirm.setContentText("Êtes-vous sûr de vouloir supprimer " + selected.getNom() + "?");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                // Suppression
                produitDAO.deleteProduits(selected);

                // Rafraîchissement
                chargerProduits();
                clearForm();

                showAlert("Succès", "Produit supprimé", "Le produit a été supprimé avec succès");
            }

        } catch (Exception e) {
            showAlert("Erreur", "Erreur suppression",
                    "Erreur lors de la suppression: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateProduct() {
        ProduitElectro selected = produitsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Aucune sélection", "Erreur", "Veuillez sélectionner un produit à modifier");
            return;
        }

        try {
            // Validation des champs
            if (nomProduit.getText().isEmpty() || fabricant.getText().isEmpty() ||
                    prix.getText().isEmpty() || quantite.getText().isEmpty()) {
                showAlert("Champs manquants", "Erreur", "Veuillez remplir tous les champs obligatoires");
                return;
            }

            // Mise à jour de l'objet
            selected.setNom(nomProduit.getText());
            selected.setFabricant(fabricant.getText());
            selected.setPrix(Double.parseDouble(prix.getText()));
            selected.setQuantite(Integer.parseInt(quantite.getText()));
            selected.setType(typeProduit.getValue());

            // Champs optionnels
            selected.setCPU(cpu.getText().isEmpty() ? null : cpu.getText());
            selected.setRam(ram.getText().isEmpty() ? null : ram.getText());
            selected.setRom(rom.getText().isEmpty() ? null : rom.getText());
            selected.setOS(os.getText().isEmpty() ? null : os.getText());

            // Cas particulier des accessoires
            if ("Accessoires".equals(typeProduit.getValue())) {
                selected.setGPU(typeAccessoire.getText().isEmpty() ? null : typeAccessoire.getText());
            }

            // Mise à jour dans la base
            produitDAO.updateProduits(selected);

            // Rafraîchissement
            chargerProduits();

            showAlert("Succès", "Produit modifié", "Le produit a été modifié avec succès");

        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Valeur incorrecte",
                    "Veuillez entrer des nombres valides pour le prix et la quantité");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur modification",
                    "Erreur lors de la modification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearForm() {
        clearForm();
    }

    private void clearForm() {
        nomProduit.clear();
        fabricant.clear();
        prix.clear();
        quantite.clear();
        cpu.clear();
        ram.clear();
        rom.clear();
        os.clear();
        typeAccessoire.clear();
        typeProduit.getSelectionModel().clearSelection();
        produitsTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}