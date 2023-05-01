/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Service;
import utils.MyConnexion;
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
public class CRUDService implements interfaces.InterfaceService {
    
    Statement ste;
    Connection conn = MyConnexion.getInstance().getConn();

    @Override
    public void addServices(Service S) {
        try {
            String req ="INSERT INTO `service`(`id`, `nom_service`, `proprietaire`, `id_type`, `prix`, `date_debut`, `date_fin`) VALUES ('"+S.getId()+"','"+S.getNomService()+"','"+S.getProprietaire()+"','"+S.getId_type()+"','"+S.getPrix()+"','"+S.getDate_debut()+"','"+S.getDate_fin()+"')";
            ste = conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("succés!");
        } catch (SQLException ex) {
            System.out.println("failure of add!"+ex.getMessage());
        }
    }

    @Override
    public void updateServices(Service S) {
        try {
            String req = "UPDATE `service` SET `nom_service`='" + S.getNomService() + "', `proprietaire`='" + S.getProprietaire() + "', `id_type`='" + S.getId_type() + "', `prix`='" + S.getPrix() + "', `date_debut`='" + S.getDate_debut() + "', `date_fin`='" + S.getDate_fin() + "' WHERE `id`='" + S.getId() + "'";
            ste = conn.createStatement();
            int rowsUpdated = ste.executeUpdate(req);
            if (rowsUpdated > 0) {
                System.out.println("Success! " + rowsUpdated + " rows updated.");
                } else {
                    System.out.println("Failure: no rows updated.");
                }
            } catch (SQLException ex) {
                System.out.println("Failure of update! " + ex.getMessage());
            }
    }

    @Override
    public void deleteServices(Service S) {
        try {
        String req = "DELETE FROM `service` WHERE `id`='" + S.getId() + "'";
        ste = conn.createStatement();
        ste.executeUpdate(req);
        System.out.println("Record with id " + S.getId() + " deleted successfully!");
        } catch (SQLException ex) {
            System.out.println("Error deleting record with id " + S.getId() + ": " + ex.getMessage());
        }
    }

    @Override
    public List<Service> showServices() {
        List<Service> list = new ArrayList<>();
        String req = "SELECT * FROM `service`";
        try {
            ste = conn.createStatement();
            ResultSet RS = ste.executeQuery(req);
            while(RS.next()){
                Service S = new Service();
                S.setId(RS.getInt(1));
                S.setNomService(RS.getString("nom_service"));
                S.setProprietaire(RS.getString("proprietaire"));
                S.setPrix(RS.getString("prix"));
                S.setDate_debut(RS.getDate("date_debut"));
                S.setDate_fin(RS.getDate("date_fin"));
                list.add(S);
            }
            System.out.println("-----jawna behi!!-----");
        } catch (SQLException ex) {
            System.out.println("mochkla ! : " + ex.getMessage());
        }
        
        return list;
    }
    
}
