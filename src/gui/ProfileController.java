package gui;

import entities.User;
import services.UserService;
import session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private TextField TFUPRENOM;
    @FXML
    private TextField TFUNOM;
    @FXML
    private TextField TFUNUMTEL;
    @FXML
    private DatePicker UDATEN;
    @FXML
    private TextField tfLicence;
    @FXML
    private TextField tfSpeciality;
    @FXML
    private TextField tfAdress;
    @FXML
    private Button BVALIDER;
    @FXML
    private Button btnRetour;
    @FXML
private Button btnLogout;



    public void magicSet(User u){
        TFUPRENOM.setText(u.getLastName());
        TFUNOM.setText(u.getFirstName());
        TFUNUMTEL.setText(u.getPhoneNumber());
        tfLicence.setText(u.getLicence());
        tfSpeciality.setText(u.getSpeciality());
        tfAdress.setText(u.getLocation());
    }
    @FXML
    public void update(ActionEvent actionEvent) {
        try {

            UserService us = new UserService();
            User user=UserSession.getSession().getUser();
            user.setFirstName(TFUNOM.getText());
            user.setLastName(TFUPRENOM.getText());
            user.setPhoneNumber(TFUNUMTEL.getText());
            user.setLicence(tfLicence.getText());
                user.setSpeciality(tfSpeciality.getText());
                user.setLocation(tfAdress.getText());

            us.updateUser(user,user.getEmail());
            System.out.println("User updated fel profile wel email: "+user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();

        }





    }

    public void retour(ActionEvent actionEvent) {
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedInPage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }



    public void logout(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



}
