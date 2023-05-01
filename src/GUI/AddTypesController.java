/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Service;
import entities.TypeService;
import services.CRUDService;
import services.CRUDTypeService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
public class AddTypesController implements Initializable {

    @FXML
    private Button btn_ov;
    @FXML
    private Button btn_serv;
    @FXML
    private Button btn_types;
    @FXML
    private TextField tf_ID;
    @FXML
    private TextField tf_Nom;
    @FXML
    private Button btn_add;
    @FXML
    private TextField tf_desc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void save_service(ActionEvent event) {
        int ID = Integer.parseInt(tf_ID.getText());
            String nom = tf_Nom.getText();
            String desc = tf_desc.getText();
            TypeService TS = new TypeService(nom, desc);
            CRUDTypeService crud_s = new CRUDTypeService();
            crud_s.addTypeServices(TS);
            
            //change page
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TypesTables.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_add.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }
    
}
