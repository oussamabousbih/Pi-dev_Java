/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.TypeService;
import services.CRUDTypeService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class UpdateTypesController implements Initializable {

    @FXML
    private TextField ID;
    @FXML
    private TextField Nom_serv;
    @FXML
    private Button update;
    @FXML
    private TextField desc;
    @FXML
    private Button ov;
    @FXML
    private Button serv_page;
    @FXML
    private Button btn_type;
    private TypeService selectedTypeService;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //to do
    }    

    @FXML
    private void update(ActionEvent event) {
        int id = Integer.parseInt(ID.getText());
        String NameType = Nom_serv.getText();
        String desccription = desc.getText();

        TypeService type_S = new TypeService(id, NameType, desccription);
        CRUDTypeService crud = new CRUDTypeService();
        crud.updateTypeServices(type_S);
        System.out.println("Le truc trac avec l'ID " + id + " a été modifié dans la base de données");

        // redirect to another page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TypesTables.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initData(TypeService type_service) {
        ID.setText(String.valueOf(type_service.getId()));
        Nom_serv.setText(type_service.getNomType());
        desc.setText(type_service.getDescription());
    }

    @FXML
    private void home(ActionEvent event) {
        
    }

    @FXML
    private void serv_page(ActionEvent event) {
    }

    @FXML
    private void type_page(ActionEvent event) {
    }
    
}
