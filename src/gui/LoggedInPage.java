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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class LoggedInPage  implements Initializable {
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnLogout;
    @FXML
    private ListView<String> listView;


    private UserSession userSession;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userSession = UserSession.getSession();
        User user = userSession.getUser();
        listView.getItems().add("salut "+user.getFirstName()+"");
        listView.getItems().add(""+user.getLastName()+"");
    }


    public void profile(ActionEvent actionEvent) {
        Stage stage = (Stage) btnProfile.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("profile.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProfileController profile = loader.getController();
        profile.magicSet(userSession.getUser());
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();

    }

    public void logout(ActionEvent actionEvent) {
        UserService us = new UserService();
        User user = UserSession.getSession().getUser();

        user.setLastLogin(LocalDate.now().atTime(0,0,0).toString());
        user.setStatus("offline");
        us.updateUser(user,user.getEmail());
        userSession.clearSession();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("login.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(loader.getRoot());
        stage.setScene(scene);
        stage.show();
        System.out.println("ena fel logout      session  = "+UserSession.getSession());


    }
}
