/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Article;
import interfaces.InterfaceArticle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyConnection;

/**
 *
 * @author mazee
 */
public class CRUDArticle implements InterfaceArticle{
    Statement ste;
    Connection conn = MyConnection.getInstance().getConn(); 

    @Override
    public void ajouterArticle(Article a) {
        try {
            String req="INSERT INTO `article`(`user_id_id`, `sujet`, `contenu`, `image`) VALUES ('"+a.getId_Auteur()+"','"+a.getSujet()+"','"+a.getContenu()+"','"+a.getImage()+"')";
            ste=conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("Article ajoutée avec succès !!");
        } catch (SQLException ex) {
            System.out.println("Article non ajoutée !!");
            System.out.println(ex.getMessage());
        }
        }

    @Override
    public void modifierArticle(Article a) {
        try {
            String rq="UPDATE `article` SET `sujet`='"+a.getSujet()+"',`contenu`='"+a.getContenu()+"' WHERE `article`.`id`='"+a.getId()+"';";
            ste=conn.createStatement();
            ste.executeUpdate(rq);
            System.out.println("Article modifier avec succès !!");
        } catch (SQLException ex) {
            System.out.println("Article non modifier !!");
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerArticle(Article a) {
               try {
            String rq="DELETE FROM `article` WHERE `article`.`id`='"+a.getId()+"';";
            ste=conn.createStatement();
            ste.executeUpdate(rq);
            System.out.println("Article supprimée avec succès !!");
        } catch (SQLException ex) {
            System.out.println("Article non supprimée !!");
        }
    }

    @Override
    public List<Article> afficherArticle() {
        List<Article> list = new ArrayList<>();
        
        String req=" SELECT * FROM `article` ";
        Statement ste;
        try {
            ste = conn.createStatement();
            ResultSet RS= ste.executeQuery(req);
            while(RS.next()){
                Article a1 = new Article();
                a1.setId(RS.getInt(1));
                a1.setId_Auteur(RS.getInt("user_id_id"));
                a1.setSujet(RS.getString("Sujet"));
                a1.setContenu(RS.getString("Contenu"));
                a1.setCreated_at(RS.getString("Created_at"));
                a1.setViews(RS.getInt("Views"));
                list.add(a1);
            }
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    @Override
    public Article afficherArticleDetail(int articleId) {
    Article article = null;
    String req = "SELECT * FROM article WHERE id = " + articleId;
    try {
        Statement ste = conn.createStatement();
        ResultSet RS = ste.executeQuery(req);
        if (RS.next()) {
            article = new Article();
            article.setId(RS.getInt("id"));
            article.setId_Auteur(RS.getInt("user_id_id"));
            article.setSujet(RS.getString("Sujet"));
            article.setContenu(RS.getString("Contenu"));
            article.setCreated_at(RS.getString("Created_at"));
            article.setViews(RS.getInt("Views"));
            article.setImage(RS.getString("image"));
        }
        System.out.println("ok");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return article;
}
    public String statArticle() {
    String count = "";
    String req = "SELECT count(*) FROM article";
    try {
        Statement ste = conn.createStatement();
        ResultSet RS = ste.executeQuery(req);
        if (RS.next()) {
            count = RS.getString(1);
        }
        RS.close();
        ste.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return count;
}
    public String statArticleToday() {
    String count = "";
    String req = "SELECT COUNT(*) as num_articles_today FROM article "
               + "WHERE created_at >= CURDATE() AND created_at < DATE_ADD(CURDATE(), INTERVAL 1 DAY)";
    try {
        Statement ste = conn.createStatement();
        ResultSet RS = ste.executeQuery(req);
        if (RS.next()) {
            count = RS.getString("num_articles_today");
        }
        RS.close();
        ste.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return count;
}


    
    
}
