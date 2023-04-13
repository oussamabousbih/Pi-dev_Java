/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Article;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.CRUDArticle;

/**
 * FXML Controller class
 *
 * @author mazee
 */
public class ModifierAController implements Initializable {

    @FXML
    private TextField idArticle;
    @FXML
    private TextField fxsujet;
    @FXML
    private TextArea fxcontenu;
    @FXML
    private ChoiceBox<String> idChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CRUDArticle art = new CRUDArticle();
       List<Article> articles = art.afficherArticle();
       for (Article article : articles) {
        idChoiceBox.getItems().add(article.getSujet());
       }
    }     

    @FXML
    private void save(ActionEvent event) {
        
        String sujet = fxsujet.getText();
        String contenu = fxcontenu.getText();
        
        CRUDArticle art = new CRUDArticle();
       List<Article> articles = art.afficherArticle();
    String selectedContent = idChoiceBox.getValue();
    Article selectedArticle = null;
    for (Article article : articles) {
        if (article.getSujet().equals(selectedContent)) {
            selectedArticle = article;
            break;
        }
    }
    if (selectedArticle != null) {
        int id = selectedArticle.getId();
        art.modifierArticle(new Article(id,sujet,contenu));
    }
    


        

        
        
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Confirmation Dialog");

        alert.setHeaderText(null);

        alert.setContentText("Article modifier avec succés !");

        alert.show();


    }
    
    
}
