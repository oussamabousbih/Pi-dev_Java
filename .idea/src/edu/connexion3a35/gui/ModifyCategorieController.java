/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.gui;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.services.CategorieCrud;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author msi
 */
public class ModifyCategorieController implements Initializable {

    private Categorie cat;

    public Categorie getCat() {
        return cat;
    }

    public void setCat(Categorie cat) {
        this.cat = cat;
    }
    @FXML
    private TextField tf_nomcatmodif;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public TextField getTf_nomcatmodif() {
        return tf_nomcatmodif;
    }

    public void setTf_nomcatmodif(TextField tf_nomc) {
        this.tf_nomcatmodif = tf_nomc;
    }
    
    @FXML
    private void tf_retour(ActionEvent event) throws IOException {
          Stage stage = (Stage) tf_nomcatmodif.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Categories_affichage.fxml"));
        Parent root = loader.load();
        Categories_affichage_Controller categoriesController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void tf_modifiercat(ActionEvent event) {
        String nomCategorie = tf_nomcatmodif.getText();

        // check if nomCategorie only contains alphabetical characters
        if (!nomCategorie.matches("^[a-zA-Z]+$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ nom_Categorie ne peut contenir que des lettres alphabétiques !");
            alert.showAndWait();
            return;
        }

        // Success message if nomCategorie only contains alphabetical characters
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText("Categorie modifiée avec succès!");

        this.getCat().setId(cat.getId());
        cat.setNom_Categorie(nomCategorie);
        new CategorieCrud().modifierEntite(cat);
        clean();

        alert.showAndWait();
    }

    private void clean() {
        tf_nomcatmodif.setText(null);

    }

}
