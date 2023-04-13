/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Ficheconsultation;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    }
    

