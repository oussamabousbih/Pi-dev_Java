/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CRUDfiche;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AjoutFicheController implements Initializable {

    @FXML
    private TextField fxid;
    @FXML
    private TextField fxdate_consultation;
    @FXML
    private TextField fxfirstName;
    @FXML
    private TextField fxlastName;
    @FXML
    private TextField fxspecialite;
    @FXML
    private TextField fxtraitemant;
    @FXML
    private TextField fxreccomendation;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button fxaffichelistF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterFiche(ActionEvent event) {
        
            int id = Integer.parseInt(fxid.getText());
            LocalDate date_consultation = LocalDate.parse(fxdate_consultation.getText());
            String firstName= fxfirstName.getText();
            String lastName = fxlastName.getText();
            String specialite= fxspecialite.getText();
            String traitement = fxtraitemant.getText();
            String reccomendation = fxreccomendation.getText();
            
            Ficheconsultation F = new Ficheconsultation( date_consultation,firstName, lastName,  specialite,  traitement,  reccomendation);
            CRUDfiche CF = new CRUDfiche ();
            CF.ajouterFiche(F);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheFiche.fxml"));
            try {
            Parent root = loader.load();
            AfficheFicheController AFC = loader.getController();
            AFC.setfxid(""+F.getId());
            AFC.setfxdate_consultation(F.getDate_consultation().toString());
            AFC.setfxfirstName(F.getFirstName());
            AFC.setfxlastName(F.getLastName());
            AFC.setfxspecialite(F.getSpecialite());
            AFC.setfxtraitement(F.getTraitement());
            AFC.setfxreccomendation(F.getReccomendation());
           
            
            fxfirstName.getScene().setRoot(root);
        } catch (IOException ex) {
                System.out.println("Error"+ ex.getMessage());
        }
    }
    
     @FXML
    private void affichelistfiche(ActionEvent event) {
    CRUDfiche CF = new CRUDfiche ();
    List<Ficheconsultation> fiches = CF.afficherFiche();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheListFiche.fxml"));
    try {
        Parent root = loader.load();
        AfficheListFicheController ALCF = loader.getController();
        ALCF.setFiches(fiches);
        fxfirstName.getScene().setRoot(root);
    } catch (IOException ex) {
        System.out.println("Error"+ ex.getMessage());
    }
}

   
    
}
