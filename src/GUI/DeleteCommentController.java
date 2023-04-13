/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Article;
import entities.Commentaire;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import services.CRUDArticle;
import services.CRUDCommentaire;

/**
 * FXML Controller class
 *
 * @author mazee
 */
public class DeleteCommentController implements Initializable {

    @FXML
    private ChoiceBox<String> fxchoice;
    @FXML
    private Button fxdelete;
    
    private int articleId; // New private variable

    public void setArticleId(int id) {
        this.articleId = id;
                System.out.println(articleId);

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CRUDCommentaire cmnt = new CRUDCommentaire();
        List<Commentaire> commentaires = cmnt.afficherCommentaireDetail(articleId); 
        System.out.println(commentaires);
        System.out.println(articleId);
        for (Commentaire commentaire : commentaires) {
        fxchoice.getItems().add(commentaire.getContenu());
       }
    }    
    @FXML
    private void save(ActionEvent event) {
                CRUDCommentaire cmnt = new CRUDCommentaire();

        List<Commentaire> commentaires = cmnt.afficherCommentaireDetail(articleId); 

    String selectedContent = fxchoice.getValue();
    Commentaire selectedCommentaire = null;
    for (Commentaire commentaire : commentaires) {
        if (commentaire.getContenu().equals(selectedContent)) {
            selectedCommentaire = commentaire;
            break;
        }
    }
    if (selectedCommentaire != null) {
        int id = selectedCommentaire.getId();
        cmnt.supprimerCommentaire(new Commentaire(id));
    }

                

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmation Dialog");

        alert.setHeaderText(null);

        alert.setContentText("Article supprimée avec succés !");

        alert.show();
        

    }   
    
}
