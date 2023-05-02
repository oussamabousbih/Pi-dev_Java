package gui;

import entities.User;
import services.UserService;
import session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckToken {

    @FXML
    private Button btnValider;

    @FXML
    private TextField tfCode;

    @FXML
    private  Button btnRetour;

    @FXML
    void redirectToUpdate(ActionEvent event) {
        User user= UserSession.getSession().getUser();
        if(tfCode.getText().equals(user.getResetToken())){
            System.out.println("token correct");
            Stage stage = (Stage) btnValider.getScene().getWindow();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("updatePassword.fxml"));
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
            System.out.println("token incorrect");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error wrong Code");
            alert.setHeaderText("Token incorrect");
            alert.show();
        }

    }

    @FXML
    public void returnBack(){
        Stage stage = (Stage) btnRetour.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("login.fxml"));
    }

}
