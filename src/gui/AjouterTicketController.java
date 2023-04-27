/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.event_ticket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ticketService;

/**
 * FXML Controller class
 *
 * @author ThinkPad
 */
public class AjouterTicketController implements Initializable {

    @FXML
    private TextField tdate;
    @FXML
    private TextField tvalidite;
    @FXML
    private TextField tprix;
    @FXML
    private TextField tmatricule;
    @FXML
    private TextField tevent;
    @FXML
    private Button btnajouterticket;
    @FXML
    private TextField timage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterticket(ActionEvent event) {
     try {
            int event_id_id = Integer.parseInt(tevent.getText());
            String matricule_event = tmatricule.getText();
            String image = timage.getText();
            String date_ticket = tdate.getText();
            String valide_ticket = tvalidite.getText();
            String prix_ticket = tprix.getText();
           
            
            event_ticket t = new event_ticket(event_id_id,matricule_event,image,date_ticket,valide_ticket,prix_ticket) ;
            ticketService e = new ticketService ();
            e.ajouter(t);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       
        
        
        
    }
    
}
