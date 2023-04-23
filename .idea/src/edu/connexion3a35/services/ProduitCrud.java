/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.services;

import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Produit;
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
public class ProduitCrud implements InterfaceCRUD<Produit> {

    @Override
    public void ajouterEntitee(Produit t) {
        try {
            String requete = "INSERT INTO produit(categorie_produit_id,nom_produit,  description, quantite_produit, prix , image_produit, marque)"
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, t.getCategorie_produit());
            pst.setString(2, t.getNom_produit());
            pst.setString(3, t.getDescription());
            pst.setInt(4, t.getQuantite());
            pst.setFloat(5, t.getPrix());
            pst.setString(6, t.getImage_produit());
            pst.setString(7, t.getMarque());
            pst.executeUpdate();
            System.out.println("ajouter succes!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Produit> listeDesEntites() {
        List<Produit> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM produit ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt(1));
                p.setNom_produit(rs.getString("nom_produit"));
                p.setMarque(rs.getString("marque"));
                p.setDescription(rs.getString("description"));
                p.setImage_produit(rs.getString("image_produit"));
                p.setCategorie_produit(rs.getInt("categorie_produit_id"));
                p.setQuantite(rs.getInt("quantite_produit"));
                p.setPrix(rs.getFloat("prix"));
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    
    public List<Produit> listeDes() {
        List<Produit> myList = new ArrayList<>();
        try {  
            String requete = "SELECT p.*, c.id as categorie_id, c.nom_categorie FROM produit p "
                    + "JOIN categorie c ON c.id=p.categorie_produit_id ;";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt(1));
                p.setNom_produit(rs.getString("nom_produit"));
                p.setMarque(rs.getString("marque"));
                p.setDescription(rs.getString("description"));
                p.setImage_produit(rs.getString("image_produit"));
                 p.setCategorie_produit(rs.getInt("categorie_produit_id"));
                p.setQuantite(rs.getInt("quantite_produit"));
                p.setPrix(rs.getFloat("prix"));
                Categorie categ = new Categorie( rs.getInt("categorie_id")  , rs.getString("nom_categorie") );
                p.setCat(categ);
                
                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    

    public void supprimerEntite(int id) {
        try {
            String requete = "DELETE FROM produit WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("produit supprime!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierEntite(Produit t) {
        try {
            String requete = "UPDATE produit SET categorie_produit_id=?,nom_produit=?,  description=?, quantite_produit=?, prix=? , image_produit=?, marque=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getCategorie_produit());
            pst.setString(2, t.getNom_produit());
            pst.setString(3, t.getDescription());
            pst.setInt(4, t.getQuantite());
            pst.setFloat(5, t.getPrix());
            pst.setString(6, t.getImage_produit());
            pst.setString(7, t.getMarque());
            pst.setInt(8, t.getId());
            pst.executeUpdate();
            System.out.println("produit modifie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
