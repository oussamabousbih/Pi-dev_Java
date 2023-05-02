package gui;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import services.UserService;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class InscriptionController implements Initializable {
    @FXML
    private TextField TFUEMAIL;
    @FXML
    private TextField TFUPASSWORD;
    @FXML
    private TextField TFUNOM;
    @FXML
    private TextField TFUPRENOM;
    @FXML
    private TextField TFUNUMTEL;
    @FXML
    private DatePicker UDATEN;
    @FXML
    private ChoiceBox USEXE;

    @FXML
    private TextField tfLicence;

    @FXML
    private TextField tfSpeciality;
    @FXML
    private TextField tfAdress;
    @FXML
    private Button BVALIDER;
    @FXML
    private Button btnAnnuler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        USEXE.getItems().addAll("Homme", "Femme");
        }

    public void saveUser() {
        if(TFUEMAIL.getText().isEmpty() || isEmail(TFUEMAIL.getText())==false || TFUPASSWORD.getText().isEmpty() || TFUNOM.getText().isEmpty() || TFUPRENOM.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }
        if (TFUNUMTEL.getText().length() != 8 || !TFUNUMTEL.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez saisir un numéro de téléphone valide");
            alert.showAndWait();
            return;
        }
        if(UDATEN.getValue()==null){
            System.out.println("select a  valid date");
            Alert alert = new Alert(Alert.AlertType.ERROR , "select a  valid date", ButtonType.OK);
            alert.setGraphic(new ImageView("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwF21ciI0nD7DWOjajVyJQxBwELAM721oObaqH5WD8G6Ja881obq6UN2oGeEyluC7l5J4&usqp=CAU"));
            alert.showAndWait();
            return;
        }
        if (TFUPASSWORD.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez saisir un mot de passe valide");
            alert.showAndWait();
            return;
        }
        if(USEXE.getValue()==null){
            System.out.println("select a  valid date");
            Alert alert = new Alert(Alert.AlertType.ERROR , "select a sexe", ButtonType.OK);
            alert.setGraphic(new ImageView("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwF21ciI0nD7DWOjajVyJQxBwELAM721oObaqH5WD8G6Ja881obq6UN2oGeEyluC7l5J4&usqp=CAU"));
            alert.showAndWait();
            return;
        }
        UserService us = new UserService();
        User user = new User();
        user.setEmail(TFUEMAIL.getText());
        user.setRole("[\"ROLE_USER\"]");
        user.setPassword(TFUPASSWORD.getText());
        user.setFirstName(TFUPRENOM.getText());
        user.setLastName(TFUNOM.getText());
        LocalDate localDate = UDATEN.getValue(); // Get the selected date from the DatePicker
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())); // Convert LocalDate to Instant
        Date date = Date.from(instant); // Convert Instant to Date
        user.setDateOfBirth(date); // Set the selected date as the user's date of birth
        user.setGender(USEXE.getValue().toString());
        user.setPhoneNumber(TFUNUMTEL.getText());
        user.setCreatedAt(new Date());
        user.setBanned("0");
        us.createUser(user);
        System.out.println("saved"+user);
        System.out.println(us.selectAllUsers());
        loadLogin();
    }

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Home home = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public boolean isEmail(String email){
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if(email.matches(emailRegex)) {
            System.out.println("Valid email");
            return true;
        }
        return false;
    }

    public void saveDoctor(ActionEvent actionEvent) {
        if(TFUEMAIL.getText().isEmpty() || isEmail(TFUEMAIL.getText())==false || TFUPASSWORD.getText().isEmpty() || TFUNOM.getText().isEmpty() || TFUPRENOM.getText().isEmpty()   || TFUNUMTEL.getText().isEmpty() || tfLicence.getText().isEmpty() || tfSpeciality.getText().isEmpty() || tfAdress.getText().isEmpty()){
            System.out.println("Veuillez remplir tous les champs");
            Alert alert = new Alert(Alert.AlertType.ERROR , "Veuillez remplir tous les champs", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (TFUNUMTEL.getText().length() != 8 || !TFUNUMTEL.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez saisir un numéro de téléphone valide");
            alert.showAndWait();
            return;
        }

        if(UDATEN.getValue()==null){
            System.out.println("select a  valid date");
            Alert alert = new Alert(Alert.AlertType.ERROR , "select a  valid date", ButtonType.OK);
            alert.setGraphic(new ImageView("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwF21ciI0nD7DWOjajVyJQxBwELAM721oObaqH5WD8G6Ja881obq6UN2oGeEyluC7l5J4&usqp=CAU"));
            alert.showAndWait();
            return;
        }
        if (TFUPASSWORD.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez saisir un mot de passe valide");
            alert.showAndWait();
            return;
        }
        if(USEXE.getValue()==null){
            System.out.println("select a  valid date");
            Alert alert = new Alert(Alert.AlertType.ERROR , "select a sexe", ButtonType.OK);
            alert.setGraphic(new ImageView("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwF21ciI0nD7DWOjajVyJQxBwELAM721oObaqH5WD8G6Ja881obq6UN2oGeEyluC7l5J4&usqp=CAU"));
            alert.showAndWait();
            return;
        }

        UserService us = new UserService();
        User user = new User();
        user.setEmail(TFUEMAIL.getText());
            user.setRole("[\"ROLE_DOCTOR\"]");
        user.setPassword(TFUPASSWORD.getText());
        user.setFirstName(TFUPRENOM.getText());
        user.setLastName(TFUNOM.getText());
        LocalDate localDate = UDATEN.getValue(); // Get the selected date from the DatePicker
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())); // Convert LocalDate to Instant
        Date date = Date.from(instant); // Convert Instant to Date
        user.setDateOfBirth(date); // Set the selected date as the user's date of birth
        user.setGender(USEXE.getValue().toString());
        user.setPhoneNumber(TFUNUMTEL.getText());
        user.setCreatedAt(new Date());
        user.setLicence(tfLicence.getText());
        user.setLocation(tfAdress.getText());
        user.setSpeciality(tfSpeciality.getText());
        user.setBanned("0");
        us.createUser(user);
        System.out.println("saved");
       loadLogin();
    }
    public void loadLogin(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION , "Vous ete inscrit en tant que Doctor", ButtonType.OK);
        alert.setTitle("voila");
        alert.setGraphic(new ImageView("https://icon-library.com/images/validity-icon/validity-icon-27.jpg"));
        alert.showAndWait();
        Stage stage = (Stage) BVALIDER.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void saveNurse(){
        if(TFUEMAIL.getText().isEmpty() || isEmail(TFUEMAIL.getText())==false || TFUPASSWORD.getText().isEmpty() || TFUNOM.getText().isEmpty() || TFUPRENOM.getText().isEmpty() || tfLicence.getText().isEmpty() || tfSpeciality.getText().isEmpty() || tfAdress.getText().isEmpty()){
            System.out.println("Veuillez remplir tous les champs");
            Alert alert = new Alert(Alert.AlertType.ERROR , "Veuillez remplir tous les champs", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        if (TFUNUMTEL.getText().length() != 8 || !TFUNUMTEL.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez saisir un numéro de téléphone valide");
            alert.showAndWait();
            return;
        }

        if(UDATEN.getValue()==null){
            System.out.println("select a  valid date");
            Alert alert = new Alert(Alert.AlertType.ERROR , "select a  valid date", ButtonType.OK);
            alert.setGraphic(new ImageView("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwF21ciI0nD7DWOjajVyJQxBwELAM721oObaqH5WD8G6Ja881obq6UN2oGeEyluC7l5J4&usqp=CAU"));
            alert.showAndWait();
            return;
        }
        if (TFUPASSWORD.getText().length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez saisir un mot de passe valide");
            alert.showAndWait();
            return;
        }
        if(USEXE.getValue()==null){
            System.out.println("select a  valid date");
            Alert alert = new Alert(Alert.AlertType.ERROR , "select a sexe", ButtonType.OK);
            alert.setGraphic(new ImageView("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwF21ciI0nD7DWOjajVyJQxBwELAM721oObaqH5WD8G6Ja881obq6UN2oGeEyluC7l5J4&usqp=CAU"));
            alert.showAndWait();
            return;
        }


        UserService us = new UserService();
        User user = new User();
        user.setEmail(TFUEMAIL.getText());
        user.setRole("[\"ROLE_NURSE\"]");
        user.setPassword(TFUPASSWORD.getText());
        user.setFirstName(TFUPRENOM.getText());
        user.setLastName(TFUNOM.getText());
        LocalDate localDate = UDATEN.getValue(); // Get the selected date from the DatePicker
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault())); // Convert LocalDate to Instant
        Date date = Date.from(instant); // Convert Instant to Date
        user.setDateOfBirth(date); // Set the selected date as the user's date of birth
        user.setGender(USEXE.getValue().toString());
        user.setPhoneNumber(TFUNUMTEL.getText());
        user.setCreatedAt(new Date());
        user.setBanned("0");
        us.createUser(user);
        System.out.println("saved");
        loadLogin();
    }
}
