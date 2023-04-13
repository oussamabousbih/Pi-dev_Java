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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import main.controller.BaseController;
import main.controller.gestion_rdv.services.SettingUpTableView;
import main.entities.gestion_rdv.Appointment;
import main.entities.gestion_rdv.CalandarDay;
import main.entities.gestion_rdv.TimeSlot;
import main.entities.gestion_rdv.enums.AppointmentBookingState;
import main.entities.gestion_rdv.enums.AppointmentReason;
import main.services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import main.services.gestion_rdv.CrudAppointment;
import main.services.gestion_rdv.CrudTimeSlot;
import main.services.gestion_rdv.exceptions.InsertException;
import main.services.gestion_rdv.exceptions.UpdateException;

/**
 *
 * @author rbaih
 */
public class PatientCalandarTimeSlots_listController extends BaseController implements Initializable {

    public PatientCalandarTimeSlots_listController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);

    }

    @FXML
    private Button btn_back;

    @FXML
    private Label label_date;
    @FXML
    private TableView<TimeSlot> tableView_slot;
    @FXML
    private Label label_msg;
    @FXML
    private Pane bookingPane;
    @FXML
    private Button btn_book;
    @FXML
    private TextField textField_time;
    @FXML
    private ChoiceBox<String> choiceBox_reason;
    @FXML
    private ChoiceBox<String> choiceBox_bookingState;

    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

    @FXML
    private Button btn_doctorsGo;

//    @FXML
//    private Button btn_calandarGo;
    private int indexOfSelectedCell;

    @FXML
    void onActionBackToCalandarList(ActionEvent event) {
        // clearing what we selected earlier
        rdvManager.Clear_setToNull_AllSelectedFields();
        System.out.println("back to calandarList");
        Stage currentStage = (Stage) this.btn_back.getScene().getWindow();
        viewFactory.showUserSideCalandars_list_keepStage(currentStage);
    }

    @FXML
    void onActionBook(ActionEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Book-Appointment", ButtonType.APPLY, ButtonType.CANCEL);
        confirmAlert.showAndWait();
        if (confirmAlert.getResult() == ButtonType.APPLY) {
            TimeSlot selectedTimeSlot = rdvManager.getSelectedTimeSlot();
            CalandarDay selecCalandarDay = rdvManager.getSelectedCalandar();
            System.out.println("create Appointment -from->" + selectedTimeSlot);
            int timeSlot_id = selectedTimeSlot.getId();
            int patient_id = userManager.getLogged_User().getId();
            Appointment newAppointment = new Appointment();
            newAppointment.setPatient_id(patient_id);
            newAppointment.setCalandar_day_id(patient_id);
            newAppointment.setCalandar_day_id(timeSlot_id);
            newAppointment.setHour(selectedTimeSlot.getStart_time());
            newAppointment.setReason(choiceBox_reason.getValue());
            newAppointment.setBooking_state(choiceBox_bookingState.getValue());
            // insert now 
            try {
                CrudAppointment crudAppointment = new CrudAppointment();
                if (crudAppointment.Insert_AddAppointment_Requires_atMinimum_TimeSlot_and_Patient_Id(newAppointment, timeSlot_id, patient_id)) {
                    System.out.println("Appointment inserted ");
                    label_msg.setText("Appointment Added Successfully .");
                    //updating the status ot the TimeSlot no after booking
                    selectedTimeSlot.setStatus("not-available");
                    selectedTimeSlot.setReason("booked");
                    try {
                        if (new CrudTimeSlot().update_timeSlot(selectedTimeSlot, true)) {
                            System.out.println("Time Slot Updated successfully appointment created .");
                        }
                        // instant Update indexOfSelected cell we got when we click the button defined above
                        tableView_slot.getItems().remove(indexOfSelectedCell);
                    } catch (UpdateException ex) {
                        Logger.getLogger(PatientCalandarTimeSlots_listController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    label_msg.setText("Sorry appointment could not be added .");
                }

            } catch (InsertException ex) {
                Logger.getLogger(PatientCalandarTimeSlots_listController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingPane.setVisible(false);
        this.initialiseCheckBoxes();
        LocalDate date = rdvManager.getSelectedCalandar().getDate();
        label_date.setText("Date: " + date.format(DateTimeFormatter.ISO_DATE));

        //------------List-TimeSlots------------------------------------------------------------------------------
        //TableView creation for our calandarDay' TimeSlots
        try {
            //creating table View
            this.creatingTableView();
            // hiding appointment column in timeSlotTable ( always double check this Easy to bug )
            tableView_slot.getColumns().get(9).setVisible(false);
            //making tableView not able to select only button to click
            tableView_slot.setSelectionModel(null);

            // part 2 adding button to our tableView
            //Adding Column and Button to the TableView for Booking
            TableColumn<TimeSlot, String> buttonColumn = new TableColumn<>("ACTIONS");
            buttonColumn.setCellValueFactory(cellData -> new SimpleStringProperty("Book Appointment"));
            buttonColumn.setCellFactory(column -> {
                return new TableCell<TimeSlot, String>() {
                    Button button = new Button();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            button.setText(item);
                            setGraphic(button);
                            button.setOnAction(event -> {
                                // knowing the index selected to work with on our ObservableList
                                indexOfSelectedCell = getIndex();
                                TimeSlot selectedTimeSlot = tableView_slot.getItems().get(indexOfSelectedCell);
                                //Storing the SelectedTimeslot
                                rdvManager.setSelectedTimeSlot(selectedTimeSlot);
                                System.out.println("adding selected_timeSlot obj " + selectedTimeSlot);
                                // activate the pane for Booking options
                                bookingPane.setVisible(true);
                                // filling up the textFiel_time with the selected TimeSlot time
                                textField_time.setText(selectedTimeSlot.getStart_time().format(DateTimeFormatter.ISO_TIME));
                            });

                        }
                    }
                };
            });
            // Add the button column to the table view
            tableView_slot.getColumns().add(buttonColumn);

        } catch (ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        }

    }// end-initialize

    // Private initialization
    private void initialiseCheckBoxes() {
        // i will use foreach loop if it was many options
//        String[] choicesReason = { AppointmentReason.APPOINTMENT.getName(),AppointmentReason.CHECK_UP.getName() };
        ArrayList<String> choices = new ArrayList<>();
        for (AppointmentBookingState appState : AppointmentBookingState.values()) {
            choices.add(appState.getName());
        }
        choiceBox_bookingState.getItems().addAll(choices);
        choiceBox_bookingState.setValue(choices.get(0));
        choices.clear();
        for (AppointmentReason appState : AppointmentReason.values()) {
            choices.add(appState.getName());
        }
        choiceBox_reason.getItems().addAll(choices);
        choiceBox_reason.setValue(choices.get(0));

    }

    private void creatingTableView() throws ConnectionOrPrepareStatmentException {
        int calandar_day_id = rdvManager.getSelectedCalandar().getId();
        CrudTimeSlot crudTS = new CrudTimeSlot();
        Collection<TimeSlot> slots = crudTS.getTimeSlotsBelongsTo_calandar_Day(calandar_day_id, true);
        System.out.println("data to fill tableView Slots => " + slots);
        EventHandler<MouseEvent> customMouseEvent = (event) -> {
            if (event.getClickCount() == 2) {
                String msg = " Selected row : ";
                System.out.println(msg + tableView_slot.getSelectionModel().getSelectedItem());
            }
        };
        Field[] fields = TimeSlot.class.getDeclaredFields();
        SettingUpTableView.Tableview_SettingUp(tableView_slot, slots, customMouseEvent, fields, 5);
        // makind the columns adjust to take the free size if any 
        tableView_slot.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

//    @FXML
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
