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
import java.time.format.DateTimeParseException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static jdk.nashorn.internal.runtime.Debug.id;
import services.CRUDdossier;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AjoutDossierController implements Initializable {

    
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
    private Button btnajouter;

    @FXML
    private TextField fxfirstName;
    @FXML
    private TextField fxlastName;
    @FXML
    private TextField fxdate_naissance;
    @FXML
    private TextField fxintervention_chirurgicale;
    @FXML
    private TextField fxid;
    @FXML
    private Button fxafficheList;
    @FXML
    private Button fxsuivie;
    @FXML
    private Button fxhome;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void ajouterDossier(ActionEvent event) {
    String firstName = fxfirstName.getText();
    String lastName = fxlastName.getText();
    LocalDate date_naissance;
    try {
        date_naissance = LocalDate.parse(fxdate_naissance.getText());
    } catch (DateTimeParseException e) {
        showAlert("Date de naissance invalide");
        return;
    }
    String email = fxemail.getText();
    if (!isValidEmail(email)) {
        showAlert("Adresse email invalide");
        return;
    }
    String vaccins = fxvaccins.getText();
    String maladies = fxmaladies.getText();
    String allergies = fxallergies.getText();
    String analyses = fxanalyses.getText();
    String intervention_chirurgicale = fxintervention_chirurgicale.getText();

    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || vaccins.isEmpty() || maladies.isEmpty() || allergies.isEmpty() || analyses.isEmpty() || intervention_chirurgicale.isEmpty()) {
        showAlert("Veuillez remplir tous les champs");
        return;
    }
        
    DossierMedical D = new DossierMedical(firstName, lastName, date_naissance, email, vaccins, maladies, allergies, analyses, intervention_chirurgicale);
    CRUDdossier CD = new CRUDdossier();
    CD.ajouterDossier(D);
         
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheDossier.fxml"));
    try {
        Parent root = loader.load();
        AfficheDossierController ADC = loader.getController();
        ADC.setfxid(""+D.getId());
        ADC.setfxfirstName(D.getFirstName());
        ADC.setfxlastName(D.getLastName());
        ADC.setfxdate_naissance(D.getDate_naissance().toString());
        ADC.setfxemail(D.getEmail());
        ADC.setfxvaccins(D.getVaccins());
        ADC.setfxmaladies(D.getMaladies());
        ADC.setfxallergies(D.getAllergies());
        ADC.setfxanalyses(D.getAnalyses());
        ADC.setfxintervention_chirurgicale(D.getIntervention_chirurgicale());
            
        fxfirstName.getScene().setRoot(root);
    } catch (IOException ex) {
        System.out.println("Error"+ ex.getMessage());
    }
}

private boolean isValidEmail(String email) {
    String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    return email.matches(emailPattern);
}

private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur de saisie");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
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
    private void orientationhome(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

 
    
    
}
