/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.DossierMedical;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CRUDdossier;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class ModifierDossierController implements Initializable {

    @FXML
    private TextField fxid;
    @FXML
    private TextField fxfirstName;
    @FXML
    private TextField fxlastName;
    @FXML
    private TextField fxdatenaissance;
    @FXML
    private TextField fxemail;
    @FXML
    private TextField fxvaccins;
    @FXML
    private TextField fxmaladies;
    @FXML
    private TextField fxallergies;
    @FXML
    private TextField fxanalyses;
    @FXML
    private TextField fxintervention;
    @FXML
    private Button fxbtnvalider;
    
    private DossierMedical dossier;
    @FXML
    private Button fxhome;
    @FXML
    private Button fxsuivie;
    @FXML
    private Button fxafficheList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
   public void setDossier(DossierMedical dossier) {
    fxid.setText(String.valueOf(dossier.getId()));
    fxfirstName.setText(dossier.getFirstName());
    fxlastName.setText(dossier.getLastName());
    LocalDate date_naissance = dossier.getDate_naissance();
    fxdatenaissance.setText(date_naissance.toString());
    fxemail.setText(dossier.getEmail());
    fxvaccins.setText(dossier.getVaccins());
    fxmaladies.setText(dossier.getMaladies());
    fxallergies.setText(dossier.getAllergies());
    fxanalyses.setText(dossier.getAnalyses());
    fxintervention.setText(dossier.getIntervention_chirurgicale());
}
    public DossierMedical getDossier() {
    LocalDate date_naissance = LocalDate.parse(fxdatenaissance.getText());
    return new DossierMedical(Integer.parseInt(fxid.getText()), fxfirstName.getText(), fxlastName.getText(),
            date_naissance, fxemail.getText(), fxvaccins.getText(), fxmaladies.getText(), fxallergies.getText(),
            fxanalyses.getText(), fxintervention.getText());
}



    @FXML
    private void validermodification(ActionEvent event) {   
        int id = Integer.parseInt(fxid.getText());
        String firstName = fxfirstName.getText();
        String lastName = fxlastName.getText();
        LocalDate date_naissance = LocalDate.parse(fxdatenaissance.getText());
        String email = fxemail.getText();
        String vaccins = fxvaccins.getText();
        String maladies = fxmaladies.getText();
        String allergies = fxallergies.getText();
        String analyses = fxanalyses.getText();
        String intervention = fxintervention.getText();
        DossierMedical dossier = new DossierMedical(id, firstName, lastName, date_naissance, email, vaccins, maladies, allergies, analyses, intervention);
        CRUDdossier crud = new CRUDdossier();
        crud.modifierDossier(dossier, id);
        System.out.println("Le dossier avec l'ID " + id + " a été modifié dans la base de données");
        Stage stage = (Stage) fxbtnvalider.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void orientationhome(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(ModifierDossierController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    

    @FXML
    private void suivreSanté(ActionEvent event) {
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
    private void afficheList(ActionEvent event) {
        CRUDdossier CD = new CRUDdossier();
    List<DossierMedical> dossiers = CD.afficherDossier();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheList.fxml"));
    try {
        Parent root = loader.load();
        AfficheListController ALC = loader.getController();
        ALC.setDossiers(dossiers);
        fxfirstName.getScene().setRoot(root);
    } catch (IOException ex) {
        System.out.println("Error"+ ex.getMessage());
    }
    }
}
    
    

