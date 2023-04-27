/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import controller.BaseController;
import utils.SettingUpListView;
import entities.DummyUser;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import services.gestion_rdv.CrudDoctorWithCalandars;

/**
 *
 * @author rbaih
 */
public class Doctors_listController extends BaseController implements Initializable {
    
    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

    @FXML
    private Button btn_doctorsGo;

//    @FXML
//    private Button btn_calandarGo;
    
    @FXML
    private ListView<DummyUser> userListView;

    public Doctors_listController(UserManager userManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, fxmlName, viewFactory);
    }


    public Doctors_listController(UserManager userManager, RdvManager rdvManager, ViewFactory viewFactory, String fxmlName) {
        super(userManager, rdvManager, fxmlName, viewFactory );
    }
    
   
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // creating the doctor ListView
        try {
            CrudDoctorWithCalandars doctorWithCalandars = new CrudDoctorWithCalandars();
            Collection <DummyUser> doctors = doctorWithCalandars.SelectDoctors_withCalandarDays();
            //<Event> because i have a button that Does NOt accept <MouseEvent>
            EventHandler<MouseEvent> customEvent = (event)->{
                // storing data to pass on UserManagement
                if (event.getClickCount() == 2) {
                    DummyUser doctor = userListView.getSelectionModel().getSelectedItem();
                    userManager.setSelectedDoctor(doctor);
                    System.out.println("selecting => "+ userManager.getSelectedDoctor() );
                    
                    Stage currentStage = (Stage) userListView.getScene().getWindow();
                    viewFactory.showUserSideCalandars_list_keepStage(currentStage);
                }
            }; 
            SettingUpListView.listObjectsOn_listView(userListView,doctors ,customEvent);
//            SettingUpListView.list_view_with_buttonActionListener(userListView,doctors ,customEvent,"GoTo Calandar");
        } catch (ConnectionOrPrepareStatmentException ex) {
            Logger.getLogger(Doctors_listController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  //end-initialize  
    


       
     //----------navigation-bar
    //----------navigation-bar
    //----------navigation-bar
    //----------navigation-bar
    //----------navigation-bar
    //----------navigation-bar
    //----------navigation-bar
    @FXML
    void onActionGOToAppointments(ActionEvent event) {
        System.out.println("implement onActionGOToAppointments when ready .");  
        Stage currentStage =(Stage) btn_appointmentGo.getScene().getWindow();
        viewFactory.show_UserSideAppointment_list_keepStage(currentStage);
    }

//    @FXML // not necessary for user
//    void onActionGoToCalandar(ActionEvent event) {
//        Stage currentStage =(Stage) btn_calandarGo.getScene().getWindow();
//        viewFactory.showDoctorCalandars_list_keepStage(currentStage);
//        
//    }

    @FXML
    void onActionGoToDoctors(ActionEvent event) {
        Stage currentStage =(Stage) btn_doctorsGo.getScene().getWindow();
        viewFactory.showDoctors_list_keepStage(currentStage);
    }

    @FXML
    void onActionGoToHome(ActionEvent event) {
        Stage currentStage =(Stage) btn_homeGo.getScene().getWindow();
        viewFactory.showLogin_keepStage(currentStage);
    }

    
    
    
    
}


