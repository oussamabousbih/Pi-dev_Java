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

import java.io.IOException;

public class Login implements Initializable {
    @FXML
    TextField tfEmail ;
    @FXML
    TextField tfPassword ;
    @FXML
    Button btnValider;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Hyperlink resetPassword;



    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

    }
    public void login(){

        UserService us = new UserService();
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        User user = us.login(email,password);
        
        if( user!=null && user.getRole().equals("[\"ROLE_ADMIN\"]")){
            System.out.println("ahla");
            UserSession.setSession(user);
            user.setStatus("online");
            us.updateUser(user,user.getEmail());
            System.out.println(UserSession.getSession().getUser());
            System.out.println("login ADMIN");
            System.out.println(user);
            Stage stage = (Stage) btnValider.getScene().getWindow();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("adminPage.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }


        if(user!=null &&  user.getBanned().equals("0")&& (user.getRole().equals("[\"ROLE_USER\"]") || user.getRole().equals("[\"ROLE_DOCTOR\"]") || user.getRole().equals("[\"ROLE_NURSE\"]")  )){
            UserSession.setSession(user);

            if(user.getIsVerified()!=1){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Voullez vous confirmer votre compte ?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Verifier Votre Compte");
                alert.setHeaderText("Vous devez confirmer votre compte");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.NO){
                    Stage stage = (Stage) btnValider.getScene().getWindow();
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("loggedInPage.fxml"));
                    Parent root = null;
                    System.out.println(UserSession.getSession().getUser());
                    System.out.println("login USER");
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    System.out.println("NOOO");

                }
                else{
                    Stage stage = (Stage) btnValider.getScene().getWindow();
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("verifyAccountInterface.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    System.out.println("YESS");
                }

            }else {
                Stage stage = (Stage) btnValider.getScene().getWindow();
                FXMLLoader loader= new FXMLLoader(getClass().getResource("loggedInPage.fxml"));
                Parent root = null;
                System.out.println(UserSession.getSession().getUser());
                System.out.println("login USER");
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            System.out.println(user);

        }else if(user!=null   && user.getBanned().equals("1")){
            System.out.println(" fel login ");
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"vous êtes banni",ButtonType.OK);
            alert.setTitle("Banned Alert");
            alert.setHeaderText("You are Banned");
            alert.setContentText(user.getFirstName()+" you are banned for further information you should contact our support team on aidme.io.tn@gmail.com");
            alert.showAndWait();
        }
        else{
            System.out.println("email ou mot de passe incorrect");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Email ou mot de passe incorrect");
            alert.showAndWait();
            System.out.println("login");
        }

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
    public void resetPassword (){
        Stage stage = (Stage) resetPassword.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("resetPassword.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();
    }
}
