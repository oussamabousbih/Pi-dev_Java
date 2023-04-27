/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Stream;
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
import entities.gestion_rdv.DummyPatientAppointment;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import entities.gestion_rdv.enums.AppointmentBookingState;
import entities.gestion_rdv.enums.AppointmentReason;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import services.gestion_rdv.CrudAppointment;
import services.gestion_rdv.CrudPatientAppointment;
import services.gestion_rdv.CrudTimeSlot;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import services.gestion_rdv.exceptions.DeleteException;
import services.gestion_rdv.exceptions.UpdateException;

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
    private Label label_msg_appointment;

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

        DummyPatientAppointment PatientAndappointment = rdvManager.getSelectedDummyPatientAppointment();
        String dateApp = PatientAndappointment.getDate().format(DateTimeFormatter.ISO_DATE);
        String timepp = PatientAndappointment.getTime().toString();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete appointment on " + dateApp + " @" + timepp, ButtonType.APPLY, ButtonType.CANCEL);

            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                return;
            }

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.OK);
            // necessary to look OOP for Crud
            Appointment appointment = rdvManager.getSelectedDummyPatientAppointment().convertToAppointment();
            if (new CrudAppointment().DeleteAppointment(appointment)) {

                int timeSlot_id = appointment.getTime_slot_id();
                new CrudTimeSlot().update_timeSlot_afterAppointmentDeleted(timeSlot_id);

                System.out.println("index appointment to delete debug just befor Delete :" + index_Selectd_Appointment);
                tableView_appointment.getItems().remove(index_Selectd_Appointment);
//  or this               tableView_appointment.getItems().remove(rdvManager.getSelectedDummyPatientAppointment());

                // getting doctor data for email from SelectedDummyPatient Appointment
                DummyPatientAppointment patientAppointmnt = rdvManager.getSelectedDummyPatientAppointment();
                DummyUser doctor = new DummyUser(patientAppointmnt.getDoctor_email(), "", patientAppointmnt.getDoctor_last_name(),
                        patientAppointmnt.getDoctor_phone_number(), "\"ROLE_DOCTOR\"");

                CalandarDay dummyCalandar = new CalandarDay();
                dummyCalandar.setDate(patientAppointmnt.getDate());
                dummyCalandar.setDoctor_id(patientAppointmnt.getDoctor_id());
                //Email-Send    
                //sending email notification before  cleaning the deleted data to avoid null pointer exception
                EmailSendHtmlMsg.sendEmail_Notification_Delete(patientAppointmnt, dummyCalandar, doctor);

                // alert modify for sucess
                alert.setContentText("Successfully Deleted .");
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment);
                label_msg_appointment.setText("Appointment on " + dateApp + " @" + timepp + " is Deleted Successfully.");
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Delete Failed .");
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_msg_appointment);
                label_msg_appointment.setText("Failed to delete appointment on " + dateApp + " @" + timepp);
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);
            }
            // showing anyway
            alert.showAndWait();
        } catch (DeleteException | UpdateException ex) {
            ex.printStackTrace();
        }
        
        tableView_appointment.getSelectionModel().clearSelection();
        operationsPane.setVisible(false);
    }

    @FXML
    void onActionUpdateAppointment(ActionEvent event) {

        DummyPatientAppointment PatientAndappointment = rdvManager.getSelectedDummyPatientAppointment();
        String dateApp = PatientAndappointment.getDate().format(DateTimeFormatter.ISO_DATE);
        String timepp = PatientAndappointment.getTime().toString();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update appointment " + dateApp + " @" + timepp, ButtonType.APPLY, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                return;
            }

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().clear();
            // necessary to look OOP for Crud

            //setting up values inside choiceBoxes
            rdvManager.getSelectedDummyPatientAppointment().setBooking_state(choiceBox_bookingState.getValue());
            rdvManager.getSelectedDummyPatientAppointment().setReason(choiceBox_reason.getValue());
            Appointment appointment = rdvManager.getSelectedDummyPatientAppointment().convertToAppointment();

            if (new CrudAppointment().UpdateAppointmentFields_reason_bookingState(appointment)) {
//              alert modify for sucess
                alert.setContentText("Successfully Updated .");
                alert.getButtonTypes().add(ButtonType.OK);

                System.out.println("index appointment to Update debug just befor update On tableView Level :" + index_Selectd_Appointment);
                tableView_appointment.getItems().set(index_Selectd_Appointment, rdvManager.getSelectedDummyPatientAppointment());

                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_appointment);
                label_msg_appointment.setText("Appointment on " + dateApp + " @" + timepp + " is Updated successfully .");
                MyLabelUtil.hideLabelAfter(label_msg_appointment, 3);

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Update Failed .");
                alert.getButtonTypes().add(ButtonType.OK);

                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_msg_appointment);
                label_msg_appointment.setText("Appointment on " + dateApp + " @" + timepp + " Update Failed .");
            }
            // showing anyway
            alert.showAndWait();
        } catch (UpdateException ex) {
            ex.printStackTrace();
        }
        tableView_appointment.getSelectionModel().clearSelection();
        operationsPane.setVisible(false);
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

                    if (tableView_appointment.getSelectionModel().getSelectedItem() != null) {

                        operationsPane.setVisible(true);

                        //store the selection and the index in table
                        index_Selectd_Appointment = tableView_appointment.getSelectionModel().getSelectedIndex();
                        System.out.println("index Appointment selected Saved :" + index_Selectd_Appointment);

                        DummyPatientAppointment selcted_DummyPatientappointment = tableView_appointment.getSelectionModel().getSelectedItem();
                        rdvManager.setSelectedDummyPatientAppointment(selcted_DummyPatientappointment);
                    }

                }
            };
            //*****end event-handler*********
            Field[] parent = DummyPatientAppointmentDoctorSide.class.getDeclaredFields(); // special case subClass get all fields approach
            Field[] child = DummyPatientAppointment.class.getDeclaredFields();
            Field[] parent_child_fields = Stream.concat(Arrays.stream(parent), Arrays.stream(child)).toArray(Field[]::new);
            int firstFieldsColumnToHide = 6;
            SettingUpTableView.Tableview_SettingUp(tableView_appointment, appointments, customMouseEvent, parent_child_fields, firstFieldsColumnToHide);

        } catch (ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        }

    }

    //****************
    private void initialize_inputsDeleteNotifyAppointment() {
        //hide buttons before table selection( appointment )
        operationsPane.setVisible(false);

        this.initializeAppointmentUpdateInput();

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
        Stage currentStage = (Stage) btn_appointmentGo.getScene().getWindow();
        viewFactory.show_UserSideAppointment_list_keepStage(currentStage);

    }

    private void initializeAppointmentUpdateInput() {
        choiceBox_bookingState.getItems().addAll(AppointmentBookingState.CONFIRMED.getName(), AppointmentBookingState.NOT_CONFIRMED.getName());
        choiceBox_reason.getItems().addAll(AppointmentReason.APPOINTMENT.getName(), AppointmentReason.CHECK_UP.getName());
        choiceBox_bookingState.setValue(AppointmentBookingState.CONFIRMED.getName());
        choiceBox_reason.setValue(AppointmentReason.APPOINTMENT.getName());
    }

//    @FXML // not necessary for user
//    void onActionGoToCalandar(ActionEvent event) {
//        Stage currentStage =(Stage) btn_calandarGo.getScene().getWindow();
//        viewFactory.showDoctorCalandars_list_keepStage(currentStage);
//        
//    }
    @FXML
    void onActionGoToDoctors(ActionEvent event) {
        Stage currentStage = (Stage) btn_doctorsGo.getScene().getWindow();
        viewFactory.showDoctors_list_keepStage(currentStage);
    }

    @FXML
    void onActionGoToHome(ActionEvent event) {
        Stage currentStage = (Stage) btn_homeGo.getScene().getWindow();
        viewFactory.showLogin_keepStage(currentStage);
    }

}
