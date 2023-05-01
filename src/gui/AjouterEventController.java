/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.event;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.eventService;

/**
 * FXML Controller class
 *
 * @author ThinkPad
 */
public class AjouterEventController implements Initializable {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfdiscription;
    @FXML
    private TextField tfstatus;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfimage;
    @FXML
    private Button btnajouterevent;
    @FXML
    private TextField tfdatedebut;
    @FXML
    private TextField tfdatefin;
    @FXML
    private TextArea screen;
    @FXML
    private Button bsAffich;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterevent(ActionEvent event) {
        
        try {
            String nom_event = tfnom.getText();
            String discription_event = tfdiscription.getText();
            String image_event = tfimage.getText();
            String date_debut_event = tfdatedebut.getText();
            String date_fin_event = tfdatefin.getText();
            String adresse_event = tfadresse.getText();
            String status = tfstatus.getText();
            
            event t = new event(nom_event,discription_event,image_event, date_debut_event, date_fin_event, adresse_event, status) ;
            eventService e = new eventService ();
            e.ajouter(t);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterEventController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
            
       
        
        
        
    }

    
    @FXML
    private void bsAffich(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEvent.fxml"));
            Parent root = loader.load();
            bsAffich.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(AjouterEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
