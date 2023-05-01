/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import interfaces.InterfaceFiche;
import utils.MyConnection;
import entities.DossierMedical;
import entities.Ficheconsultation;
import static java.awt.PageAttributes.MediaType.D;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AOUADI HADIL
 */
public class CRUDfiche implements InterfaceFiche {
    
    
      PreparedStatement ps;
    Connection  conn = MyConnection.getInstance().getConn();
    
    

    @Override
    public void ajouterFiche(Ficheconsultation F) {
       
         try {
            String req ="INSERT INTO `ficheconsultation`( `date_consultation`, `first_name`, `last_name`, `specialite`, `traitement`, `reccomendation`) VALUES ('"+F.getDate_consultation()+"','"+F.getFirstName()+"','"+F.getLastName()+"','"+F.getSpecialite()+"','"+F.getTraitement()+"','"+F.getReccomendation()+"')";
            ps = conn.prepareStatement(req);
            ps.executeUpdate(req);
            
            System.out.println("fiche ajoutée");  
        } catch (SQLException ex) {
             System.out.println("fiche non ajoutée");
             System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ajouterFiche2(Ficheconsultation F) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int recupererIdFiche(String firstName, String lastName) {
    int id = 0;
    try {
        String req = "SELECT id FROM ficheconsultation WHERE first_name=? AND last_name=?";
        PreparedStatement pst = MyConnection.getInstance().getConn().prepareStatement(req);
        pst.setString(1, firstName);
        pst.setString(2, lastName);
        System.out.println("Executing SQL query: " + pst.toString());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération de l'ID du fiche : " + ex.getMessage());
    }
    System.out.println("ID du dossier pour " + firstName + " " + lastName + " : " + id);
    return id;
}

   @Override
    public void modifierFiche(Ficheconsultation F, int id) {
        try {
        // Récupérer l'ID du dossier en utilisant le prénom et le nom du patient

            String req = "UPDATE ficheconsultation SET date_consultation=?,first_name=?,last_name=?,specialite=?,traitement=?,reccomendation=? WHERE id=?";
            PreparedStatement pst = MyConnection.getInstance().getConn().prepareStatement(req);
            pst.setDate(1, Date.valueOf(F.getDate_consultation()));
            pst.setString(2, F.getFirstName());
            pst.setString(3, F.getLastName());
            pst.setString(4, F.getSpecialite());
            pst.setString(5, F.getTraitement());
            pst.setString(6, F.getReccomendation());
            pst.setInt(7,id);
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Fiche modifiée");
            } else {
                System.out.println("Impossible de modifier la fiche.");
            }
        } catch (SQLException ex) {
            System.out.println("Impossible de modifier la fiche." + ex.getMessage());
        }
    }

    @Override
    public void supprimerFiche( int id) {
    try {
        

        // Supprimer le dossier avec l'ID récupéré
        String req = "DELETE FROM `ficheconsultation`  WHERE id=?";
        PreparedStatement pst = MyConnection.getInstance().getConn().prepareStatement(req);
        pst.setInt(1, id); // utiliser l'ID récupéré

        int result = pst.executeUpdate();
        if (result > 0) {
            System.out.println("Fiche supprimé avec succès");
        } else {
            System.out.println("Impossible de supprimer la fiche avec l'ID " + id);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la suppression du fiche : " + ex.getMessage());
    }
}

    @Override
    public List<Ficheconsultation> afficherFiche() {
        List<Ficheconsultation> list = new ArrayList<>();
        
        String req="SELECT * FROM `ficheconsultation`";
        
        try {
            PreparedStatement ps = conn.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Ficheconsultation F = new Ficheconsultation();
                F.setId(rs.getInt(1));
                F.setDate_consultation(rs.getDate("date_consultation").toLocalDate());
                F.setFirstName(rs.getString("first_name"));
                F.setLastName(rs.getString("last_name"));
                F.setSpecialite(rs.getString("specialite"));
                F.setTraitement(rs.getString("traitement"));
                F.setReccomendation(rs.getString("reccomendation"));
                
                
                list.add(F);
            }
            System.out.println("fiche affiché");
        } catch (SQLException ex) {
            System.out.println("fiche non affiché");
            System.out.println(ex.getMessage());
        }
                
        return list;
    }
    }
    

