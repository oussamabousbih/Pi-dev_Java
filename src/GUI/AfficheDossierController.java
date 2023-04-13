/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.DossierMedical;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CRUDdossier;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AfficheDossierController implements Initializable {

    @FXML
    private TextField fxid;
    @FXML
    private TextField fxfirstName;
    @FXML
    private TextField fxlastName;
    @FXML
    private TextField fxdate_naissance;
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
    private TextField fxintervention_chirurgicale;
    @FXML
    private Button fxbtnajouter;
    @FXML
    private Button fxbtnafficher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setfxid (String message){
        this.fxid.setText(message);
    }
    public void setfxfirstName (String message){
        this.fxfirstName.setText(message);
    }
    public void setfxlastName (String message){
        this.fxlastName.setText(message);
    }
    public void setfxdate_naissance (String message){
        this.fxdate_naissance.setText(message);
    }
    public void setfxemail (String message){
        this.fxemail.setText(message);
    }
    public void setfxvaccins (String message){
        this.fxvaccins.setText(message);
    }
    public void setfxmaladies (String message){
        this.fxmaladies.setText(message);
    }
    public void setfxallergies (String message){
        this.fxallergies.setText(message);
    }
    public void setfxanalyses (String message){
        this.fxanalyses.setText(message);
    }
     public void setfxintervention_chirurgicale (String message){
        this.fxintervention_chirurgicale.setText(message);
    }
@FXML
private void ajouterDetail(ActionEvent event) {
    DossierMedical D = new DossierMedical();
    D.setDate_naissance(fxdate_naissance.getText()); // set the date_naissance property
    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheDossier.fxml"));
    try {
        Parent root = loader.load();
        AfficheDossierController ADC = loader.getController();
        ADC.setfxid(""+D.getId());
        ADC.setfxfirstName(D.getFirstName());
        ADC.setfxlastName(D.getLastName());
        ADC.setfxemail(D.getEmail());
        ADC.setfxvaccins(D.getVaccins());
        ADC.setfxmaladies(D.getMaladies());
        ADC.setfxallergies(D.getAllergies());
        ADC.setfxanalyses(D.getAnalyses());
        ADC.setfxintervention_chirurgicale(D.getIntervention_chirurgicale());
        CRUDdossier CD = new CRUDdossier();
        CD.ajouterDossier(D);
        fxfirstName.getScene().setRoot(root);
    } catch (IOException ex) {
        System.out.println("Error"+ ex.getMessage());
    }
}


    @FXML
    private void afficgerDetail(ActionEvent event) {
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
