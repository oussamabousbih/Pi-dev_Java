/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.DossierMedical;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import services.CRUDdossier;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class HomeController implements Initializable {

    @FXML
    private Button fxajout;
    @FXML
    private Button fxsuivie;
    @FXML
    private Button fxdossiers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void ajouterDossier(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutDossier.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AfficheListController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @FXML
    private void suiviesanté(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Calcul.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjoutDossierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  @FXML
private void affichagedossier(ActionEvent event) {
    try {
         CRUDdossier CD = new CRUDdossier();
         List<DossierMedical> dossiers = CD.afficherDossier();
        // Charger le fichier FXML de l'interface graphique d'affichage des dossiers
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheList.fxml"));
        Parent root = loader.load();
        AfficheListController ALC = loader.getController();
        ALC.setDossiers(dossiers);
        
        // Créer une nouvelle scène pour afficher l'interface graphique
        Scene scene = new Scene(root);
        
        // Récupérer la scène actuelle pour la remplacer par la nouvelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AfficheListController.class.getName()).log(Level.SEVERE, null, ex);
    }}}


