/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.event;
import entities.event_ticket;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.eventService;
import services.ticketService;

/**
 * FXML Controller class
 *
 * @author ThinkPad
 */
public class AfficheTicketController implements Initializable {

    @FXML
    private TableColumn<event_ticket, Integer> tid;
    @FXML
    private TableColumn<event_ticket, String> tmatricule;
    @FXML
    private TableColumn<event_ticket, Integer> teventid;
    @FXML
    private TableColumn<event_ticket, String> timage;
    @FXML
    private TableColumn<event_ticket, String> tdate;
    @FXML
    private TableColumn<event_ticket, String> tvalide;
    @FXML
    private TableColumn<event_ticket, String> tprix;
    @FXML
    private TableView<event_ticket> tsticket;
    @FXML
    private Button downloadButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ticketService service = new ticketService();
        try {
            List<event_ticket>   e = service.recuperer();
           
              
                 ObservableList<event_ticket> obs = FXCollections.observableArrayList(e);
            tid.setCellValueFactory(new PropertyValueFactory<event_ticket, Integer>("id"));
            tmatricule.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("matricule_event"));
            teventid.setCellValueFactory(new PropertyValueFactory<event_ticket, Integer>("event_id_id"));
            timage.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("image"));
            tdate.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("date_ticket"));
            tvalide.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("valide_ticket"));
            tprix.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("prix_ticket"));
        
            tsticket.setItems(obs);
            } catch (SQLException ex) {
                Logger.getLogger(AfficheTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
           
    }    
    
    
    
}
