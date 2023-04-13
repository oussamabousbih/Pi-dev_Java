/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.UserManager;
import main.services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import main.services.gestion_rdv.CrudDummyUser;




/**
 *
 * @author rbaih
 */
public class DummyLoginController extends BaseController implements Initializable{
    
    @FXML
    private TextField textField_userId;

    public DummyLoginController(UserManager userManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, fxmlName, viewFactory);
    }

    @FXML
    void onActionLogin(ActionEvent event)  {
        
        System.out.println("hello world i am working \njjjjjjjjjjj\njjjjjjjjjjjj\njjjjjjjjjjjjjjjjjj\njjjjjjjjjjjjjjjjjjjjj");
        int user_id = Integer.parseInt(textField_userId.getText());
         try {
             userManager.setLogged_User(new CrudDummyUser().get_dummyUserData(user_id));
         } catch (ConnectionOrPrepareStatmentException ex) {
             Logger.getLogger(DummyLoginController.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         if(userManager.getLogged_User()!=null){
             
            Stage currentStage =(Stage) textField_userId.getScene().getWindow();
            if(userManager.getLogged_User().getRoles().contains("\"ROLE_USER\""))
                viewFactory.showDoctors_list_keepStage(currentStage);
            
            if(userManager.getLogged_User().getRoles().contains("\"ROLE_DOCTOR\""))
                viewFactory.showDoctorCalandars_list_keepStage(currentStage);
            
         }else
             textField_userId.setText("id intered does not exist inter a valid id please !");
     
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
