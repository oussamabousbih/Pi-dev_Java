/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import main.controller.BaseController;
import main.controller.gestion_rdv.services.SettingUpTableView;
import main.entities.DummyUser;
import main.entities.gestion_rdv.Appointment;
import main.entities.gestion_rdv.DummyPatientAppointment;
import main.services.gestion_rdv.CrudAppointment;
import main.services.gestion_rdv.CrudPatientAppointment;
import main.services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import main.services.gestion_rdv.exceptions.DeleteException;
import main.services.gestion_rdv.exceptions.SelectExecuteQueryException;

/**
 * FXML Controller class
 *
 * @author rbaih
 */
public class AppointmentUserController extends BaseController implements Initializable {
    
    @FXML
    private TableView<DummyPatientAppointment> tableView_appointment;

    @FXML
    private Pane operationsPane;

    @FXML
    private Label label_msg;

    @FXML
    private Button btn_update;

    @FXML
    private ChoiceBox<String> choiceBox_reason;

    @FXML
    private ChoiceBox<String> choiceBox_bookingState;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

    @FXML
    private Button btn_doctorsGo;

//    @FXML not necessary for user 
//    private Button btn_calandarGo;
    
    //data to store
    int index_Selectd_Appointment;

    public AppointmentUserController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);
    }

   

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete appointment  .", ButtonType.APPLY, ButtonType.CANCEL);

            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                return;
            }

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.CANCEL);
            // necessary to look OOP for Crud
            Appointment appointment = rdvManager.getSelectedDummyPatientAppointment().convertToAppointment();
            if (new CrudAppointment().DeleteAppointment(appointment)) {
                // alert modify for sucess
//                alert.setContentText("Successfully Deleted .");
                System.out.println("index appointment to delete debug just befor Delete :" + index_Selectd_Appointment);
                tableView_appointment.getItems().remove(index_Selectd_Appointment);
                tableView_appointment.refresh();

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Delete Failed .");
            }
            // showing anyway
            alert.showAndWait();
        } catch (DeleteException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    void onActionUpdateAppointment(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //hide inputs and setting elements( appointment-timeslot)
        this.initialize_inputsDeleteNotifyAppointment();

      
        //Appointment table view / calandar selected
        this.setAppointmentTableView();
    }
    
    // private method appointment
    // private method appointment
    // private method appointment
    // private method appointment
    private void setAppointmentTableView() {
        //------------List-Appointments------------------------------------------------------------------------------
        //TableView creation for our calandarDay' TimeSlots
        try {

            CrudPatientAppointment crudAppPatient = new CrudPatientAppointment();
            DummyUser currentUser = userManager.getLogged_User();
            Collection<DummyPatientAppointment> appointments = new CrudPatientAppointment().get_DummyPatientAppointment_user(currentUser);
            rdvManager.setCollecPatientappointments(appointments);
            System.out.println("data to fill tableView Appointments => " + appointments);
            //*******start_event_handler********
            EventHandler<MouseEvent> customMouseEvent = (event) -> {
                if (event.getClickCount() == 1) {
                    //gui show (buttons delete and notify)
                    operationsPane.setVisible(true);

                    //store the selection and the index in table
                    index_Selectd_Appointment = tableView_appointment.getSelectionModel().getSelectedIndex();
                    System.out.println("index Appointment selected Saved :" + index_Selectd_Appointment);

                    DummyPatientAppointment selcted_DummyPatientappointment = tableView_appointment.getSelectionModel().getSelectedItem();
                    rdvManager.setSelectedDummyPatientAppointment(selcted_DummyPatientappointment);
                    
                }
            };
            //*****end event-handler*********
            Field[] fields = DummyPatientAppointment.class.getDeclaredFields(); // necessary for autoGenerating my Table headers and Columns
            int firstFieldsColumnToHide = 0;
            SettingUpTableView.Tableview_SettingUp(tableView_appointment, appointments, customMouseEvent, fields, firstFieldsColumnToHide);

        } catch (ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        }

    }

    //****************
    private void initialize_inputsDeleteNotifyAppointment() {
        //hide buttons before table selection( appointment )
        operationsPane.setVisible(false);

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
