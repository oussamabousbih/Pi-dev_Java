package gui;

import entities.User;
import services.EmailSendHtmlMsg;
import services.SmsService;
import services.SmtpEmailUser;
import services.UserService;
import session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoController extends AdminPageController implements Initializable  {
    @FXML
    private Button btnRetour;

    @FXML
    private Button btnBan;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lbAdresse;

    @FXML
    private Label lbAge;

    @FXML
    private Label lbCreationDate;

    @FXML
    private Label lbDateOfBirth;

    @FXML
    private Label lbEmail;

    @FXML
    private Label lbFirstName;

    @FXML
    private Label lbLastName;

    @FXML
    private Label lbLicence;

    @FXML
    private Label lbNumTel;

    @FXML
    private Label lbRole;

    @FXML
    private Label lbSexe;

    @FXML
    private Label lbSpeciality;
    @FXML
    private  Button btnUnban;
    private Stage previousStage;
    private User user;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void retour() {
            previousStage = (Stage) btnRetour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        previousStage.setScene(scene);
        previousStage.show();
    }

    @FXML
    public void delete(ActionEvent actionEvent) {

        UserService userService = new UserService();
        userService.deleteUser(getUser().getEmail());
        System.out.println("User deleted"+getUser());
        Stage stage = (Stage) btnDelete.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("adminPage.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void bannir(ActionEvent actionEvent) throws MessagingException {
        UserService userService = new UserService();
        User user = getUser();
        System.out.println(user);
        if(  user.getBanned().equals("1")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"User already banned",ButtonType.OK);
            alert.setTitle("User already banned");
            alert.setHeaderText("Too Bad ");
            alert.setContentText("this user "+ getUser().getFirstName()+" already banned");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "ban User", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("User banned");
            alert.setHeaderText("User" + getUser().getEmail());
            alert.setContentText("User banned");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {

                user.setBanned("1");
                userService.updateUser(user, user.getEmail());
                SmtpEmailUser email = new SmtpEmailUser();
                email.sendEmail(new String[]{user.getEmail()}, "account suspension", "", EmailSendHtmlMsg.htmlBanned(user.getFirstName(), "AIDME TEAM"));
                SmsService smsService = new SmsService();
                smsService.sendSmsBanned(user.getPhoneNumber(),user.getFirstName()+" "+ user.getLastName());
                System.out.println("User banned" + getUser().getBanned());
            } else if (alert.getResult() == ButtonType.CANCEL) {
                alert.close();
            }

        }
    }
    public void unBan(){
        UserService userService = new UserService();
        User user = getUser();
        if(user.getBanned().equals("0")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"This user is not banned",ButtonType.OK);
            alert.setTitle("this user is already authorized");
            alert.setHeaderText("user authorized");
            alert.setContentText("oops this user is already authorized you can't unban an authorized user");
            alert.showAndWait();
        }else {
            user.setBanned("0");
            userService.updateUser(user,user.getEmail());
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"User authorize",ButtonType.OK);
            alert.setTitle("authorized to use the application");
            alert.setHeaderText("User authorized again");
            alert.setContentText("the user " + user.getFirstName() + " is authorized to use this app again he's no longer banned");
            alert.showAndWait();
        }
    }


    public void setSelectedUser(User selectedUser) {
        lbAdresse.setText(selectedUser.getLocation() != null ? selectedUser.getLocation() : "UNDEFINED");
        lbAge.setText(selectedUser.getAge() != null ? selectedUser.getAge().toString() : "UNDEFINED");
        lbCreationDate.setText(selectedUser.getCreatedAt() != null ? selectedUser.getCreatedAt().toString() : "UNDEFINED");
        lbDateOfBirth.setText(selectedUser.getDateOfBirth() != null ? selectedUser.getDateOfBirth().toString() : "UNDEFINED");
        lbEmail.setText(selectedUser.getEmail() != null ? selectedUser.getEmail() : "UNDEFINED");
        lbFirstName.setText(selectedUser.getFirstName() != null ? selectedUser.getFirstName() : "UNDEFINED");
        lbLastName.setText(selectedUser.getLastName() != null ? selectedUser.getLastName() : "UNDEFINED");
        lbLicence.setText(selectedUser.getLicence() != null ? selectedUser.getLicence() : "UNDEFINED");
        lbNumTel.setText(selectedUser.getPhoneNumber() != null ? selectedUser.getPhoneNumber() : "UNDEFINED");
        lbRole.setText(selectedUser.getRole() != null ? selectedUser.getRole() : "UNDEFINED");
        lbSexe.setText(selectedUser.getGender() != null ? selectedUser.getGender() : "UNDEFINED");
        lbSpeciality.setText(selectedUser.getSpeciality() != null ? selectedUser.getSpeciality() : "UNDEFINED");
    }










    UserSession userSession;

   @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
























