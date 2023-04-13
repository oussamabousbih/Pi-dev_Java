/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.tests;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Produit;
import edu.connexion3a35.services.CategorieCrud;
import edu.connexion3a35.services.ProduitCrud;
import edu.connexion3a35.utils.MyConnection;
import java.util.List;

/**
 *
 * @author karra
 */
public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        
        //pcd.ajouterEntitee(p);
        Categorie c = new Categorie (13,"aps");
        CategorieCrud cr = new CategorieCrud();
        
        
        //cr.ajouterEntitee(c);        
        List<Categorie> res1= cr.listeDesEntites();
          System.out.println(res1);

        
      
        Produit pro=new Produit();
        pro.setCat(c);
         ProduitCrud pr =new ProduitCrud();  
         List<Produit> res2= pr.listeDesEntites();
          System.out.println(res2);
      
        // pr.ajouterEntitee(pro);
         
    }
}
