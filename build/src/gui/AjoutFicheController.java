/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Ficheconsultation;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import services.CRUDfiche;

/**
 * FXML Controller class
 *
 * @author AOUADI HADIL
 */
public class AjoutFicheController implements Initializable {

    @FXML
    private TextField fxid;
    @FXML
    private TextField fxdate_consultation;
    @FXML
    private TextField fxfirstName;
    @FXML
    private TextField fxlastName;
    @FXML
    private TextField fxspecialite;
    @FXML
    private TextField fxtraitemant;
    @FXML
    private TextField fxreccomendation;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button fxhome;
    @FXML
    private Button fxajout;
    @FXML
    private Button fxafficheList;
    @FXML
    private Button fxenvoyer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterFiche(ActionEvent event) {
        
            int id = Integer.parseInt(fxid.getText());
            LocalDate date_consultation;

           try {
            date_consultation = LocalDate.parse(fxdate_consultation.getText());
           } catch (DateTimeParseException e) {
            showAlert("Date de consultation invalide");
            return;


    }
            String firstName= fxfirstName.getText();
            String lastName = fxlastName.getText();
            String specialite= fxspecialite.getText();
            String traitement = fxtraitemant.getText();
            String reccomendation = fxreccomendation.getText();
            
            if (firstName.isEmpty() || lastName.isEmpty() || specialite.isEmpty() || traitement.isEmpty() || reccomendation.isEmpty()) {
        showAlert("Veuillez remplir tous les champs");
        return;
    }
           
            Ficheconsultation F = new Ficheconsultation( date_consultation,firstName, lastName,  specialite,  traitement,  reccomendation);
            CRUDfiche CF = new CRUDfiche ();
            CF.ajouterFiche(F);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheFiche.fxml"));
            try {
            Parent root = loader.load();
            AfficheFicheController AFC = loader.getController();
            AFC.setfxid(""+F.getId());
            AFC.setfxdate_consultation(F.getDate_consultation().toString());
            AFC.setfxfirstName(F.getFirstName());
            AFC.setfxlastName(F.getLastName());
            AFC.setfxspecialite(F.getSpecialite());
            AFC.setfxtraitement(F.getTraitement());
            AFC.setfxreccomendation(F.getReccomendation());
           
            
            fxfirstName.getScene().setRoot(root);
        } catch (IOException ex) {
                System.out.println("Error"+ ex.getMessage());
        }
    }
    private boolean isValidEmail(String email) {
    String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    return email.matches(emailPattern);
}

    private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur de saisie");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    private void affichelistfiche(ActionEvent event) {
    CRUDfiche CF = new CRUDfiche ();
    List<Ficheconsultation> fiches = CF.afficherFiche();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheListFiche.fxml"));
    try {
        Parent root = loader.load();
        AfficheListFicheController ALCF = loader.getController();
        ALCF.setFiches(fiches);
        fxfirstName.getScene().setRoot(root);
    } catch (IOException ex) {
        System.out.println("Error"+ ex.getMessage());
    }
}

    @FXML
    private void orientationhomeFiche(ActionEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFiche.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(HomeFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void afficheList(ActionEvent event) {
            CRUDfiche CF = new CRUDfiche ();
    List<Ficheconsultation> fiches = CF.afficherFiche();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficheListFiche.fxml"));
    try {
        Parent root = loader.load();
        AfficheListFicheController ALCF = loader.getController();
        ALCF.setFiches(fiches);
        Scene scene = new Scene(root);
        
        // Récupérer la scène actuelle pour la remplacer par la nouvelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace(); // afficher les détails de l'erreur dans la console
    Logger.getLogger(AfficheListFicheController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    

    @FXML
    private void envoyaermail(ActionEvent event) {
        Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

     Session session = Session.getInstance(props,
  new javax.mail.Authenticator() {
    @Override
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication("aidme.io.tn@gmail.com", "pjbrnycqrolmwtyh");
    }
  });

    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("hedil.aouadi@esprit.tn"));
        message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse("awadihedil@gmail.com"));
        message.setSubject("Consultation information");

        // Récupération des informations de la consultation
        String firstName = fxfirstName.getText();
        String lastName = fxlastName.getText();
        String traitement = fxtraitemant.getText();
        String recommandation = fxreccomendation.getText();
        String dateConsultation = fxdate_consultation.getText();
        
        // Construction du contenu de l'email
        String emailContent = "Nom : " + lastName + "\n" +
                              "Prénom : " + firstName + "\n" +
                              "Traitement : " + traitement + "\n" +
                              "Recommandation : " + recommandation + "\n" +
                              "Date de consultation : " + dateConsultation;

        message.setText(emailContent);

        Transport.send(message);

        System.out.println("Email envoyé avec succès");

    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}
    }

   
    

