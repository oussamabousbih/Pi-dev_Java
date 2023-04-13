/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entities.Article;
import entities.Commentaire;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CRUDArticle;
import services.CRUDCommentaire;

/**
 * FXML Controller class
 *
 * @author mazee
 */
public class ArticleByIDController implements Initializable {

    @FXML
    private Label fxarticle;

    private int articleId; // New private variable
    @FXML
    private Button fxcomment;
    @FXML
    private ScrollPane fxarticleScrollPane;
    
    
       public void setArticleId(int id) {
        this.articleId = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                CRUDArticle art = new CRUDArticle();
        CRUDCommentaire cmnt = new CRUDCommentaire();

        Article article = art.afficherArticleDetail(articleId);
        List<Commentaire> commentaires = cmnt.afficherCommentaireDetail(articleId); 
        StringBuilder sb = new StringBuilder();
        if (article != null) {
            String contenu = article.getContenu();
            
            sb.append("Auteur : ").append(article.getId_Auteur()).append("\n");
            sb.append("Views : ").append(article.getViews()).append("\n");
            sb.append("Date : ").append(article.getCreated_at()).append("\n");
            sb.append("Sujet Article : ").append(article.getSujet()).append("\n");
            sb.append("Contenu : ").append(contenu).append("\n\n");
            
            sb.append("-------- Comments --------\n\n"); // add a separator here

            for (Commentaire commentaire : commentaires) {

                sb.append("Commentateur : ").append(commentaire.getId_Auteur()).append("\n");
                sb.append("Date Commentaire: ").append(commentaire.getCreated_at()).append("\n");
                sb.append("Contenu Commentaire : ").append(commentaire.getContenu()).append("\n\n");
                sb.append("-------------------------------------\n\n"); // add a separator here
            


}
        } else {
            sb.append("Article not found.");
        }

        Label fxarticle = new Label(sb.toString());
        fxarticle.setWrapText(true);
        fxarticleScrollPane.setContent(fxarticle);
    }
    @FXML
private void handleaddComment(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddComment.FXML"));
        Parent root = loader.load();
        AddCommentController addCommentController = loader.getController();
        addCommentController.setArticleId(articleId);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Ajouter Commentaire");
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    @FXML
private void handledeleteComment(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteComment.FXML"));
        Parent root = loader.load();
        DeleteCommentController deleteCommentController = loader.getController();
        deleteCommentController.setArticleId(articleId);
        deleteCommentController.initialize(null, null); // Call the initialize() method manually
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Supprimer Commentaire");
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
@FXML
private void handleupdateComment(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateComment.FXML"));
        Parent root = loader.load();
        UpdateCommentController updateCommentController = loader.getController();
        updateCommentController.setArticleId(articleId);
        updateCommentController.initialize(null, null); // Call the initialize() method manually
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Supprimer Commentaire");
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
    }
}





}

