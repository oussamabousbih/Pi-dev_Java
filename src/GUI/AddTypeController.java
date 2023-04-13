/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.TypesServices;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CRUDTypesServices;

/**
 * FXML Controller class
 *
 * @author Yasmine Rajhi
 */
public class AddTypeController implements Initializable {

    @FXML
    private TextField TFNomTS;
    @FXML
    private TextField TFDescTS;
    @FXML
    private Button BTAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save_type(ActionEvent event) {
        String Nom = TFNomTS.getText();
        String Desc = TFDescTS.getText();
        
        TypesServices TS = new TypesServices(Nom,Desc);
        CRUDTypesServices TC = new CRUDTypesServices();
        TC.addTypeServices(TS);
    }
    
}
