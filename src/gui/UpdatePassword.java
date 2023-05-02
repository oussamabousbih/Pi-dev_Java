package gui;

import entities.User;
import services.PasswordHasher;
import services.UserService;
import session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePassword implements Initializable {

    @FXML
    private Button btnEnvoyer;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfPasswordConfirm;

    private UserSession userSession;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userSession = UserSession.getSession();
    }

    @FXML
    void updatePasswordMethod(ActionEvent event) {
        UserService us = new UserService();
        User user = UserSession.getSession().getUser();
        user.setResetToken(null);
        user.setPassword(PasswordHasher.hashPassword(tfPassword.getText()));
        if(tfPassword.getText().equals(tfPasswordConfirm.getText())){
            System.out.println("password updated");
            us.updateUser(user,user.getEmail());
            Stage stage = (Stage) btnEnvoyer.getScene().getWindow();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("login.fxml"));
            userSession.clearSession();
            System.out.println(userSession.getUser());
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
        else {
            System.out.println("password not updated");
        }
    }
}
