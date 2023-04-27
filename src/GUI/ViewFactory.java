/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.UserManager;
import main.RdvManager;
import controller.BaseController;
import controller.DummyLoginController;
import controller.gestion_rdv.AppointmentDoctorController;
import controller.gestion_rdv.AppointmentUserController;
import controller.gestion_rdv.DoctorCalandarTimeSlots_listController;
import controller.gestion_rdv.DoctorCalandars_listController;
import controller.gestion_rdv.Doctors_listController;
import controller.gestion_rdv.PatientCalandarTimeSlots_listController;
import controller.gestion_rdv.UserSideCalandars_listController;
/**
 *
 * @author rbaih
 */
public class ViewFactory {
    private ArrayList<Stage> activeStagesList; // keep track of all active stages in case we need them all
    private UserManager userManager; // for each managementClass 
    private RdvManager rdvManager;  // for each managementClass
    
// constructors 
    public ViewFactory(UserManager userManager ,RdvManager rdvManager ) {
        activeStagesList =new ArrayList<>();
        this.userManager = userManager;
        this.rdvManager = rdvManager;
    }
    

    
// close Current Stage method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-
   public void closeStage(Stage stageToBeClosed ){
       activeStagesList.remove(stageToBeClosed);
       stageToBeClosed.close();
   }
    
   
// private common initialize method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-
   private void initializeStage(BaseController baseController){
       //controller passed is the controller assigned to that specefic fxml module
       //fxmlName will be filled on constructor at instantiation time with the desired fxmlfile path
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource( baseController.getFxmlName()  ));
        fxmlLoader.setController(baseController);
        Parent parent;
        try {    
           parent = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        Scene scene = new Scene(parent);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
        activeStagesList.add(newStage);
       
    }
   
   private void initializeScene(Stage currentStage , BaseController baseController ){
       FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource( baseController.getFxmlName() ) );
       fxmlLoader.setController(baseController);
       Parent parent; 
       
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        Scene scene = new Scene(parent);
        currentStage.setScene(scene);
   }
   
// CSS methods _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    
// getters and setter  _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-

    public ArrayList<Stage> getActiveStagesList() {
        return activeStagesList;
    }

    public UserManager getUserManager() {
        return userManager;
    }
    
    public RdvManager getRdvManager() {
        return rdvManager;
    }
    

    
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
// view changing  GUI stages and scenes method _-_-_-_-_-_--_-__-_-_--_-_-_-_--_-_-_-_-_--_-_-_-_-_-_-_-_--_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    
    //Login Stage_Scene
    public void showLogin_newStage(){
        DummyLoginController controller = new DummyLoginController(this.userManager,"DummyLogin.fxml", this) ;
        this.initializeStage(controller);
    }
    
    public void showLogin_keepStage(Stage currentStage){             //this is the viewFactory object himself
        DummyLoginController controller = new DummyLoginController(this.userManager, "DummyLogin.fxml", this);
        this.initializeScene(currentStage, controller);
    }
    
    //DoctorWithCalandarDay_listing
    public void showDoctors_list_newStage(){
                                                                              //this is the viewFactory object himself
        Doctors_listController controller = new Doctors_listController(this.userManager,"Doctors_list.fxml", this) ;
        this.initializeStage(controller);
        
    }
    
    public void showDoctors_list_keepStage(Stage currentStage){             //this is the viewFactory object himself
        Doctors_listController controller = new Doctors_listController(this.userManager, "Doctors_list.fxml", this);
        this.initializeScene(currentStage, controller);
    }
    
    //CalandarDay_List of a certain doctor
    public void showDoctorCalandars_list_newStage(){
        DoctorCalandars_listController controller = new DoctorCalandars_listController(this.userManager, this.rdvManager,"DoctorCalandars_list.fxml", this) ;
        this.initializeStage(controller);
        
    }
    
    public void showDoctorCalandars_list_keepStage(Stage currentStage){             //this is the viewFactory object himself
        DoctorCalandars_listController controller = new DoctorCalandars_listController(this.userManager, this.rdvManager,"DoctorCalandars_list.fxml", this);
        this.initializeScene(currentStage, controller);
    }
    
    //show Doctor-View Calandar_WithRespective TIMESLOTS
    public void showCalandarTimeSlotsDoctorView_list_newStage(){
        DoctorCalandarTimeSlots_listController controller = new DoctorCalandarTimeSlots_listController( this.userManager, this.rdvManager, "DoctorCalandarTimeSlots_list.fxml" , this);
        this.initializeStage( controller );
    }
    
    public void showCalandarTimeSlotsDoctorView_list_keepStage(Stage currentStage){             //this is the viewFactory object himself
        DoctorCalandarTimeSlots_listController controller = new DoctorCalandarTimeSlots_listController(this.userManager, this.rdvManager, "DoctorCalandarTimeSlots_list.fxml" , this);
        this.initializeScene(currentStage, controller);
    }
    
    //show Patien-View Calandar_WithRespective TIMESLOTS
    public void showCalandarTimeSlotsPatientView_list_newStage(){
        PatientCalandarTimeSlots_listController controller = new PatientCalandarTimeSlots_listController( this.userManager, this.rdvManager, "PatientCalandarTimeSlots_list.fxml" , this);
        this.initializeStage( controller );
    }
    
    public void showCalandarTimeSlotsPatientView_list_keepStage(Stage currentStage){             //this is the viewFactory object himself
        PatientCalandarTimeSlots_listController controller = new PatientCalandarTimeSlots_listController(this.userManager, this.rdvManager, "PatientCalandarTimeSlots_list.fxml" , this);
        this.initializeScene(currentStage, controller);
    }
    
    //CalandarDay_List of a certain doctor
    public void showUserSideCalandars_list(){
        UserSideCalandars_listController controller = new UserSideCalandars_listController(this.userManager, this.rdvManager,"UserSideCalandars_list.fxml", this) ;
        this.initializeStage(controller);
        
    }
    
    public void showUserSideCalandars_list_keepStage(Stage currentStage){             //this is the viewFactory object himself
        UserSideCalandars_listController controller = new UserSideCalandars_listController(this.userManager, this.rdvManager,"UserSideCalandars_list.fxml", this);
        this.initializeScene(currentStage, controller);
    }
    //show TimeSLOT
    
    
//Show Appointmen
    //user-Side
    public void showUser_SideAppointment_list(){
        AppointmentUserController controller = new AppointmentUserController(this.userManager, this.rdvManager,"AppointmentUser.fxml", this) ;
        this.initializeStage(controller);
        
    }
    
    public void show_UserSideAppointment_list_keepStage(Stage currentStage){             //this is the viewFactory object himself
        AppointmentUserController controller = new AppointmentUserController(this.userManager, this.rdvManager,"AppointmentUser.fxml", this);
        this.initializeScene(currentStage, controller);
    }
    //doctor-side
    public void show_All_DoctorSideAppointment_list(Stage currentStage){             //this is the viewFactory object himself
        AppointmentDoctorController controller = new AppointmentDoctorController(this.userManager, this.rdvManager,"AppointmentDoctor.fxml", this) ;
        this.initializeStage(controller);
        
    }
    
    public void show_All_DoctorSideAppointment_list_keepStage(Stage currentStage){  
        //this is the viewFactory object himself
        AppointmentDoctorController controller = new AppointmentDoctorController(this.userManager, this.rdvManager,"AppointmentDoctor.fxml", this);
        this.initializeScene(currentStage, controller);
    }
    
    //ListAppointment
    
    
    
    
    
    
    
    
}
