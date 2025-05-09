package org.example.projet_phn_fx;

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

    private ObservableList<ProduitElectro> produitsData = FXCollections.observableArrayList();
    private IProduitDAO produitDAO;

    @FXML
    public void initialize() {
        // Initialisation de la connexion à la base de données
        DatabaseManager dbManager = new DatabaseManager(new MYSQLConfig());
        this.produitDAO = new ProduitsDAO(dbManager);

        // Configuration des colonnes
        configureTableColumns();

        // Initialisation du ChoiceBox
        typeProduit.getItems().addAll("Téléphone", "Ordinateur", "Accessoires");

        // Gestion de la visibilité des champs
        setupTypeProduitListener();

        // Chargement initial des données
        chargerProduits();

        // Gestion de la sélection
        setupSelectionListener();
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
        produitsData.clear();
        produitsData.addAll(produitDAO.getProduits());
        produitsTable.setItems(produitsData);
    }

    private void afficherDetailsProduit(ProduitElectro produit) {
        if (produit != null) {
            nomProduit.setText(produit.getNom());
            fabricant.setText(produit.getFabricant());
            prix.setText(String.valueOf(produit.getPrix()));
            cpu.setText(produit.getCPU());
            ram.setText(produit.getRam());
            rom.setText(produit.getRom());
            os.setText(produit.getOS());
            quantite.setText(String.valueOf(produit.getQuantite()));
            typeProduit.setValue(produit.getType());

            if ("Accessoires".equals(produit.getType())) {
                typeAccessoire.setText(produit.getGPU());
            }
        }
    }

    @FXML
    private void handleAddProduct() {
        try {
            ProduitElectro produit = new ProduitElectro();
            produit.setNom(nomProduit.getText());
            produit.setFabricant(fabricant.getText());
            produit.setPrix(Double.parseDouble(prix.getText()));
            produit.setCPU(cpu.getText());
            produit.setRam(ram.getText());
            produit.setRom(rom.getText());
            produit.setOS(os.getText());
            produit.setQuantite(Integer.parseInt(quantite.getText()));
            produit.setType(typeProduit.getValue());

            if ("Accessoires".equals(typeProduit.getValue())) {
                produit.setGPU(typeAccessoire.getText());
            }

            produitDAO.addProduits(produit);
            chargerProduits();
            clearForm();
        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Valeur incorrecte", "Veuillez entrer des valeurs numériques valides pour le prix et la quantité.");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur d'ajout", "Une erreur est survenue lors de l'ajout du produit.");
        }
    }

    @FXML
    private void handleDeleteProduct() {
        ProduitElectro selected = produitsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                produitDAO.deleteProduits(selected);
                chargerProduits();
                clearForm();
            } catch (Exception e) {
                showAlert("Erreur", "Erreur de suppression", "Une erreur est survenue lors de la suppression du produit.");
            }
        } else {
            showAlert("Aucune sélection", "Aucun produit sélectionné",
                    "Veuillez sélectionner un produit à supprimer.");
        }
    }

    @FXML
    private void handleUpdateProduct() {
        ProduitElectro selected = produitsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setNom(nomProduit.getText());
                selected.setFabricant(fabricant.getText());
                selected.setPrix(Double.parseDouble(prix.getText()));
                selected.setCPU(cpu.getText());
                selected.setRam(ram.getText());
                selected.setRom(rom.getText());
                selected.setOS(os.getText());
                selected.setQuantite(Integer.parseInt(quantite.getText()));
                selected.setType(typeProduit.getValue());

                if ("Accessoires".equals(typeProduit.getValue())) {
                    selected.setGPU(typeAccessoire.getText());
                }

                produitDAO.updateProduits(selected);
                chargerProduits();
            } catch (NumberFormatException e) {
                showAlert("Erreur de format", "Valeur incorrecte", "Veuillez entrer des valeurs numériques valides pour le prix et la quantité.");
            } catch (Exception e) {
                showAlert("Erreur", "Erreur de modification", "Une erreur est survenue lors de la modification du produit.");
            }
        } else {
            showAlert("Aucune sélection", "Aucun produit sélectionné",
                    "Veuillez sélectionner un produit à modifier.");
        }
    }

    @FXML
    private void handleClearForm() {
        clearForm();
        produitsTable.getSelectionModel().clearSelection();
    }

    private void clearForm() {
        nomProduit.clear();
        fabricant.clear();
        prix.clear();
        cpu.clear();
        ram.clear();
        rom.clear();
        os.clear();
        quantite.clear();
        typeProduit.getSelectionModel().clearSelection();
        typeAccessoire.clear();
        labelTypeAccessoire.setVisible(false);
        typeAccessoire.setVisible(false);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}