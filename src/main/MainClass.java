/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import GUI.ViewFactory;
import controller.gestion_rdv.services.CalandarEXCEL;
import controller.gestion_rdv.services.CalandarPDF;
import controller.gestion_rdv.services.EmailSendHtmlMsg;
import controller.gestion_rdv.services.MySmtpEmail;
import entities.DummyUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.gestion_rdv.CrudCalandarDay;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;

/**
 *
 * @author rbaih
 */
public class MainClass extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ViewFactory viewFactory = new ViewFactory(new UserManager(), new RdvManager());
        viewFactory.showLogin_newStage();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        try {
            //        try {
//            MySmtpEmail smtp = new MySmtpEmail("2a21group5@gmail.com", "cotlmerzeszyfwuu");
//            smtp.set_emailsToSentTo(new String[]{"rabbehs@gmail.com","rabbehseif@gmail.com"} );
//            String[] emailBody;
//            emailBody = EmailSendHtmlMsg.htmlNotifyCancelHtmlMsgReturn_plus_rawText("seif", "amin", LocalDate.now().format(DateTimeFormatter.ISO_DATE), "legends-Dev");
//            
//            smtp.setEmailSubject_andBody("notification asshome","", emailBody[0]);
//            smtp.sendEmail();
//
//            
//        } catch (MessagingException ex ) {
//            ex.printStackTrace();
//        }

            
           
//        } catch (ConnectionOrPrepareStatmentException ex) {
//            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        launch(args);
    }

}
