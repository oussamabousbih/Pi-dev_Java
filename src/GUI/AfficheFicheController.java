/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Ficheconsultation;
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
import services.CRUDfiche;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AfficheFicheController implements Initializable {

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
    private TextField fxtraitement;
    @FXML
    private TextField fxreccomendation;
    @FXML
    private Button fxajouterdetail;
    @FXML
    private Button fxafficherdetail;

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
      public void setfxdate_consultation(String message){
        this.fxdate_consultation.setText(message);
    }
    public void setfxfirstName (String message){
        this.fxfirstName.setText(message);
    }
    public void setfxlastName (String message){
        this.fxlastName.setText(message);
    }
     public void setfxspecialite (String message){
        this.fxspecialite.setText(message);
    }
    public void setfxtraitement (String message){
        this.fxtraitement.setText(message);
    }
    public void setfxreccomendation (String message){
        this.fxreccomendation.setText(message);
    }

    @FXML
    private void ajouterDetail(ActionEvent event) {
        
    }

    @FXML
    private void afficherDetail(ActionEvent event) {
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
