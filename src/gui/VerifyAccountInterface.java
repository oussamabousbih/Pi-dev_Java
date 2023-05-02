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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;

public class VerifyAccountInterface  {

    @FXML
    private Button btnEmail;

    @FXML
    private Button btnPhoneNumber;

    @FXML
    void emailVerify(ActionEvent event) throws MessagingException {
        User user = UserSession.getSession().getUser();
        UserService us = new UserService();
        int code = (int) (Math.random() * 9999);
        user.setResetToken(String.valueOf(code));
        us.updateUser(user, user.getEmail());
        System.out.println(user);
        SmtpEmailUser smtpEmail = new SmtpEmailUser();
        String html = EmailSendHtmlMsg.htmlVerifyAccount(user.getFirstName(), code+"", "AIDME");
        smtpEmail.sendEmail(new String[]{user.getEmail()}, "Verify Account", "", html);
        Stage stage = (Stage) btnEmail.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("checkTokenVerifyAccount.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void phoneNumberVerify() {
        User user = UserSession.getSession().getUser();
        UserService us = new UserService();
        int code = (int) (Math.random() * 9999);
        user.setResetToken(String.valueOf(code));
        us.updateUser(user, user.getEmail());
        SmsService smsService = new SmsService();
        smsService.sendSms(user.getPhoneNumber(), "Your verification code is: "+code,user.getFirstName());

        Stage stage = (Stage) btnPhoneNumber.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("checkTokenVerifyAccount.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();


    }

}
