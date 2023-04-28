/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Service;
import Services.CRUDService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yasmine Rajhi
 */
public class AddFormController implements Initializable {

    @FXML
    private TextField tf_ID;
    @FXML
    private TextField tf_Nom;
    @FXML
    private TextField tf_prop;
    @FXML
    private TextField tf_prix;
    @FXML
    private DatePicker tf_date_deb;
    @FXML
    private DatePicker tf_date_fin;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_serv;
    @FXML
    private Button btn_types;
    @FXML
    private Button btn_ov;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void save_service(ActionEvent event) {
            int ID = Integer.parseInt(tf_ID.getText());
            String nom = tf_Nom.getText();
            String prop = tf_prop.getText();
            String prix = tf_prix.getText();
            LocalDate dateDeb = tf_date_deb.getValue();
            LocalDate dateFin = tf_date_fin.getValue();
            String id_type="1";
            
            Date date_deb = java.sql.Date.valueOf(dateDeb);
            Date date_fin = java.sql.Date.valueOf(dateFin);
            
            Service S = new Service(ID, nom, prop, id_type, prix, date_deb, date_fin);
            CRUDService crud_s = new CRUDService();
            crud_s.addServices(S);
            
            //change page
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ServicesTables.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_add.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void serv_page(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ServicesTables.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_serv.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void types_page(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TypesTables.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_types.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void overview(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_ov.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    
}
