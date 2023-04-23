/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.gui;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Produit;
import edu.connexion3a35.services.CategorieCrud;
import edu.connexion3a35.services.ProduitCrud;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ModifyProduitController implements Initializable {
 private Produit produit;

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Button getId_modifier() {
        return id_modifier;
    }

    public void setId_modifier(Button id_modifier) {
        this.id_modifier = id_modifier;
    }

    public TextField getTf_nomp() {
        return tf_nomp;
    }

    public void setTf_nomp(TextField tf_nomp) {
        this.tf_nomp = tf_nomp;
    }

    public TextField getTd_desc() {
        return td_desc;
    }

    public void setTd_desc(TextField td_desc) {
        this.td_desc = td_desc;
    }

    public TextField getTf_prix() {
        return tf_prix;
    }

    public void setTf_prix(TextField tf_prix) {
        this.tf_prix = tf_prix;
    }

    public TextField getTf_quantite() {
        return tf_quantite;
    }

    public void setTf_quantite(TextField tf_quantite) {
        this.tf_quantite = tf_quantite;
    }

    public TextField getTf_image() {
        return tf_image;
    }

    public void setTf_image(TextField tf_image) {
        this.tf_image = tf_image;
    }

    public ComboBox<?> getTf_cat() {
        return tf_cat;
    }

    public void setTf_cat(ComboBox<Categorie> tf_cat) {
        this.tf_cat = tf_cat;
    }

    public TextField getTf_marque() {
        return tf_marque;
    }

    public void setTf_marque(TextField tf_marque) {
        this.tf_marque = tf_marque;
    }

    public Button getId_retour() {
        return id_retour;
    }

    public void setId_retour(Button id_retour) {
        this.id_retour = id_retour;
    }
    @FXML
    private Button id_modifier;
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
    private Button id_retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategorieCrud cr = new CategorieCrud();
        List<Categorie> categories = cr.listeDesEntites();
        tf_cat.getItems().addAll(categories);


    }

    @FXML
    private void tf_modifier(ActionEvent event) {
        this.getProduit().getId();

            String quantiteText = tf_quantite.getText();
            String prixText = tf_prix.getText();
            String nom_produit = tf_nomp.getText();
            String description = td_desc.getText();
            String image_produit = tf_image.getText();
            String marque = tf_marque.getText();
            Categorie selectedCategory = tf_cat.getValue();

            if (isValidInput(quantiteText, prixText, selectedCategory, nom_produit, description, image_produit, marque)) {
                this.getProduit().getId();
                produit.setNom_produit(nom_produit);
                produit.setDescription(description);
                produit.setPrix((float) Double.parseDouble(prixText));
                produit.setCategorie_produit(selectedCategory.getId());
                System.out.println("edu.connexion3a35.gui.Acceuil_pageController.tf_ajouter()" + produit.getCategorie_produit());
                produit.setQuantite(Integer.parseInt(quantiteText));
                produit.setImage_produit(image_produit);
                produit.setMarque(marque);

                new ProduitCrud().modifierEntite(produit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Produit ajouté");
                alert.setHeaderText(null);
                alert.setContentText("Le produit a été modifié avec succès !");
                alert.showAndWait();


            }
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
    private void tf_retour(ActionEvent event) throws IOException {
        Stage stage = (Stage) tf_nomp.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Affichage_Produit.fxml"));
        Parent root = loader.load();
        Affichage_ProduitController produitController = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
