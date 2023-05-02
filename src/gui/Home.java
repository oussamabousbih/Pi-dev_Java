package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

;import java.io.IOException;

public class Home implements Initializable {
    @FXML
    Button connect;
    @FXML
    Button inscrire;
        @Override
        public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        }

    public void loginPage(ActionEvent actionEvent) {
        Stage stage = (Stage) connect.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Login login = loader.getController();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("edu/connexion3a35/gui/css/style.css");
        stage.setScene(scene);
        stage.show();
    }

    public void inscrirePage(ActionEvent actionEvent) {
            Stage stage = (Stage) inscrire.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inscriptionType.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            scene.getStylesheets().add("edu/connexion3a35/gui/css/style.css");
            stage.setScene(scene);
            stage.show();

        }

}
