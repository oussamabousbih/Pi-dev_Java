/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import controller.BaseController;
import controller.gestion_rdv.services.EmailSendHtmlMsg;
import controller.gestion_rdv.services.MySmsSender;
import utils.MyLabelUtil;
import utils.MyTextAreaUtil;
import utils.SettingUpTableView;
import entities.gestion_rdv.Appointment;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import entities.gestion_rdv.TimeSlot;
import entities.gestion_rdv.enums.TimeSlotReason;
import entities.gestion_rdv.enums.TimeSlotStatus;
import services.gestion_rdv.CrudAppointment;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import services.gestion_rdv.CrudPatientAppointment;
import services.gestion_rdv.CrudTimeSlot;
import services.gestion_rdv.exceptions.DeleteException;
import services.gestion_rdv.exceptions.SelectExecuteQueryException;
import services.gestion_rdv.exceptions.UpdateException;

 

//*************
/**
 *
 * @author rbaih
 */
public class DoctorCalandarTimeSlots_listController extends BaseController implements Initializable {

    public DoctorCalandarTimeSlots_listController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);
    }

    @FXML
    private Pane paneTimeSlot;
    @FXML
    private Pane paneAppointment;

    @FXML
    private Label label_msg_appointment;
    @FXML
    private Label label_msg_slots;

    @FXML
    private Button btn_update;

    @FXML
    private ChoiceBox<String> choiceBox_status;

    @FXML
    private ChoiceBox<String> choiceBox_reason;

    @FXML
    private TextArea textArea_note;

    @FXML
    private Button btn_notify;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

//    @FXML
//    private Button btn_doctorsGo;
    @FXML
    private Button btn_calandarGo;

    @FXML
    private Button btn_back;

    @FXML
    private Label label_date;

    @FXML
    private TableView<TimeSlot> tableView_slot;

    @FXML
    private TableView<DummyPatientAppointmentDoctorSide> tableView_appointment;
//data to workwith
    Integer index_Selectd_TimeSlot;
    Integer index_Selectd_Appointment;

    @FXML
    void onActionBackToCalandarList(ActionEvent event) {
        // clearing what we selected earlier
        rdvManager.Clear_setToNull_AllSelectedFields();
        System.out.println("back to calandarList");
        Stage currentStage = (Stage) this.btn_back.getScene().getWindow();
        viewFactory.showDoctorCalandars_list_keepStage(currentStage);

        this.resetFields_of_CurrentController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hide inputs and setting elements( appointment-timeslot)
        this.initialize_inputsUpdateTimeSlot();
        this.initialize_inputsDeleteNotifyAppointment();

        // set Date of Calandar for the page
        LocalDate date = rdvManager.getSelectedCalandar().getDate();
        label_date.setText("Date: " + date.format(DateTimeFormatter.ISO_DATE));

        // timeSlots table view / calandar selected
        this.setTimeSlotTableView();

        //Appointment table view / calandar selected
        this.setAppointmentTableView();

    }// end-initialize

    //TimeSlot Code --------------------------------------------------------------------------------------------
    //TimeSlot Code --------------------------------------------------------------------------------------------
    //TimeSlot Code --------------------------------------------------------------------------------------------
    @FXML
    void onActionUpdateTimeSlot(ActionEvent event) {

        rdvManager.getSelectedTimeSlot().setNote(textArea_note.getText());
        rdvManager.getSelectedTimeSlot().setStatus(choiceBox_status.getValue());
        rdvManager.getSelectedTimeSlot().setReason(choiceBox_reason.getValue());
        String timeSlot_hour = rdvManager.getSelectedTimeSlot().getStart_time().toString();

        try {
            // assuming success from the start
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TimeSlot @" + timeSlot_hour + " Updated Successfully .", ButtonType.OK);
            if (new CrudTimeSlot().update_timeSlot(rdvManager.getSelectedTimeSlot(), true)) {
                tableView_slot.getItems().set(index_Selectd_TimeSlot, rdvManager.getSelectedTimeSlot());
                //label msg
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_slots,"Slot @" + timeSlot_hour + " Successfully Updated .");
                MyLabelUtil.hideLabelAfter(label_msg_slots, 3);

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Update Failed .");
                //labelmsg
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_msg_slots, "Slot @" + timeSlot_hour + " failed to update ." );
                MyLabelUtil.hideLabelAfter(label_msg_slots, 3);
            }
            // showing anyway
            alert.show();

        } catch (UpdateException ex) {
            ex.printStackTrace();
        }

        this.unselectTable_hide_paneSlotsFields();

    }
    //private methods TimeSlot
    //private methods TimeSlot
    //private methods TimeSlot

    private void setTimeSlotTableView() {
        //------------TableView-Listing-TimeSlots------------------------------------------------------------------------------
        //TableView creation for our calandarDay' TimeSlots
        try {

            int calandar_day_id = rdvManager.getSelectedCalandar().getId();
            CrudTimeSlot crudTS = new CrudTimeSlot();
            Collection<TimeSlot> slots = crudTS.getTimeSlotsBelongsTo_calandar_Day(calandar_day_id, false);
            rdvManager.setCollecTimeSlots(slots);

            System.out.println("data to fill tableView Slots=> " + slots);
            //****Event-handler-code********************
            EventHandler<MouseEvent> customMouseEvent = (event) -> {
                if (event.getClickCount() == 1) {
                    //store index of selected item
                    index_Selectd_TimeSlot = tableView_slot.getSelectionModel().getSelectedIndex();
                    TimeSlot selected_timeSlot = tableView_slot.getSelectionModel().getSelectedItem();
                    // storing the selected timeSlot
                    rdvManager.setSelectedTimeSlot(selected_timeSlot);

                    fillUpData_updateTimeSlot_andSetVisible(selected_timeSlot.getNote(),
                            selected_timeSlot.getStatus(), false, selected_timeSlot.getReason(), false);

                    //case if timeSlot is booked by patient we don't want to change some attribute(status,reason)) of timeSlot
                    if (selected_timeSlot.getAppointment() == null) {
                        fillUpData_updateTimeSlot_andSetVisible(selected_timeSlot.getNote(),
                                selected_timeSlot.getStatus(), true, selected_timeSlot.getReason(), true);
                    }

                }
            };
            //******End-Event-Handler**********************
            Field[] fields = TimeSlot.class.getDeclaredFields();
            int firstFieldsColumnToHide = 4;
            SettingUpTableView.Tableview_SettingUp(tableView_slot, slots, customMouseEvent, fields, firstFieldsColumnToHide);

        } catch (ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        }

    }

    //****************************
    private void initialize_inputsUpdateTimeSlot() {
        //hide buttons before table selection( timeslot)
        paneTimeSlot.setVisible(false);

        // set text limit for text area
        MyTextAreaUtil.LimitTextLengh_plus_ValidateInput_JsSqlInjection(textArea_note, 600);
        //fill up choice boxes for forcing input
        choiceBox_reason.getItems().addAll(TimeSlotReason.BOOKED.getName(), TimeSlotReason.UNBOOKED.getName(), TimeSlotReason.LUNCH_TIME.getName());
        choiceBox_status.getItems().addAll(TimeSlotStatus.AVAILABLE.getName(), TimeSlotStatus.NOT_AVAILABLE.getName());
        choiceBox_reason.setValue(TimeSlotReason.BOOKED.getName());
        choiceBox_status.setValue(TimeSlotStatus.AVAILABLE.getName());

    }

    /**
     *
     * @param note string note
     * @param Status string status
     * @param hideStatus boolean hideStatus
     * @param reason string reason
     * @param hideReason boolean hideReason
     */
    private void fillUpData_updateTimeSlot_andSetVisible(String note, String Status, boolean showStatus, String reason, boolean showReason) {
        paneTimeSlot.setVisible(true);
        textArea_note.setText(note);
        choiceBox_reason.setValue(reason);
        choiceBox_reason.setVisible(showReason);
        choiceBox_status.setValue(Status);
        choiceBox_status.setVisible(showStatus);

    }

    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    //Appointment  code ----------------------------------------------------------------------------------------------
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

        try {
            DummyPatientAppointmentDoctorSide PatientAndappointment = rdvManager.getSelectedDummyPatientAppointmentDoctorSide();
            String dateApp = PatientAndappointment.getDate().format(DateTimeFormatter.ISO_DATE);
            String timepp = PatientAndappointment.getTime().toString();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete appointment " + dateApp + " @" + timepp, ButtonType.APPLY, ButtonType.CANCEL);

            //showing msg
            String msg = "Deleting .. Sending Email Notifier";
            MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment, msg);
            MyLabelUtil.LoadingGaraphicMsg(label_msg_appointment, "Sending Email");

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
                //sending email notification before  cleaning the deleted data to avoid null pointer exception
                EmailSendHtmlMsg.sendEmail_Notification_Delete(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide(),
                        rdvManager.getSelectedCalandar(), userManager.getLogged_User());

                // show notification label after email
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment, " Email successfully sent.");
                MyLabelUtil.LoadingGaraphicMsg(label_msg_appointment, "sending SMS");
                

                //sending SMS
                // Example code for sending a Twilio message
                // Your Twilio message sending code here
                String smspartMsg = "We inform you that we canceled your appointment with Dr " + userManager.getLogged_User().getLast_name().toUpperCase() + " on ";
                //sendSms ==>
                boolean emailSuccsees = MySmsSender.sendEmailToDummyPatientsByDoctor(new DummyPatientAppointmentDoctorSide[]{rdvManager.getSelectedDummyPatientAppointmentDoctorSide()},
                        smspartMsg);
                

                // Updating TimeSlot Booking Fields
                int timeSlot_id = rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getTime_slot_id();
                new CrudTimeSlot().update_timeSlot_afterAppointmentDeleted(timeSlot_id);

                //Cleaning the Table From the deleted Item ater deleting
                System.out.println("index appointment to delete debug just befor Delete :" + index_Selectd_Appointment);

                // cleaning
                boolean remove = tableView_appointment.getItems().remove(rdvManager.getSelectedDummyPatientAppointmentDoctorSide());

                rdvManager.setSelectedDummyPatientAppointmentDoctorSide(null);
                rdvManager.setSelectedAppointment(null);
                if (remove == true) {
                    System.out.println("successfully removed  Patientappointment From tableView");
                }
                
                // lbel msg
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment, "Appointment on " + dateApp + " @" + timepp + "is deleted and user has been notified.");
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);
                //show in any case fail
                alert.showAndWait();
                //------ clearing selection and hide Buttons again 
                this.unselectTable_hide_paneAppointmentFields();
                
                
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.getButtonTypes().add(ButtonType.OK);
                alert.setContentText("Delete Failed .");
                //show in any case fail
                alert.showAndWait();
                // lbel msg
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_msg_appointment, "Failed to delete appointment on " + dateApp + " @" + timepp);
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);
                return;
            }

        } catch (DeleteException | UpdateException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void onActionNotifyAppointment(ActionEvent event) {

        DummyPatientAppointmentDoctorSide PatientAndappointment = rdvManager.getSelectedDummyPatientAppointmentDoctorSide();
        String dateApp = PatientAndappointment.getDate().format(DateTimeFormatter.ISO_DATE);
        String timepp = PatientAndappointment.getTime().toString();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Notify Patient appointment " + dateApp + " @" + timepp, ButtonType.APPLY, ButtonType.CANCEL);

        //showing msg
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment," Sending Email .");
        MyLabelUtil.LoadingGaraphicMsg(label_msg_appointment, "sending email" );

        alert.showAndWait();
        if (alert.getResult() == ButtonType.CANCEL) {
            label_msg_appointment.setVisible(false);
            return;
        }

        //Email-Send    
        //Email-Send    
        //Email-Send   
        //sending email notification before  cleaning the deleted data to avoid null pointer exception
        EmailSendHtmlMsg.sendEmail_Notification_Recall(this.rdvManager.getSelectedDummyPatientAppointmentDoctorSide(),
                rdvManager.getSelectedCalandar(), userManager.getLogged_User());

        // show notification label after email
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment," Email successfully sent." );
        MyLabelUtil.LoadingGaraphicMsg(label_msg_appointment,"sending SMS" );

        //sending SMS
        //sending SMS
        //sending SMS
        String smspartMsg = "We inform you that you have an appointment with Dr " + userManager.getLogged_User().getLast_name().toUpperCase() + " on ";
        boolean smsSuccsees = MySmsSender.sendEmailToDummyPatientsByDoctor(new DummyPatientAppointmentDoctorSide[]{rdvManager.getSelectedDummyPatientAppointmentDoctorSide()},
                smspartMsg);
        System.out.println("sms sent successfully :" + smsSuccsees);

//        label_msg_appointment.setGraphic(null);
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment," SMS successfully sent.");
        MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);

        //------ clearing selection and hide Buttons again 
        this.unselectTable_hide_paneAppointmentFields();

    }

    // private method appointment
    // private method appointment
    // private method appointment
    // private method appointment
    private void setAppointmentTableView() {
        //------------List-Appointments------------------------------------------------------------------------------
        //TableView creation for our calandarDay' TimeSlots
        try {

            // getting the calandar day 's id to searcch for related timeSlots
            int calandar_day_id = rdvManager.getSelectedCalandar().getId();

            CrudPatientAppointment crudAppPatient = new CrudPatientAppointment();
            //get all related appointment for the Calandar day with join data of patient with it
            Collection<DummyPatientAppointmentDoctorSide> dummyPatientsappointments = crudAppPatient.get_Patients_Email_name_date_time_from_CalandarDay(calandar_day_id);
            rdvManager.setCollecDoctorPatientAppointment(dummyPatientsappointments);

            rdvManager.setCollecDoctorPatientAppointment(dummyPatientsappointments);
            System.out.println("data to fill tableView Appointments => " + dummyPatientsappointments);
            //*******start_event_handler********
            EventHandler<MouseEvent> customMouseEvent = (event) -> {
                if (event.getClickCount() == 1) {
                    //gui show (buttons delete and notify)
                    paneAppointment.setVisible(true);

                    //store the selection and the index in table
                    index_Selectd_Appointment = tableView_appointment.getSelectionModel().getSelectedIndex();
                    System.out.println("index Appointment selected Saved :" + index_Selectd_Appointment);

                    DummyPatientAppointmentDoctorSide selcted_DummyPatientappointment = tableView_appointment.getSelectionModel().getSelectedItem();
                    rdvManager.setSelectedDummyPatientAppointmentDoctorSide(selcted_DummyPatientappointment);

                }
            };
            //*****end event-handler*********
            Field[] fields = DummyPatientAppointmentDoctorSide.class.getDeclaredFields(); // necessary for autoGenerating my Table headers and Columns
            int firstFieldsColumnToHide = 7; // depends on the order of declared attribute on the record class entity "appointment"
            SettingUpTableView.Tableview_SettingUp(tableView_appointment, dummyPatientsappointments, customMouseEvent, fields, firstFieldsColumnToHide);

        } catch (ConnectionOrPrepareStatmentException | SelectExecuteQueryException ex) {
            ex.printStackTrace();
        }

    }

    //****************
    private void initialize_inputsDeleteNotifyAppointment() {
        //hide buttons before table selection( appointment )
        paneAppointment.setVisible(false);
        label_msg_appointment.setText("");

    }

    //----------navigation-bar code -----------------------------------------------------------------------------------------
    //----------navigation-bar code -----------------------------------------------------------------------------------------
    //----------navigation-bar code -----------------------------------------------------------------------------------------
    //----------navigation-bar code -----------------------------------------------------------------------------------------
    @FXML
    void onActionGOToAppointments(ActionEvent event) {
        System.out.println("implement onActionGOToAppointments when ready .");
        // clean all data stored for the specefic case  because going to list all the appointments the doctor has
        rdvManager.SetEverythingToNull();
        //clear this because only related to this current controller
        this.resetFields_of_CurrentController();

        // going to the next interface
        Stage currentStage = (Stage) btn_appointmentGo.getScene().getWindow();
        viewFactory.show_All_DoctorSideAppointment_list_keepStage(currentStage);
    }

    @FXML
    void onActionGoToCalandar(ActionEvent event) {
        // clean all data stored because going back to the calandar list
        rdvManager.SetEverythingToNull();
        //clear this because only related to this current controller
        this.resetFields_of_CurrentController();

        // going to the next interface
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
        // clean all data stored because going back to the calandar list
        rdvManager.SetEverythingToNull();
        //clear this because only related to this current controller
        this.resetFields_of_CurrentController();

        // going to the next interface
        Stage currentStage = (Stage) btn_homeGo.getScene().getWindow();
        viewFactory.showLogin_keepStage(currentStage);
    }

    @FXML
    void onActionGoToLogout(ActionEvent event) {

        // clean all data stored because going back to the login
        rdvManager.SetEverythingToNull();
        // clear user because we re logging out
        userManager.clear_all_fields();

        //clear this because only related to this current controller
        this.resetFields_of_CurrentController();

        // going to the next interface
        Stage currentStage = (Stage) btn_homeGo.getScene().getWindow();
        viewFactory.showLogin_keepStage(currentStage);
    }

// Clear Fields -------------------------------------------------------
// Clear Fields -------------------------------------------------------
// Clear Fields -------------------------------------------------------
// Clear Fields -------------------------------------------------------
    private void unselectTable_hide_paneAppointmentFields() {
        tableView_appointment.getSelectionModel().clearSelection();
        paneAppointment.setVisible(false);
    }

    private void unselectTable_hide_paneSlotsFields() {
        tableView_appointment.getSelectionModel().clearSelection();
        paneTimeSlot.setVisible(false);
    }

    private void resetFields_of_CurrentController() {

        //hide pane and remove selection
        this.unselectTable_hide_paneAppointmentFields();
        this.unselectTable_hide_paneSlotsFields();

        // clear the element of the tableView
        tableView_appointment.getItems().clear();
        tableView_slot.getItems().clear();

        // set data Attributes we worked with to null in this Controller
        index_Selectd_TimeSlot = null;
        index_Selectd_Appointment = null;
    }

}
