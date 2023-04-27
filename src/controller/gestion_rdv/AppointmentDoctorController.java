 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import controller.BaseController;
import controller.gestion_rdv.services.EmailSendHtmlMsg;
import utils.MyLabelUtil;
import utils.SettingUpTableView;
import entities.DummyUser;
import entities.gestion_rdv.Appointment;
import entities.gestion_rdv.CalandarDay;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.gestion_rdv.CrudAppointment;
import services.gestion_rdv.CrudPatientAppointment;
import services.gestion_rdv.CrudTimeSlot;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import services.gestion_rdv.exceptions.DeleteException;
import services.gestion_rdv.exceptions.SelectExecuteQueryException;
import services.gestion_rdv.exceptions.UpdateException;

/**
 * FXML Controller class
 *
 * @author rbaih
 */
public class AppointmentDoctorController extends BaseController implements Initializable {

    @FXML
    private TableView<DummyPatientAppointmentDoctorSide> tableView_appointment;

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

    @FXML
    private CheckBox checkBox_orderBy;

    @FXML
    private CheckBox checkBox_FullList;

    @FXML
    private Pane pane_buttons;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_notify;

    @FXML
    private Label label_msg_appointment;

    // data to work with :
    int indexPatientAppointmentSelected;
    private Timer timer;

//    @FXML
//    private Button btn_doctorsGo;
    @FXML
    private Button btn_calandarGo;

    public AppointmentDoctorController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);

    }

    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            DummyPatientAppointmentDoctorSide PatientAndappointment = rdvManager.getSelectedDummyPatientAppointmentDoctorSide();
            String dateApp = PatientAndappointment.getDate().format(DateTimeFormatter.ISO_DATE);
            String timepp = PatientAndappointment.getTime().toString();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete appointment " + dateApp + " @" + timepp, ButtonType.APPLY, ButtonType.CANCEL);

            MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment,"");
            MyLabelUtil.LoadingGaraphicMsg(label_msg_appointment, "Deleting Appointment ... ");
            
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                label_msg_appointment.setVisible(false);
                return;
            }

            Appointment appointment = rdvManager.getSelectedDummyPatientAppointmentDoctorSide().convertToAppointment();

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().clear();
            // necessary to look OOP for Crud
            
            
            if (new CrudAppointment().DeleteAppointment(appointment)) {
                // alert modify for sucess
                
                alert.setContentText("Successfully Deleted .");
                alert.getButtonTypes().add(ButtonType.OK);

                //Email-Send    
                //Email-Send    
                //Email-Send    
                //Email-Send    
                //sending email notification before  cleaning the deleted data to avoid null pointer exception
                CalandarDay calander = new CalandarDay();
                calander.setId(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getCalandar_day_id());
                calander.setDate(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getDate());
                EmailSendHtmlMsg.sendEmail_Notification_Delete(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide(),
                        calander, userManager.getLogged_User());

                // Updating TimeSlot Booking Fields
                int timeSlot_id = rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getTime_slot_id();
                new CrudTimeSlot().update_timeSlot_afterAppointmentDeleted(timeSlot_id);

                //Cleaning the Table From the deleted Item ater deleting
                System.out.println("index appointment to delete debug just befor Delete :" + indexPatientAppointmentSelected);

                // cleaning
                boolean remove = tableView_appointment.getItems().remove(rdvManager.getSelectedDummyPatientAppointmentDoctorSide());

                rdvManager.setSelectedDummyPatientAppointmentDoctorSide(null);
                rdvManager.setSelectedAppointment(null);
                if (remove == true) {
                    System.out.println("successfully removed  Patientappointment From tableView");
                }
                // lbel msg
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment, "Appointment on " + dateApp + " @" + timepp + " deleted .");
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.getButtonTypes().add(ButtonType.OK);
                alert.setContentText("Delete Failed .");
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_msg_appointment, "Failed to delete appointment on " + dateApp + " @" + timepp);
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);
            }
            //show in any case fail or success
            alert.showAndWait();
        } catch (DeleteException | UpdateException ex) {
            ex.printStackTrace();
        }

        //------ clearing selection and hide Buttons again 
        this.clear_selection_andDisableButton();

    }

    @FXML
    void onActionNotify(ActionEvent event) {

        DummyPatientAppointmentDoctorSide PatientAndappointment = rdvManager.getSelectedDummyPatientAppointmentDoctorSide();
        String dateApp = PatientAndappointment.getDate().format(DateTimeFormatter.ISO_DATE);
        String timepp = PatientAndappointment.getTime().toString();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Notify Patient appointment " + dateApp + " @" + timepp, ButtonType.APPLY, ButtonType.CANCEL);
        
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment,"");
        MyLabelUtil.LoadingGaraphicMsg(label_msg_appointment, "Notifying patient ... ");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.CANCEL) {
            label_msg_appointment.setVisible(false);
            return;
        }
        //Email-Send    
        //Email-Send    
        //Email-Send    
        //sending email notification before  cleaning the deleted data to avoid null pointer exception
        CalandarDay calander = new CalandarDay();
        calander.setId(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getCalandar_day_id());
        calander.setDate(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getDate());
        EmailSendHtmlMsg.sendEmail_Notification_Recall(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide(),
                calander, userManager.getLogged_User());

        //------ clearing selection and hide Buttons again 
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment, "Patient have been notified.");
        MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);

        this.clear_selection_andDisableButton();
    }

    private void clear_selection_andDisableButton() {
        tableView_appointment.getSelectionModel().clearSelection();
        this.DisableButton_notify_delete();
    }

    private void DisableButton_notify_delete() {
        btn_notify.setDisable(true);
        btn_delete.setDisable(true);

    }

    private void enableButton_notify_delete() {
        btn_notify.setDisable(false);
        btn_delete.setDisable(false);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize_research_fields_AndEventsRelated();

        setUpTableView_appointmentPatientData();

    }

//---PrivateMethods
//---PrivateMethods
//---PrivateMethods
//---PrivateMethods
//---PrivateMethods
    // table View setting 
    private void setUpTableView_appointmentPatientData() {
        try {

            DummyUser doctor = userManager.getLogged_User();

            Collection<DummyPatientAppointmentDoctorSide> appointments = new CrudPatientAppointment()
                    .get_DummyPatientAppointment_doctor(doctor, !checkBox_FullList.isSelected(),
                            tf_nameSearch.getText(), tf_emailSearch.getText(), tf_phoneSearch.getText());

            EventHandler<MouseEvent> customEventHandler = (event) -> {
                if (event.getClickCount() == 1) {
                    //store index 
                    indexPatientAppointmentSelected = tableView_appointment.getSelectionModel().getSelectedIndex();
                    //store Selected PatientAppointment
                    DummyPatientAppointmentDoctorSide patientAppointment = tableView_appointment.getSelectionModel().getSelectedItem();
                    rdvManager.setSelectedDummyPatientAppointmentDoctorSide(patientAppointment);

                    //do not allow notify or delete for old Appointments
                    if (patientAppointment.getDate().compareTo(LocalDate.now()) < 0) {
                        this.DisableButton_notify_delete();
                    } else {
                        this.enableButton_notify_delete();
                    }

                }
            };
            Field[] fields = DummyPatientAppointmentDoctorSide.class.getDeclaredFields();
            int firstFieldsToHide = 5;
            SettingUpTableView.Tableview_SettingUp(tableView_appointment, appointments, customEventHandler, fields, firstFieldsToHide);

        } catch (ConnectionOrPrepareStatmentException | SelectExecuteQueryException ex) {
            ex.printStackTrace();
        }

    }

    //clear table and pass new Array
    private void clear_TableViewAnd_Update_NewResult() {

        try {
            DummyUser doctor = userManager.getLogged_User();

            Collection<DummyPatientAppointmentDoctorSide> appointments = new CrudPatientAppointment()
                    .get_DummyPatientAppointment_doctor(doctor, !checkBox_FullList.isSelected(),
                            tf_nameSearch.getText(), tf_emailSearch.getText(), tf_phoneSearch.getText());

            tableView_appointment.getItems().clear();
            tableView_appointment.getItems().addAll(appointments);

        } catch (ConnectionOrPrepareStatmentException | SelectExecuteQueryException ex) {
            ex.printStackTrace();
        }
    }

    //initialise_fields
    private void initialize_research_fields_AndEventsRelated() {
        checkBox_FullList.setSelected(false);
        // more if needed 
        nameSearch_ActionListener();
        emailSearch_ActionListener();
        phoneSearch_ActionListener();
        CheckBox_FullList_ActionListener();

    }

    // action listener name textfield
    private void nameSearch_ActionListener() {

        // assure one TImerOnly used
        if (timer != null) {
            timer.cancel();
        }
        // Initialize the timer
        timer = new Timer();

        // Add a listener to the text property of the search field
        tf_nameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            // Restart the timer each time the user types a new character
            timer.cancel();
            timer = new Timer();
            // clear other fields textField
            tf_phoneSearch.clear();
            tf_emailSearch.clear();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    //update table based on the input fields
                    clear_TableViewAnd_Update_NewResult();

                    //at the end closing timer to avoid weird behavior
                    timer.cancel();
                }
            }, 650);
        });
    }

    //-------------------------------------------------------------------------------------------------
    // action listener email textfield
    private void emailSearch_ActionListener() {
        // assure one TImerOnly used
        if (timer != null) {
            timer.cancel();
        }
        // Initialize the timer
        timer = new Timer();

        // Add a listener to the text property of the search field
        tf_emailSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            // Restart the timer each time the user types a new character
            timer.cancel();
            timer = new Timer();
            // clear other fields textField
            tf_phoneSearch.clear();
            tf_nameSearch.clear();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    //update table based on the input fields
                    clear_TableViewAnd_Update_NewResult();

                    //at the end closing timer to avoid weird behavior
                    timer.cancel();
                }
            }, 650);
        });
    }

    //-------------------------------------------------------------------------------------------------
    // event listener phone textfield
    private void phoneSearch_ActionListener() {
        // assure one TImerOnly used
        if (timer != null) {
            timer.cancel();
        }
        // Initialize the timer
        timer = new Timer();

        // Add a listener to the text property of the search field
        tf_phoneSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            // Restart the timer each time the user types a new character
            timer.cancel();
            timer = new Timer();
            // clear other fields textField
            tf_nameSearch.clear();
            tf_emailSearch.clear();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    //update table based on the input fields
                    clear_TableViewAnd_Update_NewResult();

                    //at the end closing timer to avoid weird behavior
                    timer.cancel();
                }
            }, 650);
        });
    }

    //-------------------------------------------------------------------------------------------------
    // event listener checkbox textfield
    private void CheckBox_FullList_ActionListener() {

        // assure one TImerOnly used
        if (timer != null) {
            timer.cancel();
        }
        // Initialize the timer
        timer = new Timer();

        // Add a change listener to the CheckBox's selected property
        checkBox_FullList.selectedProperty().addListener((observable, oldValue, newValue) -> {
            // Restart the timer each time the user types a new character
            timer.cancel();
            timer = new Timer();

            if (newValue != oldValue) {
                // CheckBox state has changed, start the timer
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        //update table based on the input fields
                        clear_TableViewAnd_Update_NewResult();

                        //at the end closing timer to avoid weird behavior
                        timer.cancel();
                    }
                }, 650);
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------------------------
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
        Stage currentStage = (Stage) btn_appointmentGo.getScene().getWindow();
        viewFactory.show_All_DoctorSideAppointment_list_keepStage(currentStage);
    }

    @FXML
    void onActionGoToCalandar(ActionEvent event) {
        Stage currentStage = (Stage) btn_calandarGo.getScene().getWindow();
        viewFactory.showDoctorCalandars_list_keepStage(currentStage);

    }

//    @FXML
//    void onActionGoToDoctors(ActionEvent event) {
//        Stage currentStage =(Stage) btn_doctorsGo.getScene().getWindow();
//        viewFactory.showDoctors_list_keepStage(currentStage);
//    }
    @FXML
    void onActionGoToHome(ActionEvent event) {
        Stage currentStage = (Stage) btn_homeGo.getScene().getWindow();
        viewFactory.showLogin_keepStage(currentStage);
    }

}
