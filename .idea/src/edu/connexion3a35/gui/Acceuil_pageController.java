/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.gui;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Produit;
import edu.connexion3a35.services.CategorieCrud;
import edu.connexion3a35.services.ProduitCrud;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author msi
 */
public class Acceuil_pageController implements Initializable {

    @FXML
    private Button id_ajouter;

    @FXML
    private TextField tf_nomp;

    @FXML
    private TextField td_desc;

    @FXML
    private TextField tf_prix;

    @FXML
    private TextField tf_quantite;

    @FXML
    private TextField tf_image;

    @FXML
    private ComboBox<Categorie> tf_cat;

    @FXML
    private TextField tf_marque;

    @FXML
    private Button id_afficher;

    @FXML
    private TextField tf_nomc;

    @FXML
    void tf_afficher(ActionEvent event) throws IOException {
        Stage stage = (Stage) tf_prix.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Affichage_Produit.fxml"));
        Parent root = loader.load();
        Affichage_ProduitController produitController = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tf_ajouter(ActionEvent event) {
        String nom_Produit = tf_nomp.getText();
        String desc_produit = td_desc.getText();
        String quantiteText = tf_quantite.getText();
        String prixText = tf_prix.getText();
        String image_produit = tf_image.getText();
        String marque = tf_marque.getText();
        Categorie selectedCategory = tf_cat.getValue();

        // check if input is valid
        if (!isValidInput(quantiteText, prixText, selectedCategory, nom_Produit, desc_produit, image_produit, marque)) {
            return;
        }

        int quantite = Integer.parseInt(quantiteText);
        float prix = Float.parseFloat(prixText);

        Produit pro = new Produit();
        pro.setCategorie_produit(selectedCategory.getId());
        System.out.println("edu.connexion3a35.gui.Acceuil_pageController.tf_ajouter()" + pro.getCategorie_produit());

        Produit p = new Produit(quantite, pro.getCategorie_produit(), prix, nom_Produit, marque, desc_produit, image_produit);
        ProduitCrud pr = new ProduitCrud();
        pr.ajouterEntitee(p);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Produit ajouté");
        alert.setHeaderText(null);
        alert.setContentText("Le produit a été ajouté avec succès !");
        alert.showAndWait();
        // Clear the text fields
        tf_nomp.clear();
        td_desc.clear();
        tf_prix.clear();
        tf_quantite.clear();
        tf_image.clear();
        tf_marque.clear();
        tf_cat.getSelectionModel().clearSelection();
    }

    private boolean isValidInput(String quantiteText, String prixText, Categorie selectedCategory, String nom_Produit, String desc_produit, String image_produit, String marque) {
        // check if all required fields are filled
        if (nom_Produit.isEmpty() || desc_produit.isEmpty() || quantiteText.isEmpty() || prixText.isEmpty() || image_produit.isEmpty() || marque.isEmpty() || selectedCategory == null) {
            // Afficher un message d'erreur si les champs obligatoires ne sont pas remplis
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires !");
            alert.showAndWait();
            return false;
        }

        // check if quantiteText and prixText are valid numbers
        try {
            int quantite = Integer.parseInt(quantiteText);
            float prix = Float.parseFloat(prixText);

            if (quantite <= 0 || prix <= 0) {
                // Afficher un message d'erreur si quantite ou prix sont inférieurs ou égaux à 0
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer des nombres positifs pour la quantité et le prix !");
                alert.showAndWait();
                return false;
            }
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si quantiteText ou prixText ne sont pas des nombres valides
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des nombres valides pour la quantité et le prix !");
            alert.showAndWait();
            return false;
        }

        // check if nom_Produit, desc_produit and marque contains only letters and spaces
        if (!nom_Produit.matches("^[a-zA-Z ]+$") || !desc_produit.matches("^[a-zA-Z ]+$") || !marque.matches("^[a-zA-Z ]+$")) {
            // Afficher un message d'erreur si nom_Produit, desc_produit or marque contains non-letter characters
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des caractères alphabétiques pour le nom, la description et la marque !");
            alert.showAndWait();
            return false;
        }

        // check if image_produit ends with .jpg or .png
        if (!image_produit.endsWith(".jpg") && !image_produit.endsWith(".png")) {
            // Afficher un message d'erreur si image_produit n'est pas un fichier .jpg ou .png
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer une image au format .jpg ou .png !");
            alert.showAndWait();
            return false;
        }

        return true;
    }




    @FXML
    private void tf_ajouterc(ActionEvent event) {
        String nom_Categorie = tf_nomc.getText();

        // Check if nom_Categorie only contains letters
        if (!nom_Categorie.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ nom_Categorie ne peut contenir que des lettres!");
            alert.showAndWait();
            return;
        }

        // Check if nom_Categorie is empty
        if (nom_Categorie.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ nom_Categorie ne peut pas être vide!");
            alert.showAndWait();
            return;
        }

        // Success message if the nom_Categorie field is not empty and only contains letters
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText("Categorie ajoutée avec succès!");

        Categorie c = new Categorie(nom_Categorie);
        CategorieCrud cr = new CategorieCrud();
        cr.ajouterEntitee(c);
        clean();

        alert.showAndWait();
    }


    @FXML
    private void tf_afficherc(ActionEvent event) throws IOException {
        Stage stage = (Stage) tf_nomc.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Categories_affichage.fxml"));
        Parent root = loader.load();
        Categories_affichage_Controller categoriesController = loader.getController();
        //Categorie c =new Categorie("knfknfkn");

        //categoriesController.setCat(c);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private void clean() {
        tf_nomc.setText(null);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategorieCrud cr = new CategorieCrud();
        List<Categorie> categories = cr.listeDesEntites();
        tf_cat.getItems().addAll(categories);


    }

}
