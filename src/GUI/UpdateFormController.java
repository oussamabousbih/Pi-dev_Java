/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Service;
import Services.CRUDService;
import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yasmine Rajhi
 */
public class UpdateFormController implements Initializable {

    @FXML
    private TextField ID;
    @FXML
    private TextField Nom_serv;
    @FXML
    private TextField prop;
    @FXML
    private TextField prix;
    @FXML
    private DatePicker date_deb;
    @FXML
    private DatePicker date_fin;
    @FXML
    private Button update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void initData(Service service) {
    ID.setText(String.valueOf(service.getId()));
    Nom_serv.setText(service.getNomService());
    prop.setText(service.getProprietaire());
    prix.setText(service.getPrix());
    java.sql.Date date_debut = (java.sql.Date) service.getDate_debut();
    LocalDate localDateDebut = date_debut.toLocalDate();
    date_deb.setValue(localDateDebut);
    java.sql.Date datefin = (java.sql.Date) service.getDate_fin();
    LocalDate localDatefin = datefin.toLocalDate();
    date_fin.setValue(localDatefin);
    }

    @FXML
    private void update(ActionEvent event) {
        int id = Integer.parseInt(ID.getText());
        String Name = Nom_serv.getText();
        String Prop = prop.getText();
        //LocalDate date_debut = LocalDate.parse(date_deb.getText());
        //LocalDate date_debut;
        //date_debut = LocalDate.parse(date_deb.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // LocalDate date_f = LocalDate.parse(date_f.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Service S = new Service();
        //Service S = new Service(id, Name, Prop, Prop, Prop, date_debut, date_f);
        CRUDService crud = new CRUDService();
        crud.updateServices(S);
        System.out.println("Le truc trac avec l'ID " + id + " a été modifié dans la base de données");
        Stage stage = (Stage) update.getScene().getWindow();
        stage.close();
    }
    
}
