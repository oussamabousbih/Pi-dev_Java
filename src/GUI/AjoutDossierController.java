/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.DossierMedical;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterDossier(ActionEvent event) {
       // int id = Integer.parseInt(fxid.getText());
        String firstName= fxfirstName.getText();
        String lastName = fxlastName.getText();
        LocalDate date_naissance = LocalDate.parse(fxdate_naissance.getText());
        String email = fxemail.getText();
        String vaccins = fxvaccins.getText();
        String maladies = fxmaladies.getText();
        String allergies = fxallergies.getText();
        String analyses = fxanalyses.getText();
        String intervention_chirurgicale = fxintervention_chirurgicale.getText();
        
         DossierMedical D = new DossierMedical(   firstName,  lastName,  date_naissance,  email,  vaccins,  maladies,  allergies,  analyses,  intervention_chirurgicale) ;
         CRUDdossier  CD = new CRUDdossier () ;
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
