/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import entities.Article;
import entities.Commentaire;
import services.CRUDArticle;
import services.CRUDCommentaire;
import utils.MyConnection;

/**
 *
 * @author mazee
 */
public class PiDevProjetDESKTOP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyConnection cc = MyConnection.getInstance();
        Article add = new Article(3,"Testing Adding sujet","Testing Adding contenu","Testing Adding image");
        Commentaire addc = new Commentaire(15,1,"Testing Adding commentaire");
        Article edit = new Article(64,"Testing Modification","Testing Modification");
        Commentaire editc = new Commentaire(42,"Testing Modification allooo");
        Commentaire deletec = new Commentaire(39);
        Article delete = new Article(64);
        CRUDArticle art = new CRUDArticle();
        CRUDCommentaire cmnt = new CRUDCommentaire();
        System.out.println(art.statArticle());
        System.out.println(cmnt.statCommentaire());
        System.out.println(art.statArticleToday());
        System.out.println(cmnt.statCommentToday());
        //art.ajouterArticle(add);
        //cmnt.ajouterCommentaire(addc);
        //cmnt.modifierCommentaire(editc);
        //cmnt.supprimerCommentaire(deletec);
        //art.modifierArticle(edit);
        //art.supprimerArticle(delete);
        //System.out.println(art.afficherArticleDetail(15));
        //System.out.println(cmnt.afficherCommentaireDetail(17));
        //System.out.println(cmnt.afficherCommentaire());
    }
    
}
