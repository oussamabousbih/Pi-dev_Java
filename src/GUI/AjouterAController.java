/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Article;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.CRUDArticle;

/**
 * FXML Controller class
 *
 * @author mazee
 */
public class AjouterAController implements Initializable {

    @FXML
    private TextField fxid;
    @FXML
    private TextField fxsujet;
    @FXML
    private TextField fxcontenu;
    @FXML
    private TextField fximage;
    @FXML
    private Button fxajouter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void save(ActionEvent event) {
        
        int id = Integer.parseInt(fxid.getText());
        String sujet = fxsujet.getText();
        String contenu = fxcontenu.getText();
        String image = fximage.getText();
        
        Article a = new Article(id,sujet,contenu,image);
        
        CRUDArticle art = new CRUDArticle();
        
        art.ajouterArticle(a);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmation Dialog");

        alert.setHeaderText(null);

        alert.setContentText("Article insérée avec succés !");

        alert.show();

    }
    
}
