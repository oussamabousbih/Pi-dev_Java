/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Interfaces.InterfaceDossier;
import Utils.MyConnection;
import entities.DossierMedical;
import static java.awt.PageAttributes.MediaType.D;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AOUADI HADIL
 */
public class CRUDdossier implements InterfaceDossier {

    
    PreparedStatement ps;
    Connection  conn = MyConnection.getInstance().getConn();
    
    
    
    
    @Override
   public void ajouterDossier(DossierMedical D) {
        try {
            String req = "INSERT INTO `dossier_medical`(`first_name`, `last_name`, `date_naissance`, `email`, `vaccins`, `maladies`, `allergies`, `analyses`, `intervention_chirurgicale`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(req);
            ps.setString(1, D.getFirstName());
            ps.setString(2, D.getLastName());
           if (D.getDate_naissance() != null) {
    ps.setDate(3, Date.valueOf(D.getDate_naissance()));
} else {
    ps.setNull(3, java.sql.Types.DATE);
}
            ps.setString(4, D.getEmail());
            ps.setString(5, D.getVaccins());
            ps.setString(6, D.getMaladies());
            ps.setString(7, D.getAllergies());
            ps.setString(8, D.getAnalyses());
            ps.setString(9, D.getIntervention_chirurgicale());

            ps.executeUpdate();

            System.out.println("Dossier ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du dossier : " + ex.getMessage());
        }
    }

    @Override
    public void ajouterDossier2(DossierMedical D) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  public void modifierDossier(DossierMedical D, int id) {
    try {
        // Mettre à jour les informations du dossier avec l'ID fourni
        String req = "UPDATE dossier_medical SET first_name=?,last_name=?, date_naissance=?,email=?,vaccins=?,maladies=?,allergies=?,analyses=?,intervention_chirurgicale=? WHERE id=?";
        PreparedStatement pst = MyConnection.getInstance().getConn().prepareStatement(req);
        pst.setString(1, D.getFirstName());
        pst.setString(2, D.getLastName());
        pst.setDate(3, Date.valueOf(D.getDate_naissance()));
        pst.setString(4, D.getEmail());
        pst.setString(5, D.getVaccins());
        pst.setString(6, D.getMaladies());
        pst.setString(7, D.getAllergies());
        pst.setString(8, D.getAnalyses());
        pst.setString(9, D.getIntervention_chirurgicale());
        pst.setInt(10, id); // utiliser l'ID fourni

        int result = pst.executeUpdate();
        if (result > 0) {
            System.out.println("Dossier modifié avec succès");
        } else {
            System.out.println("Impossible de modifier le dossier avec l'ID " + id);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la modification du dossier : " + ex.getMessage());
    }
    
    
}
  






 
   @Override
public void supprimerDossier( int id) {
    try {
       

        // Supprimer le dossier avec l'ID récupéré
        String req = "DELETE FROM dossier_medical WHERE id=?";
        PreparedStatement pst = MyConnection.getInstance().getConn().prepareStatement(req);
        pst.setInt(1, id); // utiliser l'ID récupéré

        int result = pst.executeUpdate();
        if (result > 0) {
            System.out.println("Dossier supprimé avec succès");
        } else {
            System.out.println("Impossible de supprimer le dossier avec l'ID " + id);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la suppression du dossier : " + ex.getMessage());
    }
}


    @Override
  public List<DossierMedical> afficherDossier() {
    List<DossierMedical> list = new ArrayList<>();
    String req = "SELECT * FROM dossier_medical";
    
    try {
        PreparedStatement ps = conn.prepareStatement(req);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            DossierMedical D = new DossierMedical();
            D.setId(rs.getInt(1));
            D.setFirstName(rs.getString("first_name"));
            D.setLastName(rs.getString("last_name"));
            D.setDate_naissance(rs.getDate("date_naissance").toLocalDate());
            D.setEmail(rs.getString("email"));
            D.setVaccins(rs.getString("vaccins"));
            D.setMaladies(rs.getString("maladies"));
            D.setAllergies(rs.getString("allergies"));
            D.setAnalyses(rs.getString("analyses"));
            D.setIntervention_chirurgicale(rs.getString("intervention_chirurgicale"));
            
            list.add(D);
        }
        System.out.println("Dossier affiché.");
    } catch (SQLException ex) {
        System.out.println("Erreur lors de l'affichage du dossier : " + ex.getMessage());
    }
    
    return list;
}

}
    

