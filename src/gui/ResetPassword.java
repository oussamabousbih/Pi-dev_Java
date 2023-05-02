package gui;

import entities.User;
import services.EmailSendHtmlMsg;
import services.SmsService;
import services.SmtpEmailUser;
import services.UserService;
import session.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResetPassword implements Initializable {

    @FXML
    private Button btnEnvoyer;

    @FXML
    private TextField tfMail;
    @FXML
    private TextField tfSms;
    @FXML
    private Button btnSms;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void resetForgotPasswordEmail() throws MessagingException {
        UserService us = new UserService();
        if(us.afficherUser( tfMail.getText())!=null){
            User user = us.afficherUser( tfMail.getText());
            UserSession.setSession(user);
            SmtpEmailUser smtpEmail = new SmtpEmailUser();
            int code = (int) (Math.random() * 9999);
            user.setResetToken(""+code);
            System.out.println(user.getResetToken());
            us.updateUser(user,user.getEmail());
            String html = EmailSendHtmlMsg.htmlResetPassword(user.getFirstName(),code,"AIDME");
            smtpEmail.sendEmail(new String[]{tfMail.getText()},"reset Password", "" ,html);
            Stage stage = (Stage) btnEnvoyer.getScene().getWindow();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("checkToken.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
        }
        else {
            System.out.println("mail not found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email not found");
            alert.show();
        }

    }
    @FXML
    public void sendforgotPasswordSms() {
        UserService us = new UserService();
        if(us.afficherUser( tfMail.getText())!=null){
            User user = us.afficherUser( tfMail.getText());
            UserSession.setSession(user);
            SmsService smsService = new SmsService();
            int code = (int) (Math.random() * 9999);
            user.setResetToken(""+code);
            System.out.println(user.getResetToken());
            us.updateUser(user,user.getEmail());
            smsService.sendSmsResetPassword(user.getPhoneNumber(),code+"",user.getFirstName());
            Stage stage = (Stage) btnSms.getScene().getWindow();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("checkToken.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
        }
        else {
            System.out.println("numéro de téléphone inexistant");
            Alert alert = new Alert(Alert.AlertType.NONE, "numéro de téléphone inexistant", javafx.scene.control.ButtonType.OK);
            alert.setTitle("numéro inexistant");
            alert.show();
        }


    }

}
