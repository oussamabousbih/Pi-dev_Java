/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import GUI.ViewFactory;
import main.RdvManager;
import main.UserManager;
import controller.BaseController;
import controller.gestion_rdv.services.CalandarEXCEL;
import controller.gestion_rdv.services.CalandarPDF;
import controller.gestion_rdv.services.EmailSendHtmlMsg;
import controller.gestion_rdv.services.MyChartPie;
import utils.MyLabelUtil;
import utils.MySpinnerUtil;
import utils.SettingUpTableView;
import entities.gestion_rdv.CalandarDay;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import entities.gestion_rdv.StatisticsAppointmentSlot;
import javafx.scene.chart.PieChart;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;
import services.gestion_rdv.CrudCalandarDay;
import services.gestion_rdv.CrudPatientAppointment;
import services.gestion_rdv.DatatBaseStatitsicAppointmentSlot;
import services.gestion_rdv.exceptions.DeleteException;
import services.gestion_rdv.exceptions.InsertException;
import services.gestion_rdv.exceptions.SelectExecuteQueryException;
import utils.MyPieChart;


/**
 *
 * @author rbaih
 */
public class DoctorCalandars_listController extends BaseController implements Initializable {

    @FXML
    private Pane pane_delete;
    @FXML
    private TextArea textArea_deleteData;
    @FXML
    private DatePicker dp_date;

    @FXML
    private Spinner<Integer> sp_dayStart_hour;
    @FXML
    private Spinner<Integer> sp_dayStart_min;
    @FXML
    private Spinner<Integer> sp_dayEnd_hour;
    @FXML
    private Spinner<Integer> sp_dayEnd_Minutes;
    @FXML
    private Spinner<Integer> sp_lunchTime_hour;
    @FXML
    private Spinner<Integer> sp_lunchTime_min;
    @FXML
    private Spinner<Integer> sp_lunchTimeDuration;
    @FXML
    private Spinner<Integer> sp_sessionDuration;
    @FXML
    private Button btn_createCalandar;
    @FXML
    private Label label_insertMsg;
    @FXML
    private TableView<CalandarDay> tableViewCalandar;

    @FXML
    private Button btn_homeGo;

    @FXML
    private Button btn_appointmentGo;

//    @FXML
//    private Button btn_doctorsGo;
// charts statistics :
    @FXML
    private PieChart pieChart;

//excel pdf generation
    @FXML
    private Button btn_calandarGo;

    @FXML
    private Button btn_generatePDF;

    @FXML
    private Button btn_generateEXCEL;

    @FXML
    private Label label_msg_downloadFile;
    // data to work with 
    private int indexSelectedCalandarObservableList;

    // constructors ******
    // constructors ******
    public DoctorCalandars_listController(UserManager userManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, fxmlName, viewFactory);
    }

    public DoctorCalandars_listController(RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(rdvManager, fxmlName, viewFactory);
    }

    public DoctorCalandars_listController(UserManager userManager, RdvManager rdvManager, String fxmlName, ViewFactory viewFactory) {
        super(userManager, rdvManager, fxmlName, viewFactory);
    }

    //***OnAction-Methods-CHART*****
    //***OnAction-Methods-CHART*****
    //***OnAction-Methods*****
    private void initPieChart() {

        MyPieChart myPieChart = new MyPieChart(pieChart, "Stats for all types Off appointment");
        updatePieChart(myPieChart);
    }

    private void updatePieChart(MyPieChart myPieChart) {
        StatisticsAppointmentSlot fetchedStats = DatatBaseStatitsicAppointmentSlot.countTypeAppointments(userManager.getLogged_User().getId());
        
        // Create the initial data for the chart
        PieChart.Data[] dataForChart = new PieChart.Data[]{
            new PieChart.Data("check-up  ("+fetchedStats.getCheckUpPercent()+"%)", fetchedStats.getAppointmentsReasonCheckUp()),
            new PieChart.Data("appointments ("+fetchedStats.getAppointmentPercent()+"%)", fetchedStats.getAppointmentsReasonAppointment()),
            new PieChart.Data("slots-booked-manually ("+fetchedStats.getBookedSlotPercent()+"%)", fetchedStats.getLocallyBookedSlots())
        };

        if (myPieChart != null) {
            myPieChart.setPieChartData_byObservableList(dataForChart);
        } else {
            pieChart.getData().clear();
            pieChart.getData().addAll(dataForChart);
        }

               

    }

    @FXML
    void onActionRefreshChart() {
        updatePieChart(null);
    }

    //***OnAction-Methods*****
    //***OnAction-Methods*****
    //***OnAction-Methods*****
    //***OnAction-Methods*****
    @FXML
    void onActionDeleteSelected(ActionEvent event) {

        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "the calandar selected will be deleted .", ButtonType.APPLY,
                ButtonType.CANCEL);
        alertDelete.setTitle("Delete-Calendar");
        alertDelete.showAndWait();
        if (alertDelete.getResult() == ButtonType.CANCEL) {
            return;
        }

        try {
            //email all users before deleting
            CalandarDay cal = this.rdvManager.getSelectedCalandar();
            Collection<DummyPatientAppointmentDoctorSide> patientsAppointment = new CrudPatientAppointment()
                    .get_Patients_Email_name_date_time_from_CalandarDay(cal.getId());
            EmailSendHtmlMsg.sendEmail_Notification_DeleteForAllUsersPassed(patientsAppointment, cal, userManager.getLogged_User());

            // deleting calandar
            if (new CrudCalandarDay().delete_calandar_day_withAllRelated(rdvManager.getSelectedCalandar())) {

                tableViewCalandar.getItems().remove(indexSelectedCalandarObservableList); //update table view after delete
                new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted .", ButtonType.CLOSE).showAndWait();

            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Delete .", ButtonType.CLOSE).showAndWait();
            }

        } catch (DeleteException | SelectExecuteQueryException | ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        }

    }

    
    
    
    @FXML
    void onActionCreateCalandar(ActionEvent event) {

        CalandarDay calandarToInsert = this.fillingUpCalandarFromInput();
        //validation of input at submit time
        if (!this.validateInputCreateCalandarAfterStoredInCalandarObject_EasierCheck(calandarToInsert, label_insertMsg)) {
            return; // next instruction is necessarry in case app used in other laptop with same user logged
        }
        CrudCalandarDay crudCalandar = new CrudCalandarDay();

        try {

            //test If Calandar Already Exists before creation
            if (null != crudCalandar.getcalandarBy_Doctor_and_date(calandarToInsert.getDoctor_id(), calandarToInsert.getDate())) {
                String msg="- Pick an other Date calandar already created - ";
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_insertMsg ,msg );
                MyLabelUtil.hideLabelAfter(label_insertMsg, 3 );
                return;
            }

            // alert before insertion 
            Alert alertCreation = new Alert(Alert.AlertType.CONFIRMATION, calandarToInsert.toString(), ButtonType.APPLY, ButtonType.CANCEL);
            alertCreation.showAndWait();
            if (alertCreation.getResult() == ButtonType.CANCEL) {
                return;
            }

            // creating the calandar now ( safe to create we tested for existing )
            if (crudCalandar.create_CalanadarDay_WithTimeSlotsOption(calandarToInsert, true) != null) {
                tableViewCalandar.getItems().add(calandarToInsert);// make sure to get the Calandar's PrimaryKey during insert
                String msg="Successfully created Calandar . ";
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_insertMsg,msg);
                MyLabelUtil.hideLabelAfter(label_insertMsg, 3);
            } else {
                String msg="Failed To create Calandar . ";
                MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(label_insertMsg,msg);
                MyLabelUtil.hideLabelAfter(label_insertMsg, 3);
                
            }

        } catch (InsertException | ConnectionOrPrepareStatmentException ex) {
            ex.printStackTrace();
        }

    }

    //****************************
    //****Initialize********
    //****Initialize********
    //****Initialize********
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (this.userManager.getLogged_User().getRoles().contains("\"ROLE_DOCTOR\"")) {
            this.initialize_If_Doctor();
        }

        initPieChart();

    }// end Initialize *************************************************************************************************************

    //*private methods****
    //*private methods****
    //*private methods****
    //*private methods****
    private void initialize_If_Doctor() {
        //initialize input 
        this.main_initializeandConfigInput_of_CalandarCreation();
        // getting the doctor responsibe for the selected calandar
        int doctor_id = userManager.getLogged_User().getId();

        //TableView creation for our calandarDay
        try {
            //getData fromDb
            Collection<CalandarDay> calandars
                    = new CrudCalandarDay().getCalandar_view_greaterEqualDateOptional(doctor_id, true, null);
            // setting up MouseEvent
            EventHandler<MouseEvent> customEvent = (event) -> {
                if (event.getClickCount() == 1) {
                    //logic
                    indexSelectedCalandarObservableList = tableViewCalandar.getSelectionModel().getSelectedIndex();
                    CalandarDay cal = tableViewCalandar.getSelectionModel().getSelectedItem();
                    rdvManager.setSelectedCalandar(cal);
                    System.out.println("selecting ==> " + rdvManager.getSelectedCalandar());
                    //gui update
                    textArea_deleteData.setText("Calendar : " + rdvManager.getSelectedCalandar().getDate().format(DateTimeFormatter.ISO_DATE) + "\n"
                            + "click on delete to proceed with the delete process .");
                    pane_delete.setVisible(true);
                }
                if (event.getClickCount() == 2) {
                    //navigation 
                    Stage currentStagr = (Stage) this.sp_dayEnd_Minutes.getScene().getWindow();
                    viewFactory.showCalandarTimeSlotsDoctorView_list_keepStage(currentStagr);
                }
            };
            //arrange your entityClass for easier manipulation for this to work by putting the attributes you wish to hide the first on the class then count them and pass the number
            SettingUpTableView.Tableview_SettingUp(tableViewCalandar, calandars, customEvent, CalandarDay.class.getDeclaredFields(), 4);

        } catch (ConnectionOrPrepareStatmentException | SelectExecuteQueryException ex) {
            ex.printStackTrace();
        }

    }// end initialize if doctor

    //*********************
    private boolean validateInputCreateCalandarAfterStoredInCalandarObject_EasierCheck(CalandarDay cal, Label error_label) {
        boolean dateOk, timeOk, breakTimeOk, sessionOk, breakSessionOk;
        dateOk = timeOk = breakTimeOk = sessionOk = breakSessionOk = true;
        String msg = "";
        if (cal.getDate().compareTo(LocalDate.now()) >= 0) {
            dateOk = true;
        }
        if (cal.getDay_start().compareTo(cal.getDay_end()) >= 0) {
            timeOk = false;
            msg += (msg.length() > "".length() ? "\n" : "");
            msg += "- Day end must be > Day Start time - ";
        }
        if (!(cal.getLunch_break_start().compareTo(cal.getDay_start()) > 0 && cal.getLunch_break_start().compareTo(cal.getDay_end()) < 0)) {
            breakTimeOk = false;
            msg += (msg.length() > "".length() ? "\n" : "");
            msg += "- Lunch time must be in working hours - ";
        }
        Integer session_Max_PossibleDuration = (int) Duration.between(cal.getDay_start(), cal.getDay_end()).toMinutes();
        if (cal.getSession_duration() > session_Max_PossibleDuration) {
            breakTimeOk = false;
            msg += (msg.length() > "".length() ? "\n" : "");
            msg += "- Session value entered > then the whole working day - ";
        }
        if (!msg.equals("")) {
            MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(error_label);
        }
//        error_label.setText(msg);
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleRed(error_label, msg);
        MyLabelUtil.hideLabelAfter(error_label, 3);
        return dateOk && timeOk && breakTimeOk && sessionOk && breakSessionOk;
    }

    //*********************
    private CalandarDay fillingUpCalandarFromInput() {
        CalandarDay calandar = new CalandarDay();
        calandar.setDoctor_id(userManager.getLogged_User().getId());
        calandar.setDate(dp_date.getValue());
        calandar.setDay_start(LocalTime.of(sp_dayStart_hour.getValue(), sp_dayStart_min.getValue()));
        calandar.setDay_end(LocalTime.of(sp_dayEnd_hour.getValue(), sp_dayEnd_Minutes.getValue()));
        calandar.setSession_duration(sp_sessionDuration.getValue());
        LocalTime lunchTime = LocalTime.of(sp_lunchTime_hour.getValue(), sp_lunchTime_min.getValue());
        calandar.setLunch_break_start(lunchTime);
        calandar.setLunch_break_end(lunchTime.plusMinutes(sp_lunchTimeDuration.getValue()));

        return calandar;
    }

    //******************
    private void main_initializeandConfigInput_of_CalandarCreation() {
        //----input-fields-initialize-Creation-Calandar------------------------------------------------------------------
        pane_delete.setVisible(false);

        // making format accept only numeric Spinner 
        MySpinnerUtil.Change_spinner_digitOnly(sp_dayEnd_Minutes, sp_dayEnd_hour,
                sp_dayStart_hour, sp_dayStart_min, sp_lunchTimeDuration, sp_lunchTime_hour,
                sp_lunchTime_min, sp_sessionDuration);
        // making listener realize changing when inserting
        MySpinnerUtil.addFocusListenerToSpinners(sp_dayEnd_Minutes, sp_dayEnd_hour,
                sp_dayStart_hour, sp_dayStart_min, sp_lunchTimeDuration, sp_lunchTime_hour,
                sp_lunchTime_min, sp_sessionDuration);

        this.initialiseInputFieldsOnly_CalandarDayInsert();
    }
        
    
    //****************
    private void initialiseInputFieldsOnly_CalandarDayInsert() {
        // initializing attributes default values (make it as a method better)
        dp_date.setValue(LocalDate.now());
        // SpinnerValueFactory.IntegerSpinnerValueFactory() is a nested Class 
        sp_dayStart_hour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 9));
        sp_dayStart_min.setEditable(true);
        sp_dayStart_min.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        sp_dayEnd_hour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 17));
        sp_dayEnd_Minutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        sp_lunchTime_hour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        sp_lunchTime_min.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 00));
        sp_lunchTimeDuration.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 180, 60));
        sp_sessionDuration.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 480, 30));
    }

    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    @FXML
    void onActionGenerateExcelCalandars(ActionEvent event) {
        String pcOwner = System.getProperties().getProperty("user");
        String msg = "PDF File have been successfuly generated and downloaded in folder " + pcOwner + "/Downloads";
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_downloadFile, msg);
        
        new CalandarEXCEL().ExcelCalandarGenerated(userManager.getLogged_User());
        
        //clear msg after a while
        MyLabelUtil.hideLabelAfter(label_msg_downloadFile, 3);
    }

    //---------------------
    @FXML
    void onActionGeneratePdfCalandars(ActionEvent event) {
        String pcOwner = System.getProperties().getProperty("user");
        String msg = "EXCEL File have been successfuly generated and downloaded in folder " + pcOwner + "/Downloads";
        MyLabelUtil.labelmsg_collabsable_bold_textVisibleGreen(label_msg_downloadFile, msg);
        
        new CalandarPDF().PdfCalandarGenerated(userManager.getLogged_User());
        new CalandarEXCEL().ExcelCalandarGenerated(userManager.getLogged_User());
       
        //clear msg after a while
        MyLabelUtil.hideLabelAfter(label_msg_downloadFile, 3);

    }
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------
    //*******PDF-EXCEL-Download-----------------------------------------------------------------------------------------------

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
