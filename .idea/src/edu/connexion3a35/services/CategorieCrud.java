/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.services;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.interfaces.InterfaceCRUD;
import edu.connexion3a35.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author msi
 */
public class CategorieCrud implements InterfaceCRUD<Categorie> {

    @Override
    public void ajouterEntitee(Categorie c) {
        try {
            String requete = "INSERT INTO categorie(nom_categorie) VALUES (?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, c.getNom_Categorie());
            pst.executeUpdate();
            System.out.println("Categorie ajouter!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie> listeDesEntites() {
        List<Categorie> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM categorie";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Categorie c = new Categorie();
                c.setId(rs.getInt(1));
                c.setNom_Categorie(rs.getString("nom_categorie"));
                myList.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
  public List<Categorie> listeDesCategories() {
    List<Categorie> categories = new ArrayList<>();
    try {
        String requete = "SELECT nom_categorie FROM categorie";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Categorie c = new Categorie();
            c.setNom_Categorie(rs.getString("nom_categorie"));
            categories.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return categories;
}


    

    public void supprimerEntite(int id) {
        try {
            String requete = "DELETE FROM categorie WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Done!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierEntite(Categorie c) {
        try {
            String requete = "UPDATE categorie SET nom_categorie=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, c.getNom_Categorie());
            pst.setInt(2, c.getId());
            pst.executeUpdate();
            System.out.println("categorie modifier!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
