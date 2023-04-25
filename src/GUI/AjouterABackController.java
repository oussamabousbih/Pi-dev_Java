/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Article;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.CRUDArticle;

/**
 * FXML Controller class
 *
 * @author mazee
 */
public class AjouterABackController implements Initializable {


    @FXML
    private TextField fxsujet;
    @FXML
    private TextArea fxcontenu;
    @FXML
    private TextField fximage;
    @FXML
    private Button fxajouter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private boolean validateSujet() {
        if (fxsujet.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un sujet.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    private boolean validateContenu() {
        if (fxcontenu.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un contenu.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
        private boolean validateImage() {
        if (fximage.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une image.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    @FXML
    private void save(ActionEvent event) {
        
if ( validateSujet() && validateContenu() && validateImage()) {
        String sujet = fxsujet.getText();
        String contenu = fxcontenu.getText();
        String image = fximage.getText();
        
        Article a = new Article(1,sujet,contenu,image);
        
        CRUDArticle art = new CRUDArticle();
        
        art.ajouterArticle(a);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmation Dialog");

        alert.setHeaderText(null);

        alert.setContentText("Article insérée avec succés !");

        alert.show();

    }
    }
}
