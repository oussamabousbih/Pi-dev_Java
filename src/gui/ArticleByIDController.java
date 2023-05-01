/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Article;
import entities.Commentaire;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import javafx.stage.Stage;

import services.CRUDArticle;
import services.CRUDCommentaire;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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
        List<String> badWords = Arrays.asList("bhim", "badword");
        Article article = art.afficherArticleDetail(articleId);
        List<Commentaire> commentaires = cmnt.afficherCommentaireDetail(articleId); 
        StringBuilder sb = new StringBuilder();
            
        if (article != null) {

            String sanitizedArticleContent = article.getContenu();
            String sanitizedArticleSujet = article.getSujet();
    for (String badWord : badWords) {
        sanitizedArticleContent = sanitizedArticleContent.replaceAll("(?i)" + badWord, "****");
        sanitizedArticleSujet = sanitizedArticleSujet.replaceAll("(?i)" + badWord, "****");
    }
            sb.append("Auteur : ").append(article.getId_Auteur()).append("\n");
            sb.append("Views : ").append(article.getViews()).append("\n");
            sb.append("Date : ").append(article.getCreated_at()).append("\n");
            sb.append("Sujet Article : ").append(sanitizedArticleSujet).append("\n");
            sb.append("Contenu : ").append(sanitizedArticleContent).append("\n\n");


            sb.append("-------- Comments --------\n\n"); // add a separator here



// Loop through the comments and sanitize the content
for (Commentaire commentaire : commentaires) {
    String sanitizedContent = commentaire.getContenu();
    for (String badWord : badWords) {
        sanitizedContent = sanitizedContent.replaceAll("(?i)" + badWord, "****");
    }
    sb.append("Commentateur : ").append(commentaire.getId_Auteur()).append("\n");
    sb.append("Date Commentaire: ").append(commentaire.getCreated_at()).append("\n");
    sb.append("Contenu Commentaire : ").append(sanitizedContent).append("\n\n");
    sb.append("-------------------------------------\n\n"); // add a separator here
}
        } else {
            sb.append("Article not found.");
        }

        Label fxarticle = new Label(sb.toString());
        fxarticle.setWrapText(true);
        fxarticleScrollPane.setContent(fxarticle);
    }
    
    public void downloadToPDF() {
        
    StringBuilder sb = new StringBuilder();
    CRUDArticle art = new CRUDArticle();
            CRUDCommentaire cmnt = new CRUDCommentaire();

    List<Commentaire> commentaires = cmnt.afficherCommentaireDetail(articleId); 

    Article article = art.afficherArticleDetail(articleId);
                String sanitizedArticleContent = article.getContenu();
            String sanitizedArticleSujet = article.getSujet();
                    List<String> badWords = Arrays.asList("bhim", "badword");

                for (String badWord : badWords) {
        sanitizedArticleContent = sanitizedArticleContent.replaceAll("(?i)" + badWord, "****");
        sanitizedArticleSujet = sanitizedArticleSujet.replaceAll("(?i)" + badWord, "****");
    }
                

        String id = "Auteur : " + article.getId_Auteur();
        String Views = "Views : " + article.getViews();
        String Date = "Date : " + article.getCreated_at();
        String Sujet = "Sujet Article : " + sanitizedArticleSujet;
        String Content = "Contenu : " + sanitizedArticleContent;
        String Space = "-------- Comments --------";
        

    File file = new File("article.pdf");
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
contentStream.setFont(PDType0Font.load(document, new File("c:/windows/fonts/Helvetica.ttf")), 12);



            contentStream.beginText();
            contentStream.newLineAtOffset(25, 700);
            contentStream.setLeading(14.5f);  // set the size of the newline to something reasonable
            contentStream.showText(id);
            contentStream.newLine();
            contentStream.showText(Views);
            contentStream.newLine();
            contentStream.showText(Date);
            contentStream.newLine();
            contentStream.showText(Sujet);
            contentStream.newLine();
            contentStream.showText(Content);
            contentStream.newLine();
            contentStream.showText(Space);
            for (Commentaire commentaire : commentaires) {
    String sanitizedContent = commentaire.getContenu();
    for (String badWord : badWords) {
        sanitizedContent = sanitizedContent.replaceAll("(?i)" + badWord, "****");
    }
    String id1 = "Commentateur : " + commentaire.getId_Auteur();
    String Date1 = "Date Commentaire : " + commentaire.getCreated_at();
    String Content1 = "Contenu Commentaire : " + sanitizedContent;
    String Space1 = "-------------------------------------" ;


            contentStream.newLine();
            contentStream.showText(id1);
            contentStream.newLine();
            contentStream.showText(Date1);
            contentStream.newLine();
            contentStream.showText(Content1);
            contentStream.newLine();
            contentStream.showText(Space1);
            }
            contentStream.endText();
        }
        
        document.save(file);
        document.close();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
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

