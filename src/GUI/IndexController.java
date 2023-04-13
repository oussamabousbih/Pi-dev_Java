/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mazee
 */
public class IndexController implements Initializable {

    @FXML
    private Button fxliste;
    @FXML
    private Button fxajout;
    @FXML
    private Button fxsupprimer;
    @FXML
    private Button fxmodifier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

       
    }    

    @FXML
    private void handleDeleteBtn(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Delete.FXML"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Supprimer Article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleListeBtn(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("DetailA.FXML"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Liste Articles");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleAddBtn(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("AjouterA.FXML"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Ajouter Article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleUpdateBtn(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ModifierA.FXML"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Modifier Article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
