/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import main.controller.BaseController;
import main.controller.gestion_rdv.services.MyDatePickerUtil;
import main.controller.gestion_rdv.services.MyLabelUtil;
import main.controller.gestion_rdv.services.MySpinnerUtil;
import main.controller.gestion_rdv.services.SettingUpListView;
import main.controller.gestion_rdv.services.SettingUpTableView;
import main.entities.gestion_rdv.CalandarDay;
import main.services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import main.services.gestion_rdv.CrudCalandarDay;
import main.services.gestion_rdv.exceptions.DeleteException;
import main.services.gestion_rdv.exceptions.InsertException;
import main.services.gestion_rdv.exceptions.SelectExecuteQueryException;

/**
 *
 * @author rbaih
 */
public class UserSideCalandars_listController extends BaseController implements Initializable {

   
    private DatePicker dp_date;
    @FXML
    private ListView<CalandarDay> listViewCalandars;
   
    
    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

    @FXML
    private Button btn_doctorsGo;

    @FXML
//    private Button btn_calandarGo;
    
    // data to work with 
    private int indexSelectedCalandar_ObservableList;

    public UserSideCalandars_listController(UserManager userManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, fxmlName, viewFactory);
    }

    public UserSideCalandars_listController(RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(rdvManager, fxmlName, viewFactory);
    }

    public UserSideCalandars_listController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);
    }
    
    //****************************
    //****Initialize********
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    
        if(this.userManager.getLogged_User().getRoles().contains("\"ROLE_USER\""))
            this.initialize_If_User_NotDoctor();
       
    }// end Initialize *************************************************************************************************************


    private void initialize_If_User_NotDoctor(){
        
         //--------------ListView--------------------------------------------------
        //list View creation for our calandarDay
        try {
            // getting the doctor responsibe for the selected calandar
            int doctor_id = userManager.getSelectedDoctor().getId();
            //filling up the ListView With Calandars
            Collection<CalandarDay> calandars
                    = new CrudCalandarDay().getCalandar_view_greaterEqualDateOptional(doctor_id, true, null);
            EventHandler<Event> customEvent = (event) -> {
                // store selected item inside rdvManagerAttribute
                rdvManager.setSelectedCalandar(listViewCalandars.getSelectionModel().getSelectedItem());
                System.out.println("selecting ==> " + rdvManager.getSelectedCalandar());
                Stage currentStagr = (Stage) this.listViewCalandars.getScene().getWindow();
//                viewFactory.showCalandarTimeSlotsDoctorView_list_keepStage(currentStagr);
                viewFactory.showCalandarTimeSlotsPatientView_list_keepStage(currentStagr);

            };
            SettingUpListView.listObjectsOn_listView(listViewCalandars, calandars, customEvent);

           

        } catch (ConnectionOrPrepareStatmentException | SelectExecuteQueryException ex) {
            ex.printStackTrace();
        }

    }// endi initialize if user

   
  
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

//    @FXML  // user side no need 
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
