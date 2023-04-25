/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Article;
import entities.Commentaire;
import interfaces.InterfaceArticle;
import interfaces.InterfaceCommentaire;
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
public class CRUDCommentaire implements InterfaceCommentaire{
    Statement ste;
    Connection conn = MyConnection.getInstance().getConn(); 

    @Override
    public void ajouterCommentaire(Commentaire c) {
        try {
            String req="INSERT INTO `commentaire`(`article_id`, `user_id_id`, `contenu`) VALUES ('"+c.getId_Article()+"','"+c.getId_Auteur()+"','"+c.getContenu()+"')";
            ste=conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("Commentaire ajoutée avec succès !!");
        } catch (SQLException ex) {
            System.out.println("Commentaire non ajoutée !!");
            System.out.println(ex.getMessage());
        }
        }

    @Override
    public void modifierCommentaire(Commentaire c) {
        try {
            String rq="UPDATE `commentaire` SET `contenu`='"+c.getContenu()+"' WHERE `commentaire`.`id`='"+c.getId()+"';";
            ste=conn.createStatement();
            ste.executeUpdate(rq);
            System.out.println("Commentaire modifier avec succès !!");
        } catch (SQLException ex) {
            System.out.println("Commentaire non modifier !!");
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerCommentaire(Commentaire c) {
               try {
            String rq="DELETE FROM `commentaire` WHERE `commentaire`.`id`='"+c.getId()+"';";
            ste=conn.createStatement();
            ste.executeUpdate(rq);
            System.out.println("Commentaire supprimée avec succès !!");
        } catch (SQLException ex) {
            System.out.println("Commentaire non supprimée !!");
        }
    }

    @Override
    public List<Commentaire> afficherCommentaire() {
        List<Commentaire> list = new ArrayList<>();
        
        String req=" SELECT * FROM `commentaire` ";
        Statement ste;
        try {
            ste = conn.createStatement();
            ResultSet RS= ste.executeQuery(req);
            while(RS.next()){
                Commentaire a1 = new Commentaire();
                a1.setId(RS.getInt(1));
                a1.setId_Auteur(RS.getInt("user_id_id"));
                a1.setId_Article(RS.getInt("article_id"));
                a1.setContenu(RS.getString("Contenu"));
                a1.setCreated_at(RS.getString("Created_at"));
                list.add(a1);
            }
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    
    public List<Commentaire> afficherCommentaireDetail(int articleId) {
    List<Commentaire> list = new ArrayList<>();
    String req = "SELECT * FROM `commentaire` WHERE article_id = " + articleId;
     try {
            ste = conn.createStatement();
            ResultSet RS= ste.executeQuery(req);
            while(RS.next()){
                Commentaire a1 = new Commentaire();
                a1.setId(RS.getInt(1));
                a1.setId_Auteur(RS.getInt("user_id_id"));
            
                a1.setContenu(RS.getString("Contenu"));
                a1.setCreated_at(RS.getString("Created_at"));
                list.add(a1);
            }
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
}
    public String statCommentaire() {
    String count = "";
    String req = "SELECT count(*) FROM commentaire";
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
    public String statCommentToday() {
    String count = "";
    String req = "SELECT COUNT(*) as num_comment_today FROM commentaire "
               + "WHERE created_at >= CURDATE() AND created_at < DATE_ADD(CURDATE(), INTERVAL 1 DAY)";
    try {
        Statement ste = conn.createStatement();
        ResultSet RS = ste.executeQuery(req);
        if (RS.next()) {
            count = RS.getString("num_comment_today");
        }
        RS.close();
        ste.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return count;
}
    
}
