/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Ficheconsultation;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CRUDfiche;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class ModifierFicheController implements Initializable {

    @FXML
    private TextField fxtraitement;
    @FXML
    private TextField fxreccomendtaion;
    @FXML
    private TextField fxid;
    @FXML
    private TextField fxdateconsultation;
    @FXML
    private TextField fxfirstName;
    @FXML
    private TextField fxlastName;
    @FXML
    private TextField fxspecialite;
    @FXML
    private Button fxbtnvalider;
    
    private Ficheconsultation fiche;
    @FXML
    private Button fxhome;
    @FXML
    private Button fxajout;
    @FXML
    private Button fxafficheList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setFiche(Ficheconsultation fiche) {
    this.fiche = fiche;
    fxid.setText(String.valueOf(fiche.getId()));
   fxdateconsultation.setText(fiche.getDate_consultation().toString());
    fxfirstName.setText(fiche.getFirstName());
    fxlastName.setText(fiche.getLastName());
    fxspecialite.setText(fiche.getSpecialite());
    fxtraitement.setText(fiche.getTraitement());
    fxreccomendtaion.setText(fiche.getReccomendation());
}

   

 public Ficheconsultation getFiche() {
    LocalDate date_consultation = LocalDate.parse(fxdateconsultation.getText());
    return new Ficheconsultation(Integer.parseInt(fxid.getText()), date_consultation, fxfirstName.getText(), fxlastName.getText(),
            fxspecialite.getText(), fxtraitement.getText(), fxreccomendtaion.getText());
}
    @FXML
    
private void validermodification(ActionEvent event) {
    int id = fiche.getId();
    LocalDate date_consultation = LocalDate.parse(fxdateconsultation.getText());
    String firstName = fxfirstName.getText();
    String lastName = fxlastName.getText();
    String specialite = fxspecialite.getText();
    String traitement = fxtraitement.getText();
    String reccomendation = fxreccomendtaion.getText();
    
    fiche.setDate_consultation(date_consultation);
    fiche.setFirstName(firstName);
    fiche.setLastName(lastName);
    fiche.setSpecialite(specialite);
    fiche.setTraitement(traitement);
    fiche.setReccomendation(reccomendation);
    
    CRUDfiche CF = new CRUDfiche();
    CF.modifierFiche(fiche , id);
    System.out.println("La fiche avec l'ID " + id + " a été modifiée dans la base de données");
    
    Stage stage = (Stage) fxbtnvalider.getScene().getWindow();
    stage.close();
}

    @FXML
    private void orientationhomeFiche(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFiche.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(HomeFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void AjouterFiche(ActionEvent event) {
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
    private void afficheList(ActionEvent event) {
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

    }
    

