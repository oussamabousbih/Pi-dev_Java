package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InscriptionType implements Initializable {
    @FXML
    private Button btnUser;
    @FXML
    protected Button btnDoctor;
    @FXML
    protected Button btnNurse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void user(){
        Stage stage = (Stage) btnUser.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InscriptionController inscription = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void doctor(){
    Stage stage = (Stage) btnDoctor.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("inscriptionDoctor.fxml"));
    Parent root=null;
    try{
        root=loader.load();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
    public void nurse(){
        Stage stage = (Stage) btnNurse.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscriptionNurse.fxml"));
        Parent root=null;
        try{
            root=loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
