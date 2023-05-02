package gui;

import entities.User;
import services.UserService;
import session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckTokenVerifyAccount {

    @FXML
    private Button btnValidate;

    @FXML
    private TextField tfCode;

    private UserSession userSession;

    @FXML
    void verifyMyAccount(ActionEvent event) {
        userSession = UserSession.getSession();
        User user = userSession.getUser();
        UserService us = new UserService();
        if (user.getResetToken().equals(tfCode.getText())) {
            user.setResetToken(null);
            user.setIsVerified(1);
            us.updateUser(user, user.getEmail());
            UserSession.setSession(user);
            Stage stage = (Stage) btnValidate.getScene().getWindow();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("LoggedInPage.fxml"));
            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Verified");
            alert.setHeaderText("Your account has been verified successfully");
            alert.setContentText("You can now use all features of the application");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Code");
            alert.setContentText("Please enter the code sent to your email or SMS");
            alert.showAndWait();

        }

    }

}
