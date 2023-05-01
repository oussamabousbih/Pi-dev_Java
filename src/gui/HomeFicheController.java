/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.DossierMedical;
import entities.Ficheconsultation;
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
import services.CRUDfiche;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class HomeFicheController implements Initializable {

    @FXML
    private Button fxajout;
    @FXML
    private Button fxaffiche;
    @FXML
    private Button fxdossier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterfiche(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutFiche.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(AjoutFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void affichesfiches(ActionEvent event) {
        CRUDfiche CF = new CRUDfiche ();
    List<Ficheconsultation> fiches = CF.afficherFiche();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheListFiche.fxml"));
    try {
        Parent root = loader.load();
        AfficheListFicheController ALCF = loader.getController();
        ALCF.setFiches(fiches);
        Scene scene = new Scene(root);
        
        // Récupérer la scène actuelle pour la remplacer par la nouvelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace(); // afficher les détails de l'erreur dans la console
    Logger.getLogger(AfficheListFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void consulterDossier(ActionEvent event) {
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
    
    

