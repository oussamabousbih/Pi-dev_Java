/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Entities.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yasmine Rajhi
 */
public class CRUDServices implements Interfaces.InterfaceServices {

    Statement ste;
    Connection conn;
    
    @Override
    public void addServices(Service S) {
        try {
            String req ="INSERT INTO `service`(  `nom_service`, `proprietaire`,`prix`,`date_debut`,`date_fin`) VALUES ('"+S.getNomService()+"','"+S.getProprietaire()+"','"+S.getPrix()+"','"+S.getDate_debut()+"','"+S.getDate_fin()+"')";
            ste = conn.createStatement();
            ste.executeUpdate(req);
            
            System.out.println("Service ajoutée");  
        } catch (SQLException ex) {
             System.out.println("Service non ajoutée");
             System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateServices(Service S) {
        String req = "UPDATE `service` SET `nom_service`='" + S.getNomService()+ "', `proprietaire`='" + S.getProprietaire()+ "', `prix`='" + S.getPrix()+"', `date_debut`='" + S.getDate_debut()+"', `date_fin`='" + S.getDate_fin()+"'";

        try {
            ste = conn.createStatement();
            int result = ste.executeUpdate(req);
            if (result > 0) {
                System.out.println("service modifié ");
                S.setNomService(S.getNomService());
                S.setProprietaire(S.getProprietaire());
                S.setPrix(S.getPrix());
                S.setDate_debut(S.getDate_debut());
                S.setDate_fin(S.getDate_fin());
            } else {
                System.out.println("Impossible de modifier le service.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du service : " + ex.getMessage());
        }

    }

    @Override
    public void deleteServices(Service S) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Service> showServices() {
        List<Service> list = new ArrayList<>();
        String req="SELECT * FROM `service` ";
        try{
            ste = conn.createStatement();
            ResultSet RS = ste.executeQuery(req);
            while(RS.next()){
            Service s = new Service();
            s.setId(RS.getInt(1));
            s.setNomService(RS.getString("NomService"));
            }
        }catch(SQLException ex){
            System.out.println("problème!");
        }
        return list;
    }
    
}
