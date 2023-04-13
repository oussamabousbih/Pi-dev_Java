/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller.gestion_rdv;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import main.controller.BaseController;
import main.controller.gestion_rdv.services.MyTextAreaUtil;
import main.controller.gestion_rdv.services.SettingUpTableView;
import main.entities.gestion_rdv.Appointment;
import main.entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import main.entities.gestion_rdv.TimeSlot;
import main.entities.gestion_rdv.User;
import main.entities.gestion_rdv.enums.TimeSlotReason;
import main.entities.gestion_rdv.enums.TimeSlotStatus;
import main.services.gestion_rdv.CrudAppointment;
import main.services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import main.services.gestion_rdv.CrudPatientAppointment;
import main.services.gestion_rdv.CrudTimeSlot;
import main.services.gestion_rdv.exceptions.DeleteException;
import main.services.gestion_rdv.exceptions.SelectExecuteQueryException;
import main.services.gestion_rdv.exceptions.UpdateException;

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
    private Label label_msg;

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

        try {
            // assuming success from the start 
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "TimeSlot Updated Successfully .", ButtonType.OK);
            if (new CrudTimeSlot().update_timeSlot(rdvManager.getSelectedTimeSlot(), true)) {
                new CrudTimeSlot().update_timeSlot_afterAppointmentDeleted( rdvManager.getSelectedDummyPatientAppointmentDoctorSide().getTime_slot_id());
                tableView_slot.getItems().set(index_Selectd_TimeSlot, rdvManager.getSelectedTimeSlot());
                this.setAppointmentTableView();

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Update Failed .");
            }
            // showing anyway
            alert.show();

        } catch (UpdateException ex) {
            ex.printStackTrace();
        }

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
            Appointment appointment = rdvManager.getSelectedDummyPatientAppointmentDoctorSide().convertToAppointment();
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
    void onActionNotifyAppointment(ActionEvent event) {
        System.out.println("Implementing Notify method soon ......");
    }

    // private method appointment
    // private method appointment
    // private method appointment
    // private method appointment
    private void setAppointmentTableView() {
        //------------List-Appointments------------------------------------------------------------------------------
        //TableView creation for our calandarDay' TimeSlots
        try {

            int calandar_day_id = rdvManager.getSelectedCalandar().getId();
            CrudPatientAppointment crudAppPatient = new CrudPatientAppointment();
            Collection<DummyPatientAppointmentDoctorSide> appointments = crudAppPatient.get_Patient_Email_name_date_time_from_CalandarDay(calandar_day_id);
            rdvManager.setCollecDoctorPatientAppointment(appointments);
            System.out.println("data to fill tableView Appointments => " + appointments);
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
            int firstFieldsColumnToHide = 6;
            SettingUpTableView.Tableview_SettingUp(tableView_appointment, appointments, customMouseEvent, fields, firstFieldsColumnToHide);

        } catch (ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        } catch (SelectExecuteQueryException ex) {
            Logger.getLogger(DoctorCalandarTimeSlots_listController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //****************
    private void initialize_inputsDeleteNotifyAppointment() {
        //hide buttons before table selection( appointment )
        paneAppointment.setVisible(false);

    }

    //----------navigation-bar code -----------------------------------------------------------------------------------------
    //----------navigation-bar code -----------------------------------------------------------------------------------------
    //----------navigation-bar code -----------------------------------------------------------------------------------------
    //----------navigation-bar code -----------------------------------------------------------------------------------------
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
