/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.DossierMedical;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
}
    
    

