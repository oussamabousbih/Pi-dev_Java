/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import main.controller.BaseController;
import main.entities.gestion_rdv.Appointment;

/**
 * FXML Controller class
 *
 * @author rbaih
 */
public class AppointmentDoctorController extends BaseController implements Initializable {

    @FXML
    private TableView<Appointment> tableView_appointment;
    
    @FXML
    private TextField tf_nameSearch;

    @FXML
    private TextField tf_emailSearch;

    @FXML
    private TextField tf_phoneSearch;

    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

//    @FXML
//    private Button btn_doctorsGo;

    @FXML
    private Button btn_calandarGo;

    public AppointmentDoctorController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);
    }

    @FXML
    void onActionDelete(ActionEvent event) {

    }


    @FXML
    void onActionNotify(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    
    
       
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
        viewFactory.show_All_DoctorSideAppointment_list_keepStage(currentStage);
    }

    @FXML
    void onActionGoToCalandar(ActionEvent event) {
        Stage currentStage =(Stage) btn_calandarGo.getScene().getWindow();
        viewFactory.showDoctorCalandars_list_keepStage(currentStage);
        
    }

//    @FXML
//    void onActionGoToDoctors(ActionEvent event) {
//        Stage currentStage =(Stage) btn_doctorsGo.getScene().getWindow();
//        viewFactory.showDoctors_list_keepStage(currentStage);
//    }

    @FXML
    void onActionGoToHome(ActionEvent event) {
        Stage currentStage =(Stage) btn_homeGo.getScene().getWindow();
        viewFactory.showLogin_keepStage(currentStage);
    }

}
